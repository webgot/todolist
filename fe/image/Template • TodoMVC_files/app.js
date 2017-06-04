(function (window) {
	'use strict';


	$(".input-form").submit(function(event){
		event.preventDefault();
		var str = $(".input").value();
		alert(str);
	})

})(window);
