app.controller("headerController", ["$scope", "$location", "$routeParams", 'MyStorageService', 'ServiceAbstract', function($scope, $location, $routeParams, MyStorageService, ServiceAbstract) {

  $scope.usuarioLogado = MyStorageService.usuarioLogado.get() ? MyStorageService.usuarioLogado.get() : '';

  $scope.sair = function(argument) {
    MyStorageService.usuarioLogado.clear();
    MyStorageService.nomeUsuarioLogado.clear();
    MyStorageService.imagemUsuarioLogado.clear();
    $location.path('/app/login');
    return;
  }

  $scope.$watch(function(scope) {
    if (MyStorageService.imagemUsuarioLogado.get()) {
      $scope.image = MyStorageService.imagemUsuarioLogado.get();
      $scope.controllLogin = false;
    } else {
      $scope.controllLogin = true;
      $scope.nome = '';
    }


    if (MyStorageService.nomeUsuarioLogado.get()) {
      $scope.nome = 'Olá ' + MyStorageService.nomeUsuarioLogado.get().split(" ")[0] + '!';
    }
  });

  ServiceAbstract.getAll('/categoria')
    .success( function(data) {
      $scope.categorias = data;
    })
    .error( function(data, status) {
      var erro = data + '.Status:' +status;
      console.log('Erro ao carregar as categorias. Erro:' + erro);
    })

  $scope.servicoPorCategoria = function(nomeCategroia) {
    ServiceAbstract.get('/servico/servicoPorCategoria', nomeCategroia)
      .success( function(data) {
        $scope.feedServico = data;
      })
      .error( function(data, status) {
        var erro = data + '.Status:' +status;
        console.log('Erro ao carregar as categorias. Erro:' + erro);
      })
  }

}]); //end
