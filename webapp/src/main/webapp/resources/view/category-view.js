categoryView = new function CategoryView() {

    this.showCategoryTab = function(callback) {
	    $.ajax({
			type: 'GET',
			url: 'resources/template/category/home.html',
			success: function(data) {
				var body = document.getElementById('body')
	   			body.innerHTML = data
	   			callback()
			}
		})
    }

    this.addCategoriesToTable = function(categories) {
    	$.ajax({
			type: 'GET',
			url: 'resources/template/category/tableRowTemplate.html',
			success: function(template) {
				var tableBody = document.getElementById('categoryTableBody')
				tableBody.innerHTML = ""
				categories.forEach(function(category) {
					var row = template.replace('${name}', category.name).replace('${id}', category.id)
					tableBody.innerHTML = tableBody.innerHTML + row
				})
			}
		})
    }

    this.showNewCategoryPopup = function() {
    	$.ajax({
			type: 'GET',
			url: 'resources/template/category/newCategoryPopup.html',
			success: function(template) {
				baseView.showPopup('popup', template)
			}
		})
    }

    this.markCategoryNameAsInvalid = function(categoryNameField) {
		categoryNameField.css({'background-color' : '#FFF2ED'})
    }

    this.showDeleteCategoryPopup = function(categoryId) {
    	$.ajax({
			type: 'GET',
			url: 'resources/template/category/deleteCategoryPopup.html',
			success: function(template) {
				baseView.showPopup('popup', template.replace('${categoryId}', categoryId))
			}
		})
    }

}