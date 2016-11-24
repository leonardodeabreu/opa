app.controller('esqueciMinhaSenhaController', ['$scope', 'ServiceAbstract', '$routeParams', '$http', function($scope, ServiceAbstract, $routeParams, $http) {

	
	$scope.esqueciSenha = function(email){
        var url = $http.post('http://localhost:9090/usuariosenha',{"email":email});
         
        url.success(function(retorno){
        	console.log(retorno);
        });
         
        url.error(function(retorno,status){                   
            console.log(retorno);
        });
    };
	
	//jquery
	$(".modal-opener").click(function(){
		$(this).parent().find(".modal").toggleClass("open");
	});

	$(".modal .btn.negative, .modal .btn.positive").click(function(){
		$(this).parents(".modal").toggleClass("open");
	});


}]);