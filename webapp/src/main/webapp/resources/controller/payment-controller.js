paymentController = new function PaymentController() {

	var paymentView = new PaymentView()
	var paymentModel = new PaymentModel()
	var categoryModel = new CategoryModel()

	this.openPayments = function() {
		paymentView.showPaymentTab(this.addPayments)
	}

	this.addPayments = function() {
		paymentModel.getPayments(function(payments) {
			paymentView.addPaymentsToTable(payments)
		})
	}

	this.addPaymentPopup = function() {
		categoryModel.getCategories(function(categories) {
			paymentView.showNewPaymentPopup(categories)
		})
	}

	this.createNewTransaction = function() {
        var categorySelection = document.getElementById("paymentCategory")
        var categoryId = categorySelection.options[categorySelection.selectedIndex].id

        var amount = document.getElementById("paymentAmount").value
        var note = document.getElementById("paymentNote").value

        var typeSelection = document.getElementById("paymentType")
        var type = typeSelection.options[typeSelection.selectedIndex].value.toUpperCase()

        var paymentTimestamp = Date.parse(document.getElementById("paymentDate").value)

        $.ajax({
            type: 'POST',
            url: 'rest/payments/',
            headers: {
              'amount': amount,
              'type': type,
              'note': note,
              'category': categoryId,
              'paymentTimestamp': paymentTimestamp
            },
            success: function() {
                this.openPayments()
                closePopup()
            }.bind(this),
            error: function(data) {
                closePopup()
                console.log('Error creating payment')
            }
        })
    }
}