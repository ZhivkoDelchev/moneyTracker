function initializeNavigation() {
    var header = document.getElementById("header")
    header.appendChild(createNavigationElement("Home", function() {
        createHomeBody();
    }))
    header.appendChild(createNavigationElement("Transactions", function() {
		createTransactionsBody()
	}))
    header.appendChild(createNavigationElement("Categories", function() {
		createCategoryBody()
	}))
}

function createNavigationElement(name, callback) {
    var text = document.createElement("a")
    text.innerHTML = name
    text.addEventListener("click", callback)
    var header = document.createElement("li")
    header.appendChild(text)
    return header
}

function createHomeBody() {
	setBodyContent("<h1>Welcome to your money tracker ;)</h1>")
}

function createTransactionsBody() {
	setBodyContent("<h1>Transactions are not yet implemented.</h1>")
}

function createCategoryBody() {
	setBodyContent("<h1>Categories are not yet implemented.</h1>")
}

function setBodyContent(content) {
	var body = document.getElementById("body")
	removeChildren(body)
	body.innerHTML = content
}

function removeChildren(element) {
	while (element.firstChild) {
		removeChildren(element.firstChild)
		element.removeChild(element.firstChild);
	}
}