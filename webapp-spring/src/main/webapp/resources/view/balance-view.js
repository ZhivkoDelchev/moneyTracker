function BalanceView() {
    this.showBalance = function(balance) {
		$.ajax({
			type: 'GET',
			url: 'resources/template/home/balance.html',
			success: function(template) {
				var body = document.getElementById('body')
                var balanceHtml = 'incomes: ' + balance.incomes + ', expenses: ' + balance.expenses + ', balance: ' + balance.balance
                body.innerHTML = template.replace('${balance}', balanceHtml)
			}
		})
    }
}