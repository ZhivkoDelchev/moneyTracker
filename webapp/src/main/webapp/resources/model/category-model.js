function CategoryModel() {

    this.getCategories = function(callback) {
		$.ajax({
            dataType: 'json',
            type: 'GET',
            url: 'rest/categories',
            success: function(data) {
                callback(data)
            },
            error: function() {
                console.log('Error getting categories!')
            }
        })
    }

    this.postNewCategory = function(categoryName, successCallback, errorCallback) {
        $.ajax({
            type: 'POST',
            url: 'rest/categories/' + categoryName,
            success: function(data) {
                successCallback()
            }.bind(categoryController),
            error: function() {
                errorCallback()
            }
        })
    }

    this.delete = function(categoryId) {
        $.ajax({
            type: 'DELETE',
            url: 'rest/categories/' + categoryId,
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