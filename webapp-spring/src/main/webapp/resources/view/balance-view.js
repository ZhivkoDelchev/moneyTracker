function BalanceView() {
    this.showBalance = function(balance) {
		$.ajax({
			type: 'GET',
			url: 'resources/template/home/balance.html',
			success: function(template) {
				var body = document.getElementById('body')
                var balanceHtml = template.replace('${incomes}', balance.incomes).replace('${expenses}', balance.expenses).replace('${balance}', balance.balance)
                body.innerHTML = balanceHtml
			}
		})
    }
}