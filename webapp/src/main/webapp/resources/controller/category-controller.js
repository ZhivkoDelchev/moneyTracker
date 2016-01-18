categoryController = new function CategoryController() {

    var categoryModel = new CategoryModel()
    var categoryView = new CategoryView()

	this.openCategories = function() {
		categoryView.showCategoryTab(this.addCategories)
	}

	this.addCategories = function() {
		categoryModel.getCategories(function(categories) {
			categoryView.addCategoriesToTable(categories)
		})
	}

	this.addCategoryPopup = function() {
		categoryView.showNewCategoryPopup()
	}

	this.createNewCategory = function() {
		var categoryNameField = $("#createCategoryName")
		if (isValidName(categoryNameField.val())) {
			categoryView.markCategoryNameAsInvalid(categoryNameField)
		} else {
			postNewCategory(categoryNameField.val())
		}
	}

	function isValidName(categoryName) {
		return categoryName == null || categoryName == "" || categoryName == undefined || categoryName.length > 20
	}

	function postNewCategory(categoryName) {
		$.ajax({
			type: 'POST',
			url: 'rest/category/' + categoryName,
			success: function(data) {
				baseView.closePopup()
				categoryController.addCategories()
			}.bind(categoryController),
			error: function() {
				baseView.closePopup()
				console.log('Error creating category!')
			}
		})
	}

	this.deleteCategoryPopup = function(categoryId) {
		categoryView.showDeleteCategoryPopup(categoryId)
	}

	this.deleteCategory = function(categoryId) {
		$.ajax({
			type: 'DELETE',
			url: 'rest/category/' + categoryId,
			success: function(data) {
				categoryController.addCategories()
				baseView.closePopup()
			},
			error: function() {
				baseView.closePopup()
				console.log('Error creating category!')
			}
		})
	}
}