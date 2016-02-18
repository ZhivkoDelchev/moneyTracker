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
	
	function createAddButton(displayPopup) {
		var button = document.createElement('div')
		button.className = 'icon icon-plus'
		button.addEventListener('click', function() {
			displayPopup()
			fadeIn()
		})
		return button
	}

	function fadeIn() {
		$(".overlay").fadeIn(750)
		$(".overlay").fadeTo(750, 0.3).css('display', 'block')
	}

	function closePopup() {
		$("#popup").hide()
		fadeOut()
	}
	
	function fadeOut() {
		$(".overlay").fadeOut(300)
		$(".overlay").fadeOut(300)
	}

	function setBodyContent(element) {
		removeBodyContent()
		body.appendChild(element)
	}

	function appendContainerChildrenToBody(container) {
		while(container.firstChild) {
			body.appendChild(container.firstChild);
		}
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