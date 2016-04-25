$(document).ready(function() {
	console.log("Check");

	$("#new_user").click(function() {
		alert("New User!");
	});

	$("#sign_in").click(function(event){
		event.preventDefault();
		//TODO: nsert backend logic here
		var user_name = $("#user_name").text();
		var password =$("password").text();
		if(user_name == null || password == null){
			return alert("Pleaser enter your user_name and password");
		}

		window.location.href = "/Users/Ishita/Documents/Stock_Market_Game/templates/Dashboard.html"

	})
});