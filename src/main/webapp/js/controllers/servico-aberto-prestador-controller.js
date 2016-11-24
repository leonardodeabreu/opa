app.controller('servicoAbertoPrestadorController', ['$location', '$scope', 'ServiceAbstract', '$routeParams', 'MyStorageService','$filter', function($location, $scope, ServiceAbstract, $routeParams, MyStorageService, $filter) {

  var url = '/historicoServico/servicoStatusAberto',
      url_usuario = '/usuario',
      url_servico = '/servico',
      url_hist_servico = '/historicoServico',
      url_fin_servico = '/historicoFinalizacaoServico';

      $scope.naoExisteNada = null;

  ServiceAbstract.get(url, MyStorageService.usuarioLogado.get())
    .success(function(data) {
      $scope.historicoServico = data;

      for (var i = 0; i < $scope.historicoServico.length; i++) {
        recuperarUsuarioQueRequeriuServico($scope.historicoServico[i].usuarioRequisicao, i);
        recuperarServicoRequerido($scope.historicoServico[i].servico, i);
        $scope.historicoServico[i].inicio = $filter('date')($scope.historicoServico[i].inicio, 'dd/MM/yyyy');
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
        console.log(data);
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
        $scope.historicoServico[indice].servicoDataInicial = $filter('date')(data.dataCadastro, 'dd/MM/yyyy');
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
        $scope.objetoHistoricoServico = data;
        $('.modal.a').addClass("open");
        $('.modal.a .modal-title').text('hey!');
        $('.modal.a .modal-message').text('Este serviço está esperando a confirmação de fechamento do solicitante!');
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
      "origem" : "PRESTADOR"
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
    $(".modal-opener").click(function() {
      $(this).parents(".pagelist-item").find(".modal").toggleClass("open");
    });

    $(".modal .btn.negative, .modal .btn.positive").click(function() {
      $(this).parents(".modal").toggleClass("open");
    });

    $(".modal .btn.positive").click(function() {
      $(this).parents(".pagelist-item").addClass("next-phase");
    });

    //detalhes
    $(".btn.icon-info").click(function(){
      $(this).toggleClass("open");
      $(this).parents(".white-block").find(".details").slideToggle();
    });
  }, 200);


}]);
