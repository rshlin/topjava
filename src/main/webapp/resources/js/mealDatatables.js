var ajaxUrl = 'ajax/profile/meals/';
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + 'filter',
        data: $('#filter').serialize(),
        success: function (data) {
            updateTableByData(data);
        }
    });
    return false;
}

$(function () {
    datatableApi = $('#datatable').DataTable(
        {
            ajax: {
                "url": ajaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date, type, row) {
                        if (type == 'display') {
                            var dateObject = new Date(date);
                            return '<span>' + dateObject.toISOString().substr(0, 16).replace('T', ' ') + '</span>';
                        }
                        return date;
                    }
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderEditBtn
                },
                {
                    "defaultContent": "",
                    "orderable": false,
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                $(row).css('color', data.exceed ? 'red' : 'green');
            },
            "initComplete": makeEditable
        });

    $('#filter').submit(function () {
        updateTable();
        return false;
    });

    $('#dateTime').datetimepicker({
        format: 'Y-m-d\\TH:i'
    });

    var $startDate = $('#startDate'),
        $endDate = $('#endDate');

    $startDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow:function( ct ) {
            this.setOptions({
                maxDate: $endDate.val() ? $endDate.val() : false
            })
        }
    });

    $endDate.datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow:function( ct ) {
            this.setOptions({
                minDate: $startDate.val() ? $startDate.val() : false
            })
        }
    });

    $('.time-pick').datetimepicker({
        datepicker: false,
        format: 'H:i'
    })
});