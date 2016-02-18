function PaymentView() {

	this.showPaymentTab = function(callback) {
		$.ajax({
			type: 'GET',
			url: 'resources/template/payment/home.html',
			success: function(data) {
				var body = document.getElementById('body')
	   			body.innerHTML = data
	   			callback()
			}
		})
	}

	this.addPaymentsToTable = function(payments) {
		$.ajax({
			type: 'GET',
			url: 'resources/template/payment/tableRow.html',
			success: function(template) {
				var tableBody = document.getElementById('paymentTableBody')
				tableBody.innerHTML = ""
				payments.forEach(function(payment) {
					var paymentCategory = payment.category ? payment.category.name : "<i>Uncategorised</i>"
					var row = template.replace('${category}', paymentCategory).replace('${amount}', payment.amount)
					tableBody.innerHTML = tableBody.innerHTML + row
				})
			}
		})
	}

	this.showNewPaymentPopup = function(categories) {
		$.ajax({
			type: 'GET',
			url: 'resources/template/payment/newPayment.html',
			success: function(template) {
				this.showNewPaymentPopupWithCategories(categories, template)
			}.bind(this)
		})
	}

	this.showNewPaymentPopupWithCategories = function(categories, template) {
	    $.ajax({
            type: 'GET',
            url: 'resources/template/payment/categoryOptions.html',
            success: function(categoryOptionTemplate) {
                var categoryOptions = categoryOptionTemplate.replace('${id}', '0').replace('${category}', '')

                categories.forEach(function(category) {
                    categoryOptions += categoryOptionTemplate.replace('${id}', category.id).replace('${category}', category.name)
                })

                baseView.showPopup('popup-payments', template.replace('${categoryOptions}', categoryOptions))
            }
        })
	}
}