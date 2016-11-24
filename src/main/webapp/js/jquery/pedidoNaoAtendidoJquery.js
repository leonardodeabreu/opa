app.controller('pedidoNaoAtendidoJquery', ['$scope', 'ServiceAbstract', '$routeParams', function($scope, ServiceAbstract, $routeParams) {

	//modal
	$(".modal-opener").click(function(){
		$(this).parents(".pagelist-item").find(".modal").toggleClass("open");
	});

	$(".modal .btn.negative, .modal .btn.positive").click(function(){
		$(this).parents(".modal").toggleClass("open");
	});

	//remover
	$(".modal .btn.negative").click(function(){
		$(this).parents(".pagelist-item").remove();
	});

	//detalhes
	$(".icon-arrow-down").click(function(){
		$(this).toggleClass("open");
		$(this).parents(".pagelist-item").find(".details").toggleClass("open");
	});


}]);