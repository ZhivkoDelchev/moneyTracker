function PaymentModel() {

	this.getPayments = function(callback) {
		$.ajax({
            dataType: 'json',
            type: 'GET',
            url: 'rest/payments?page=0&size=20',
            success: function(data) {
                callback(data)
            },
            error: function() {
                console.log('Error getting categories!')
            }
        })
	}
}