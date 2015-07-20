category = new function category() {

    this.createCategoryBody = function() {
        var containerElement = document.createElement('div')
        containerElement.appendChild(createAddButton(addCategoryPopup))
        containerElement.appendChild(document.createElement('br'))
        containerElement.appendChild(createCategoriesTable())

        removeBodyContent()
        appendContainerChildrenToBody(containerElement)
    }
     
	function addCategoryPopup() {
		if (document.getElementById("popup")) {
			$("#popup").show()
		} else { 
			popup = document.createElement('div')
			popup.setAttribute('class', 'popup ')
			popup.setAttribute('id', 'popup')

			
			var body = document.getElementById('body')
			body.appendChild(popup)
		}
		$("#popup").html('<label class=\"inputLabel\">Name:</label><div class=\"cancel\" onclick=\"closePopup();\"></div><input type=\"text\" id=\"createCategoryName\"  class=\"textInput\"/><input type=\"button\" value=\"Create\" class=\"button\" onClick=\"category.createNewCategory()\"/>')
		$("#popup").draggable()
	}
	
	this.createNewCategory = function() {
		var categoryName = $("#createCategoryName").val()
		if (categoryName == null || categoryName == "" || categoryName == undefined || categoryName.length > 20) {
			$("#createCategoryName").css({'background-color' : '#FFF2ED'})
		} else {
			$.ajax({
				type: 'POST',
				url: 'rest/payments/category/' + categoryName,
				success: function(data) {
					this.createCategoryBody()
					closePopup()
				}.bind(this),
				error: function() {
					closePopup()
					console.log('Error creating category!')
				}.bind(this)
			})
		}
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
            }.bind(this),
            error: function() {
                console.log('Error getting categories!')
            }
        })

        return table
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
	
	function createDeleteButton(categoryId) {
		var button = document.createElement('div')
		button.className = 'icon icon-minus'
		button.addEventListener('click', function() {
			deleteCategoryConfirmPopup(categoryId)
			fadeIn()
		})
		return button
	}
	
	function deleteCategoryConfirmPopup(categoryId) {
		if (document.getElementById("popup")) {
			$("#popup").remove()
		}
		popup = document.createElement('div')
		popup.setAttribute('class', 'popup ')
		popup.setAttribute('id', 'popup')

		
		var body = document.getElementById('body')
		body.appendChild(popup)
		
		$("#popup").html('<label>Deleting a category will remove category from all payments using it. Are you sure you want to delete it?</label></div><input type=\"button\" value=\"Yes\" class=\"button\" onClick=\"category.deleteCategory(' + categoryId + ')\"/></div><input type=\"button\" value=\"No\" class=\"button\" onClick=\"closePopup()\"/>')
		$("#popup").draggable()
	}
	
	this.deleteCategory = function(categoryId) {
		$.ajax({
			type: 'DELETE',
			url: 'rest/payments/category/' + categoryId,
			success: function(data) {
				this.createCategoryBody()
				closePopup()
			}.bind(this),
			error: function() {
				closePopup()
				console.log('Error creating category!')
			}.bind(this)
		})
	}

}