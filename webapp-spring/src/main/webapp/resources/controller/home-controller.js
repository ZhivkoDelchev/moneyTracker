homeController = new function HomeController() {

    var balanceModel = new BalanceModel()
    var balanceView = new BalanceView()

    this.openHome = function() {
        balanceModel.getBalance(function(balance) {
            balanceView.showBalance(balance)
        })
    }
}