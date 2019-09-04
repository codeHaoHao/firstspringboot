function testRequest() {
	$("#testRequest").click(function() {
		let url = "/sigin";
		let data = {
				username:"15138931676",
				password:"15138931676"
		};
		$.ajax({
			type : "POST",
			url : url,
			data : data,
			success : function(json) {
				console.log(json)
				console.log(json.map.user)
			}
		})
	})
}

function init() {
	testRequest();
}
$(document).ready(function() {
	init();
})
