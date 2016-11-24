app.controller('pedidoFeedController', ['$scope', 'ServiceAbstract', '$routeParams', 'MyStorageService', function($scope, ServiceAbstract, $routeParams, MyStorageService) {

	var url = '/pedido',
			url_usuario = '/usuario',
      url_hist_pedido = '/historicoPedido';

  ServiceAbstract.getAll(url_hist_pedido)
    .success(function(data) {
      $scope.historicoPedido = data;
      
      for (var i=0; i<$scope.historicoPedido.length; i++) {
        recupearFeedPedidoPeloHistoricoPedido($scope.historicoPedido[i], i);
      }  	
      
    })
    .error(function(data, status) {
      var erro = data + '. Status: ' + status;
      console.log('Ei, ocorreu um erro ao carregar a listagem de pedidos!. Erro:' + erro);
    });

    var recupearFeedPedidoPeloHistoricoPedido = function(objetoPedido, indice) {
      ServiceAbstract.get(url, objetoPedido.pedido)
        .success( function(data) {
          $scope.historicoPedido[indice].categoria  = data.categoria.nome;
          $scope.historicoPedido[indice].titulo  = data.titulo;
          $scope.historicoPedido[indice].descricao  = data.descricao;

          if ($scope.historicoPedido[indice].titulo.length > 20) {
            $scope.historicoPedido[indice].titulo = $scope.historicoPedido[indice].titulo.substr(0,20) + '..';  
          }
          recuperarUsuarioRequeriuPedido($scope.historicoPedido[indice].usuarioRequisicao, indice);
        })
        .error ( function(data, status) {
          console.log("Erro ao listar os pedidos. Erro:" + data +'.Status: '+status);
        })
    }

    var recuperarUsuarioRequeriuPedido = function(idUsuario, indice) {
      ServiceAbstract.get(url_usuario, idUsuario)
        .success( function(data) {
          $scope.historicoPedido[indice].nomeUsuario = data.nome.split(" ")[0];
          $scope.historicoPedido[indice].imagemUsuario = data.image;
        })
        .error( function(data, status) {
          var erro =  data + '. Status: ' + status;
          console.log("Erro ao carregar o usuário que requeriu o pedido ! Erro: " + erro);
        })
    }

    $scope.recuperarObjOriginal = function(historicoPedido) {

      ServiceAbstract.get(url_hist_pedido, historicoPedido.id)
        .success(function(data) {
          $scope.objetoOriginal = data;
          alterarStatusParaAberto($scope.objetoOriginal);
        })
        .error(function(data, status) {
          var erro = data + ' Status: ' + status;
          console.log('Erro a carregar o objeto original! Erro:' + erro);
        });
    }

    var alterarStatusParaAberto = function(objetoPedido) {
      console.log(objetoPedido);
     
      ServiceAbstract.modify(url_hist_pedido, objetoPedido)
        .success(function(data) {
          $scope.objetoHistoricoServico = data;
          $('.modal').addClass("open");
          $('.modal .modal-title').text('hey!');
          $('.modal .modal-message').text('Você escolheu este serviço para prestar! ele está em andamento agora!');
          $(".modal .btn.negative, .modal .btn.positive").click(function() {
            $(this).parents(".modal").toggleClass("open");
            location.reload();
          });
        })
        .error(function(data, status) {
          var erro = data + ' Status: ' + status;
          console.log('Erro ao mudar status do serviço! Erro:' + erro);
        })
    }
  

 	// controle dos elementos da tela

  setTimeout(function(){
  	$(".pedido-item .icon-arrow-down").click(function(){
  		$(this).toggleClass("open");
  		$(this).parents(".pedido-item").find(".pedido-details").toggleClass("open");
  	});

  	//filtro

  	$(".filter .btn").click(function(){
  		$(this).parent().find(".filter-content").addClass("active");
  	});

  	$(".filter-content .btn").click(function(){
  		$(this).parents(".filter-content").removeClass("active");
  	});
  }, 200);

}]);