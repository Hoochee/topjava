package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import javax.validation.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        ValidationUtil.validate(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            saveRole(new ArrayList<>(user.getRoles()), newKey.intValue());
            return user;
        } else {
            if (namedParameterJdbcTemplate.update("""
                       UPDATE users SET name=:name, email=:email, password=:password, 
                       registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                    """, parameterSource) == 0) {
                return null;
            }
            deleteRoles(user.getId());
            saveRole(new ArrayList<>(user.getRoles()), user.getId());
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_roles r ON u.id = r.user_id WHERE u.id=?",
                getExtractor(), id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_roles r ON u.id=r.user_id WHERE email=?",
                getExtractor(), email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT * FROM users u LEFT JOIN user_roles r ON u.id=r.user_id ORDER BY name, email", getExtractor());
    }

    private ResultSetExtractor<List<User>> getExtractor() {
        return rs -> {
            Map<Integer, User> map = new HashMap<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                System.out.println(name);
                String email = rs.getString("email");
                String password = rs.getString("password");
                int calories = rs.getInt("calories_per_day");
                boolean enabled = rs.getBoolean("enabled");
                Date registered = rs.getDate("registered");
                Role roles = Role.valueOf(rs.getString("role"));
                if (map.containsKey(id)) map.get(id).getRoles().add(roles);
                else map.putIfAbsent(id, new User(id, name, email, password, calories, enabled, registered,
                        new ArrayList<>(Collections.singleton(roles))));
            }
            return new ArrayList<>(map.values());
        };
    }

    private void saveRole(final List<Role> roles, int idUser) {
        String sqlRole = "INSERT INTO user_roles (user_id,role) VALUES (?,?)";
        jdbcTemplate.batchUpdate(sqlRole, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Role role = roles.get(i);
                ps.setInt(1, idUser);
                ps.setString(2, role.name());
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });
    }

    private void deleteRoles(int idUser) {
        jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", idUser);
    }
}
