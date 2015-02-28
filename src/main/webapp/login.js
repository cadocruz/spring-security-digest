jQuery(document).ready(function($) {
	var digestAuth = new pl.arrowgroup.DigestAuthentication({
		onSuccess : function(response) {
			console.log("Success " + response.message);
			$("#response").html(response.message);
		},
		onFailure : function(response) {
			console.log("Failure " + response.responseJSON.message);
			$("#response").html('Invalid credentials !!! ' + response.responseJSON.message);
		},
		cnonce : 'testCnonce'
	});
	
	$('#btLogin').click(function () {
		digestAuth.setCredentials($('#username').val(), $('#password').val());
		digestAuth.call('/api/hello');
	});
});
