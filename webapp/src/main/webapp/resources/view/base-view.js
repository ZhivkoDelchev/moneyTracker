baseView = new function BaseView() {

	this.showPopup = function(popupClass, popupContent) {
		if (document.getElementById("popup")) {
			$("#popup").show()
		} else { 
			popup = document.createElement('div')
			popup.setAttribute('class', popupClass)
			popup.setAttribute('id', 'popup')

			var body = document.getElementById('body')
			body.appendChild(popup)
			$("#popup").draggable()
		}
		$("#popup").html(popupContent)
		fadeIn()
	}

	function fadeIn() {
		$(".overlay").fadeIn(750)
		$(".overlay").fadeTo(750, 0.3).css('display', 'block')
	}

	this.closePopup = function() {
		$("#popup").hide()
		$(".overlay").fadeOut(300)
	}
}