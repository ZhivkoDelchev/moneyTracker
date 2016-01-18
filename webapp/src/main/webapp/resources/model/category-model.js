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
                return []
            }
        })
    }
}