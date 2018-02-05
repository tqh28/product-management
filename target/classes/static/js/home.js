$(document).ready(function() {
	
	$('#table-data').DataTable({
		"searching": false,
		"lengthMenu": [10, 15, 20, 25, 35],
		"processing": true,
        "serverSide": true,
        "ajax": {
            "url": "get-product-list",
            "data": function ( d ) {
                d.name = $('#searchName').val();
                d.lowestPrice = validateIsNull($('#lowestPrice').val()) ? EMPTY_STRING : $('#lowestPrice').val();
                d.highestPrice = validateIsNull($('#highestPrice').val()) ? EMPTY_STRING : $('#highestPrice').val();
            }
        },
        "columns": [
            { "data": "code" },
            { "data": "name" },
            { "data": "price" },
            { "data": "code" },
            { "data": "id" },
        ],
        "columnDefs": [
        	{
                "render": function ( data, type, row ) {
                	// check user is admin by create new product button
                	var isAdmin = $("#create-new-product").length == 0 ? false : true
                	if (isAdmin) {
                		return '<a href="/admin/edit-product?id=' + row.id + '">' + data + '</a>';
                	} else {
                		return '<a href="/product-detail?id=' + row.id + '">' + data + '</a>';
                	}
                },
                "targets": 1
            },
            {
            	"render": function (data) {
            		return formatPrice(data);
            	},
            	"targets": 2,
            },
            {
                "render": function ( data, type, row ) {
                	return '<input type="button" class="btn btn-info" value="Check Stock" onclick="checkInStock(\'' + data + '\', \'' + row.name + '\')"/>';
                },
                "targets": 3
            },
            {
                "render": function ( data, type, row ) {
                	// check user is admin by create new product button
                	var isAdmin = $("#create-new-product").length == 0 ? false : true
                	if (isAdmin) {
                		return '<input type="button" class="btn btn-danger" value="Remove" onclick="removeProduct(\'' + data + '\', \'' + row.name + '\')"/>';
                	} else {
                		return '<input type="button" class="btn" value="Permission denied" onclick=""/>';
                	}
                    
                },
                "targets": 4
            },
        ]
	});
	
	
	$("#search-button").click(function(){
		$('#table-data').DataTable().ajax.reload();
	});
});

