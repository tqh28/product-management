const validateIsNull = function(param) {
	if (param == null || param == "") {
		return true;
	}
	return false;
}

const formatNumber = function(number) {
	if (validateIsNull(number)) {
		return "";
	} else {
		return number.toLocaleString(DEFAULT_LOCALE);
	}
}

const formatDateTime = function(date) {
	if (validateIsNull(date)) {
		return "";
	} else {
		return (new Date(date)).toLocaleString(DEFAULT_LOCALE);
	}
}

const formatPrice = function(price) {
	price = formatNumber(price);
	if (price == "") price = 0;
	
	return "$" + price;
}

const checkInStock = function(productCode, productName) {
	$.get({
		url : STOCK_CHECK_URL + productCode,
		success : function(result) {
			if (validateIsNull(result) || result.quantity < 1) {
				$("#dialog-detail").text(productName + " is out of stock!");
			} else {
				$("#dialog-detail").text(productName + " is available in stock! (" + formatNumber(result.quantity) + " left)");
			}
			$( "#dialog" ).dialog();
		},
		error: function (jqXHR, textStatus, errorThrown) {
			console.log('error');
		}
	})
}

const removeProduct = function(id, productName) {
	$("#remove-dialog-confirm-detail").text(productName)
	$("#remove-dialog-confirm").dialog({
		resizable : false,
		height : "auto",
		width : 400,
		modal : true,
		buttons : {
			"Remove" : function() {
				$.ajax({
					url : 'admin/remove-product?id=' + id,
					headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
					type : 'DELETE',
					success : function(result) {
						if (result.msgCode == SUCCESS_CODE) {
							// reload data
							$("#table-data").DataTable().ajax.reload();
							$("#delete-success-msg").show(500);
							setTimeout(function() {$("#delete-success-msg").hide(500)}, 3000);
							$("#delete-success-msg-detail").text(result.msgDetail);
						} else {
							$("#unlock-error-msg").show(500);
							setTimeout(function() {$("#unlock-error-msg").hide(500)}, 20000);
							$("#unlock-error-msg-detail").text(result.msgDetail);
						}
						
					},
					error: function (jqXHR, textStatus, errorThrown) {
						$("#delete-error-msg").show(500);
						setTimeout(function() {$("#delete-error-msg").hide(500)}, 20000);
						$("#delete-error-msg-detail").html(jqXHR.responseText + '<br>' + errorThrown);
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