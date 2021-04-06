const mealAjaxUrl = "ajax/meals/";

const ctx = {
    ajaxUrl: mealAjaxUrl
};

$(function () {
    makeEditable(
        $("#datatable").DataTable( {
            "paging" : false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data":"calories"
                },
                {
                    "defaultContent" : "Edit",
                    "orderable" : false
                },
                {
                    "defaultContent": "Delete",
                    "orderable" : false
                }
            ],
            "order" : [
                0,
                "desc"
            ]
        })
    )
});
function resetForm() {
   $('#dateForm').find(":input").val("");
   updateTable();
}

function filter() {

        let form=$('#dateForm');
       /* var startDate=(form.find('#startDate').val());
        var startTime=(form.find('#startTime').val());
        var endDate=(form.find('#endDate').val());
        var endTime=(form.find('#endTime').val());*/
        $.ajax({
            type:"GET",
            url:"ajax/meals/filter",
            data:form.serialize()
        }).done(function (){
            updateTable();
        })
        ;
}