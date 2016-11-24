app.controller('pedidoNaoAtendidoController', ['$location', '$filter', '$scope', 'ServiceAbstract', '$routeParams','MyStorageService', function($location, $filter, $scope, ServiceAbstract, $routeParams, MyStorageService) {

  var url = '/pedido/pedidoPorDono',
      idUsuarioLogado = MyStorageService.usuarioLogado.get(),
      url_pedido = '/pedido';

      $scope.naoExisteNada = null;

  ServiceAbstract.get(url, idUsuarioLogado)
    .success(function(data) {
      $scope.pedidoNaoAtendido = data;

      for(var i=0; i<$scope.pedidoNaoAtendido.length; i++) {
      	recuperarCategoria($scope.pedidoNaoAtendido[i].id, i);
      }

      if ($scope.pedidoNaoAtendido.length == 0) {
        $scope.naoExisteNada = true;
      } else {
        $scope.naoExisteNada = false;
      }

    })
    .error(function(data, status) {
      var erro = data + ' Status: ' + status;
      console.log("Ei, ocorreu um erro ao carregar a listagem de pedidos!. Erro:" + erro);
    });

  var recuperarCategoria =  function(idServico, indice) {
		ServiceAbstract.get(url_pedido, idServico)
	    .success(function(data) {
	      $scope.pedidoNaoAtendido[indice].categoria = data.categoria.nome;
	    })
	    .error(function(data, status) {
	      var erro = data + ' Status: ' + status;
	      console.log("Ei, ocorreu um erro ao carregar a categoria dos pedidos!. Erro:" + erro);
	    });
  }


  $scope.deletarPedido = function(idPedido) {
  	console.log(idPedido);
    ServiceAbstract.delete(url_pedido, idPedido)
      .success( function(data) {
        location.reload();
      })
      .error( function(data, status) {
        $('.modal.a').addClass("open");
        var erro = data + ' Status: ' + status;
        $('.modal.a .modal-title').text('opss...');
        $('.modal.a .modal-message').text('Ei, ocorreu um erro ao excluir o serviço!. Erro:' + erro);
        $(".modal.a .btn.negative, .modal .btn.positive").click(function(){
          $(this).parents(".modal.a").toggleClass("open");
        });
      })
    }

  //controlar elementos
	//modal
	setTimeout(function(){
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
	}, 200);

}]);