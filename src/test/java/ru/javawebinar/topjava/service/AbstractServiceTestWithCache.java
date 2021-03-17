package ru.javawebinar.topjava.service;

import org.junit.Assume;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import ru.javawebinar.topjava.repository.JpaUtil;

import java.util.Arrays;

public class AbstractServiceTestWithCache extends AbstractUserServiceTest{
    @Autowired
    protected JpaUtil jpaUtil;


    @Before
    public void setup() {

        cacheManager.getCache("users").clear();
        jpaUtil.clear2ndLevelHibernateCache();

    }
}
