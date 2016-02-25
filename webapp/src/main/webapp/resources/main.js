function initializeNavigation() {
    var header = document.getElementById('navigation')
    header.appendChild(createNavigationElement('Home', function() {
        createHomeBody();
    }))
    header.appendChild(createNavigationElement('Transactions', function() {
		paymentController.openPayments()
	}))
	header.appendChild(createNavigationElement('Categories', function() {
		categoryController.openCategories()
	}))
}

	function createNavigationElement(name, callback) {
		var text = document.createElement('a')
		text.innerHTML = name
		text.addEventListener('click', callback)
		return text
	}

	function createHomeBody() {
	    var body = document.getElementById('body')
        body.innerHTML = "<h1>Welcome to your money tracker ;)</h1>"
	}