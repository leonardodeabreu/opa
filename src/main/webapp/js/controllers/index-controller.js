app.controller("indexController", ["$scope", function($scope) {

	
		var pathname = window.location.pathname;
		pathname = pathname.replace(/\//g, ' ');
		$("body").addClass(pathname);
		console.log(pathname);

}]);//end