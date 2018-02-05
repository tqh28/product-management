$(document).ready(function() {
	
	$('#table-user').DataTable({
		"searching": false,
		"lengthMenu": [10, 15, 20, 25, 35],
		"processing": true,
        "serverSide": true,
        "ajax": {
            "url": "get-locked-user",
        },
        "columns": [
            { "data": "username" },
            { "data": "lockedDate" },
            { "data": "username" },
        ],
        "columnDefs": [
        	{
                "render": function ( data, type, row ) {
                	return formatDateTime(data);
                },
                "targets": 1
            },
            {
                "render": function ( data, type, row ) {
                	return '<input type="button" class="btn btn-primary" value="Unlock" onclick="unlockAccount(\'' + data + '\')"/>';
                },
                "targets": 2
            },
        ]
	});
	
	$("#refresh-button").click(function(){
		$('#table-user').DataTable().ajax.reload();
	});
});

const unlockAccount = function (username) {
	$("#unlock-dialog-confirm-detail").text(username);
	$("#unlock-dialog-confirm").dialog({
		resizable : false,
		height : "auto",
		width : 400,
		modal : true,
		buttons : {
			"Unlock" : function() {
				$.ajax({
					url : 'unlock-account-action?username=' + username,
					headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
					type : 'PUT',
					success : function(result) {
						if (result.msgCode == SUCCESS_CODE) {
							// reload data
							$("#table-user").DataTable().ajax.reload();
							$("#unlock-success-msg").show(500);
							setTimeout(function() {$("#unlock-success-msg").hide(500)}, 3000);
							$("#unlock-success-msg-detail").text(result.msgDetail);
						} else {
							$("#unlock-error-msg").show(500);
							setTimeout(function() {$("#unlock-error-msg").hide(500)}, 20000);
							$("#unlock-error-msg-detail").text(result.msgDetail);
						}
						
					},
					error: function (jqXHR, textStatus, errorThrown) {
						$("#unlock-error-msg").show(500);
						setTimeout(function() {$("#unlock-error-msg").hide(500)}, 20000);
						$("#unlock-error-msg-detail").html(jqXHR.responseText + '<br>' + errorThrown);
					}
				})

				$(this).dialog("close");
			},
			"Cancel" : function() {
				$(this).dialog("close");
			}
		}
	});
}

