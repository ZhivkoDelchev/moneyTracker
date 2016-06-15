function BalanceModel() {

    this.getBalance = function(callback) {
		$.ajax({
            dataType: 'json',
            type: 'GET',
            url: 'rest/balance',
            success: function(data) {
                callback(data)
            },
            error: function() {
                console.log('Error getting categories!')
            }
        })
    }
}