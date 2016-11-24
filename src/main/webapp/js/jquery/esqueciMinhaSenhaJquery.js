app.controller('esqueciMinhaSenhaJquery', ['$scope', 'ServiceAbstract', '$routeParams', function($scope, ServiceAbstract, $routeParams) {

	//modal
	$(".modal-opener").click(function(){
		$(this).parent().find(".modal").toggleClass("open");
	});

	$(".modal .btn.negative, .modal .btn.positive").click(function(){
		$(this).parents(".modal").toggleClass("open");
	});


}]);