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
		var element = createH1TemporarayMessage('Welcome to your money tracker ;)')
		setBodyContent(element)
	}

	// TODO: cleanup
	function createH1TemporarayMessage (message) {
		var h1 = document.createElement('h1')
		h1.innerHTML = message
		return h1;
	}
	
	function setBodyContent(element) {
		removeBodyContent()
		body.appendChild(element)
	}

	function removeBodyContent() {
		var body = document.getElementById('body')
		removeChildren(body)
	}

	function removeChildren(element) {
		while (element.firstChild) {
			removeChildren(element.firstChild)
			element.removeChild(element.firstChild);
		}
	}