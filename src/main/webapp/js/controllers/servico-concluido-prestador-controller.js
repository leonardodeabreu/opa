app.controller('servicoConcluidoPrestadorController', ['$location', '$scope', 'ServiceAbstract', '$routeParams', 'MyStorageService','$filter', function($location, $scope, ServiceAbstract, $routeParams, MyStorageService, $filter) {

  var url = '/historicoServico/servicoStatusFechado',
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

  //Controle de elementos da tela

  setTimeout(function() {
    //modal
    $(".icon-avaliation").click(function(){
      $(this).toggleClass("open");
      $(this).parents(".pagelist-item").find(".evaluation").toggleClass("open");
    });
  }, 200);


}]);
