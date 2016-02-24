function CategoryModel() {

    this.getCategories = function(callback) {
		$.ajax({
            dataType: 'json',
            type: 'GET',
            url: 'rest/category',
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
            url: 'rest/category/' + categoryName,
            success: function(data) {
                successCallback()
            }.bind(categoryController),
            error: function() {
                errorCallback()
            }
        })
    }
}