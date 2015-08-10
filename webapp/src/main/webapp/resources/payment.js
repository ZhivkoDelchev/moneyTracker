payment = new function payment() {

    this.createTransactionsBody = function() {
        var containerElement = document.createElement('div')
        containerElement.appendChild(createAddButton(addTransactionPopup))
        containerElement.appendChild(document.createElement('br'))
        containerElement.appendChild(createTransactionsTable())

        removeBodyContent()
        appendContainerChildrenToBody(containerElement)
	}

	function addTransactionPopup() {
        if (document.getElementById("popup")) {
            $("#popup").show()
        } else {
            popup = document.createElement('div')
            popup.setAttribute('class', 'popup-payments')
            popup.setAttribute('id', 'popup')


            var body = document.getElementById('body')
            body.appendChild(popup)
        }
		
		$.ajax({
            dataType: 'json',
            type: 'GET',
            url: 'rest/category',
            success: function(data) {
				$("#popup").empty()
				$("#popup").append('<label class=\"inputLabel\">Category:</label>')
				$("#popup").append('<div class=\"cancel\" onclick=\"closePopup();\"></div>')
				var selection = $("<select class=\"textInput\" ></select>")
				selection.append('<option></option>')
				data.forEach(function(entry) {
					selection.append('<option id=\""' + entry.id + '\">' + entry.name + '</option>')
				})
				$("#popup").append(selection)
				$("#popup").append('<label class=\"inputLabel\">Amount:</label>')
				$("#popup").append('<input type=\"text\" id=\"transactionAmount\"  class=\"textInput\"/>')
				$("#popup").append('<input type=\"button\" value=\"Create\" class=\"button\" onClick=\"payment.createNewTransaction()\"/>')
                
        $("#popup").draggable()
            }.bind(this),
            error: function() {
                console.log('Error getting categories!')
            }
        })
		
        
    }

	function createTransactionsTable() {
        var headerLine = document.createElement('tr')
        headerLine.innerHTML = '<th>Category</th><th>Amount</th>'

        var table = document.createElement('table')
        table.appendChild(headerLine)

        $.ajax({
            dataType: 'json',
            type: 'GET',
            url: 'rest/payments',
            success: function(data) {
                addTransactionsToTable(data, table)
            }.bind(this),
            error: function() {
                console.log('Error getting transactions!')
            }
        })

        return table
    }

    function addTransactionsToTable(categories, table) {
        categories.forEach(function(entry) {
            var line = document.createElement('tr')

            var nameColumn = document.createElement('td')
			if (entry.category) {
				nameColumn.innerHTML = entry.category.name
			} else { 
				nameColumn.innerHTML = "<i>Uncategorised</i>"
			}
            line.appendChild(nameColumn)

            var amountColumn = document.createElement('td')
            amountColumn.innerHTML = entry.amount
            line.appendChild(amountColumn)

            table.appendChild(line)
        })
    }
	
	this.createNewTransaction = function() {
		
	}
}