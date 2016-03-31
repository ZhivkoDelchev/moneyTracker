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
			categoryModel.postNewCategory(categoryNameField.val(),
                function() {
                    baseView.closePopup()
                    categoryController.addCategories()
                },
                function() {
                    baseView.closePopup()
    				console.log('Error creating category!')
                }
			)
		}
	}

	function isValidName(categoryName) {
		return categoryName == null || categoryName == "" || categoryName == undefined || categoryName.length > 20
	}

	this.deleteCategoryPopup = function(categoryId) {
		categoryView.showDeleteCategoryPopup(categoryId)
	}

	this.deleteCategory = function(categoryId) {
		categoryModel.delete(categoryId)
	}
}