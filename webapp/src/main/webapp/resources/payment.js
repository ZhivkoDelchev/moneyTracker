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
            success: function(categories) {
                var popup = $("#popup");
				popup.empty()
				popup.append('<div class=\"cancel\" onclick=\"closePopup();\"></div>')

				popup.append('<label class=\"inputLabel\">Amount:</label>')
                popup.append('<input type=\"text\" id=\"paymentAmount\"  class=\"textInput\"/>')

                popup.append('<label class=\"inputLabel\">Category:</label>')
				var categorySelection = $('<select id=\"paymentCategory\" class=\"textInput\" ></select>')
				categorySelection.append('<option></option>')
				categories.forEach(function(entry) {
					categorySelection.append('<option id=\"' + entry.id + '\">' + entry.name + '</option>')
				})
				popup.append(categorySelection)

                popup.append('<label class=\"inputLabel\">Type:</label>')
				var typeSelection = $('<select id=\"paymentType\" class=\"textInput\" ></select>')
				typeSelection.append('<option></option>')
				typeSelection.append('<option>Income</option>')
				typeSelection.append('<option>Expense</option>')
				popup.append(typeSelection)

				popup.append('<label class=\"inputLabel\">Note:</label>')
                popup.append('<input type=\"text\" id=\"paymentNote\"  class=\"textInput\"/>')

                popup.append('<label class=\"inputLabel\">Date:</label>')
                popup.append('<input type="date" id="paymentDate" class="dateInput" />')
                $("#paymentDate").datepicker()

				popup.append('<input type=\"button\" value=\"Create\" class=\"button\" onClick=\"payment.createNewTransaction()\"/>')
                
                popup.draggable()
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
	    var categorySelection = document.getElementById("paymentCategory")
	    var categoryId = categorySelection.options[categorySelection.selectedIndex].id

	    var amount = document.getElementById("paymentAmount").value
	    var note = document.getElementById("paymentNote").value

	    var typeSelection = document.getElementById("paymentType")
        var type = typeSelection.options[typeSelection.selectedIndex].value.toUpperCase()

        var date = document.getElementById("paymentDate").value

		$.ajax({
            type: 'POST',
            url: 'rest/payments/',
            headers: {
              'amount': amount,
              'type': type,
              'note': note,
              'category': categoryId,
              'date': date
            },
            success: function() {
                this.createTransactionsBody()
                closePopup()
            }.bind(this),
            error: function(data) {
                closePopup()
                console.log('Error creating payment')
            }
        })
	}
}