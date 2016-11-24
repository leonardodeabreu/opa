
app.controller('servicoAbertoSolicitanteController', ['$location', '$scope', 'ServiceAbstract', '$routeParams', 'MyStorageService', function($location, $scope, ServiceAbstract, $routeParams, MyStorageService) {

  var url = '/historicoPedido/pedidoStatusAberto',
      url_usuario = '/usuario',
      url_pedido = '/pedido',
      url_hist_pedido = '/historicoPedido',
      url_fin_servico = '/historicoFinalizacaoServico';

      $scope.naoExisteNada = null;

  ServiceAbstract.get(url, MyStorageService.usuarioLogado.get())
    .success(function(data) {
      $scope.historicoPedido = data;

      for (var i = 0; i < $scope.historicoPedido.length; i++) {
        recuperarUsuarioQueRequeriuPedido($scope.historicoPedido[i].usuarioRequisicao, i);
        recuperarPedidoRequerido($scope.historicoPedido[i].pedido, i);
      }

      if ($scope.historicoPedido.length == 0) {
        $scope.naoExisteNada = true;
      } else {
        $scope.naoExisteNada = false;
      }

    })
    .error(function(data, status) {
      var erro = data + ' Status: ' + status;
      console.log('Erro a carregar a listagem de pedidos em aberto! Erro:' + erro);
    });

  var recuperarUsuarioQueRequeriuPedido = function(usuarioRequisicao, indice) {
    ServiceAbstract.get(url_usuario, usuarioRequisicao)
      .success(function(data) {
        $scope.historicoPedido[indice].usuarioRequisicao = data.nome.split(" ")[0];
        $scope.historicoPedido[indice].imagemUsuario = data.image;
        $scope.historicoServico[indice].usuario_id = data.id;
      })
      .error(function(data, status) {
        var erro = data + ' Status: ' + status;
        console.log('Erro a carregar o usuario que requeriu o serviço! Erro:' + erro);
      });
  }

  var recuperarPedidoRequerido = function(pedidoRequerido, indice) {
    ServiceAbstract.get(url_pedido, pedidoRequerido)
      .success(function(data) {
        $scope.historicoPedido[indice].pedido = data.titulo;
      })
      .error(function(data, status) {
        var erro = data + ' Status: ' + status;
        console.log('Erro a carregar o usuário que requeriu o pedido! Erro:' + erro);
      });
  }

  $scope.recuperarObjOriginal = function(historicoPedido) {
    ServiceAbstract.get(url_hist_pedido, historicoPedido.id)
      .success(function(data) {
        $scope.objetoOriginal = data;
        moverParaFechamentoSolicitado($scope.objetoOriginal);
      })
      .error(function(data, status) {
        var erro = data + ' Status: ' + status;
        console.log('Erro a carregar o usuario que requeriu o serviço! Erro:' + erro);
      });

  }

  var moverParaFechamentoSolicitado = function(historico) {
    ServiceAbstract.modify(url_hist_pedido, historico)
      .success(function(data) {
        $scope.objetoHistoricoServico = data;
        $('.modal.a').addClass("open");
        $('.modal.a .modal-title').text('hey!');
        $('.modal.a .modal-message').text('Este serviço está esperando a confirmação de fechamento do prestador!');
        $(".modal.a .btn.negative, .modal .btn.positive").click(function() {
          $(this).parents(".modal.a").toggleClass("open");
        });
        setarOrigemDaFinalizacao($scope.objetoHistoricoServico);
      })
      .error(function(data, status) {
        var erro = data + ' Status: ' + status;
        console.log('Erro ao mudar status do serviço! Erro:' + erro);
      })
  }

   var setarOrigemDaFinalizacao = function(historico) {
    $scope.historicoFinalizacaoServico = {
      "historicoServicoId" : historico.id,
      "usuarioId" : MyStorageService.usuarioLogado.get(),
      "origem" : "SOLICITANTE"
    }

    ServiceAbstract.post(url_fin_servico, $scope.historicoFinalizacaoServico)
      .success(function(data) {
        
      })
      .error(function(data, status) {
        var erro = data + ' Status: ' + status;
        console.log('Erro ao salvar origem do servico! Erro:' + erro);
      })
  }

  //Controle de elementos da tela

  setTimeout(function() {
    //modal
    $(".modal-opener").click(function(){
      $(this).parents(".pagelist-item").find(".modal").toggleClass("open");
    });

    $(".modal .btn.negative, .modal .btn.positive").click(function(){
      $(this).parents(".modal").toggleClass("open");
    });

    $(".modal .btn.positive").click(function(){
      $(this).parents(".pagelist-item").addClass("next-phase");
    });
    //detalhes
    $(".btn.icon-info").click(function(){
      $(this).toggleClass("open");
      $(this).parents(".white-block").find(".details").slideToggle();
    });
  }, 200);


}]);
