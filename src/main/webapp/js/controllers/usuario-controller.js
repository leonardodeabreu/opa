app.controller('usuarioController', ['$location', '$scope', 'ServiceAbstract', '$routeParams', 'MyStorageService', function($location, $scope, ServiceAbstract, $routeParams, MyStorageService) {

  var urlUsuario = '/usuario',
    urlServicoUsuario = 'servico/servicoPorDono';

  if ($routeParams.id) {

    ServiceAbstract.get(urlUsuario, $routeParams.id)
      .success(function(data) {
        $scope.image = data.image;
        $scope.nome = data.nome;

        ServiceAbstract.get(urlServicoUsuario, $routeParams.id)
          .success(function(data) {
          	$scope.servicos = data;
          })
          .error(function(data, status) {
            alert('Data: ' + data + ' Status: ' + status);
          });

      })
      .error(function(data, status) {
        alert('Data: ' + data + ' Status: ' + status);
      });
  } else {

    if (MyStorageService.imagemUsuarioLogado.get()) {
      $scope.image = MyStorageService.imagemUsuarioLogado.get();
      $scope.nome = MyStorageService.nomeUsuarioLogado.get();
    } else {
      $scope.image = image;
      $scope.nome = '';
    }

  }

}]);
