function initializeNavigation() {
    var header = document.getElementById('header')
    header.appendChild(createNavigationElement('Home', function() {
        createHomeBody();
    }))
    header.appendChild(createNavigationElement('Transactions', function() {
		createTransactionsBody()
	}))
    header.appendChild(createNavigationElement('Categories', function() {
		createCategoryBody()
	}))
}

	function createNavigationElement(name, callback) {
		var text = document.createElement('a')
		text.innerHTML = name
		text.addEventListener('click', callback)
		var header = document.createElement('li')
		header.appendChild(text)
		return header
	}

	function createHomeBody() {
		var element = createH1TemporarayMessage('Welcome to your money tracker ;)')
		setBodyContent(element)
	}

	function createTransactionsBody() {
		var element = createH1TemporarayMessage('Transactions are not yet implemented.')
		setBodyContent(element)
	}
	
	// TODO: cleanup
	function createH1TemporarayMessage (message) {
		var h1 = document.createElement('h1')
		h1.innerHTML = message
		return h1;
	}

	function createCategoryBody() {
		var containerElement = document.createElement('div')
		containerElement.appendChild(createAddButton())
		containerElement.appendChild(document.createElement('br'))
		containerElement.appendChild(createCategoriesTable())
		
		removeBodyContent()
		appendContainerChildrenToBody(containerElement)
	}

		function createAddButton() {
			var button = document.createElement('div')
			button.className = 'addButton'
			button.innerHTML = '+'
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
			
			function displayPopup() {
				if (document.getElementById("popup")) {
					$("#popup").show()
				} else { 
					popup = document.createElement('div')
					popup.setAttribute('class', 'popup ')
					popup.setAttribute('id', 'popup')

					
					var body = document.getElementById('body')
					body.appendChild(popup)
				}
				$("#popup").html('<label class=\"inputLabel\">Name:</label><div class=\"cancel\" onclick=\"closePopup();\"></div><input type=\"text\" id=\"createCategoryName\"  class=\"textInput\"/><input type=\"button\" value=\"Create\" class=\"button\" onClick=\"createNewCatetgory()\"/>')
				$("#popup").draggable()
			}
			
			function createNewCatetgory() {
				$.ajax({
					type: 'POST',
					url: 'rest/payments/category/' + $("#createCategoryName").val(),
					success: function(data) {
						createCategoryBody()
						closePopup()
					},
					error: function() {
						closePopup()
						console.log('Error creating category!')
					}
				})
			}
			
			function closePopup() {
				$("#popup").hide()
				fadeOut()
			}
			
			function fadeOut() {
				$(".overlay").fadeOut(300)
				$(".overlay").fadeOut(300)
			}
		
		function createCategoriesTable() {
			var headerLine = document.createElement('tr')
			headerLine.innerHTML = '<th>Name</th><th>Delete</th>'
			
			var table = document.createElement('table')
			table.appendChild(headerLine)
			
			$.ajax({
				dataType: 'json',
				type: 'GET',
				url: 'rest/payments/category',
				success: function(data) {
					addCategoriesToTable(data, table)
				},
				error: function() {
					console.log('Error getting categories!')
				}
			})
			
			return table
		}
		
			function createDeleteButton(categoryId) {
				var button = document.createElement('div')
				button.className = 'deleteButton'
				button.textContent = '-'
				button.addEventListener('click', function() {
					alert('Delete : ' + categoryId)
				})
				return button
			}

			function addCategoriesToTable(categories, table) {
				categories.forEach(function(entry) {
					var line = document.createElement('tr')

					var nameColumn = document.createElement('td')
					nameColumn.innerHTML = entry.name
					line.appendChild(nameColumn)

					var deleteColumn = document.createElement('td')
					deleteColumn.appendChild(createDeleteButton(entry.id))
					line.appendChild(deleteColumn)

					table.appendChild(line)
				})
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