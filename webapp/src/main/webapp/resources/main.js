function initializeNavigation() {
    homeHeader = createNavigationElement("Home", function() {
        alert("test!")
    })
    var header = document.getElementById("header")
    header.appendChild(homeHeader)
    header.appendChild(createNavigationElement("Transactions"))
    header.appendChild(createNavigationElement("Categories"))
}

function createNavigationElement(name, callback) {
    var text = document.createElement("a")
    text.innerHTML = name
    text.addEventListener("click", callback)
    var header = document.createElement("li")
    header.appendChild(text)
    return header
}