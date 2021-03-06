app.controller('servicoFinalizarPrestadorController', ['$location', '$scope', 'ServiceAbstract', '$routeParams', 'MyStorageService', function($location, $scope, ServiceAbstract, $routeParams, MyStorageService) {

  var url = '/historicoServico/servicoStatusFechamentoSolicitado',
      url_usuario = '/usuario',
      url_servico = '/servico',
      url_hist_servico = '/historicoServico',
      url_ver_quem_finalizou_primeiro = '/historicoFinalizacaoServico/histFinPorServico';

      $scope.naoExisteNada = null;

  ServiceAbstract.get(url, MyStorageService.usuarioLogado.get())
    .success(function(data) {
      $scope.historicoServico = data;

      for (var i = 0; i < $scope.historicoServico.length; i++) {
        verificarQuemSolicitouFechamento($scope.historicoServico[i].id, i);
        recuperarUsuarioQueRequeriuServico($scope.historicoServico[i].usuarioRequisicao, i);
        recuperarServicoRequerido($scope.historicoServico[i].servico, i); 
      }

      if ($scope.historicoServico.length == 0) {
        $scope.naoExisteNada = true;
      } else {
        $scope.naoExisteNada = false;
      }

    })
    .error(function(data, status) {
      var erro = data + ' Status: ' + status;
      console.log('Erro a carregar a listagem de serviços em aberto! Erro:' + erro);
    });

  var recuperarUsuarioQueRequeriuServico = function(usuarioRequisicao, indice) {
    ServiceAbstract.get(url_usuario, usuarioRequisicao)
      .success(function(data) {
        $scope.historicoServico[indice].usuarioRequisicao = data.nome.split(" ")[0];
        $scope.historicoServico[indice].imagemUsuario = data.image;
        $scope.historicoServico[indice].usuario_id = data.id;
      })
      .error(function(data, status) {
        var erro = data + ' Status: ' + status;
        console.log('Erro a carregar o usuario que requeriu o serviço! Erro:' + erro);
      });
  }

  var recuperarServicoRequerido = function(servicoRequisitado, indice) {
    ServiceAbstract.get(url_servico, servicoRequisitado)
      .success(function(data) {
        $scope.historicoServico[indice].servico = data.titulo;
      })
      .error(function(data, status) {
        var erro = data + ' Status: ' + status;
        console.log('Erro a carregar o usuario que requeriu o serviço! Erro:' + erro);
      });
  }

  $scope.recuperarObjOriginal = function(historicoServico) {

    ServiceAbstract.get(url_hist_servico, historicoServico.id)
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
    ServiceAbstract.modify(url_hist_servico, historico)
      .success(function(data) {
        $('.modal.a').addClass("open");
        $('.modal.a .modal-title').text('hey!');
        $('.modal.a .modal-message').text('Este serviço agora está esperando a confirmação de fechamento do solicitante!');
        $(".modal.a .btn.negative, .modal .btn.positive").click(function() {
          $(this).parents(".modal.a").toggleClass("open");
        });

      })
      .error(function(data, status) {
        var erro = data + ' Status: ' + status;
        console.log('Erro ao mudar status do serviço! Erro:' + erro);
      })
  }

  var verificarQuemSolicitouFechamento = function(idServico, indice) {
    ServiceAbstract.get(url_ver_quem_finalizou_primeiro, idServico)
      .success(function(data) {

        if (data.origem != null || data.origem != undefined) {
          $scope.historicoServico[indice].origem = 'next-phase next-phase-' + data.origem;

        }

       
        
      })
      .error(function(data, status) {
        var erro = data + ' Status: ' + status;
        console.log('Erro ao carregar histórico de finalização do serviço! Erro:' + erro);
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

  }, 200);


}]);
