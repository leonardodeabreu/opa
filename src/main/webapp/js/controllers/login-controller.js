app.controller('loginController', ['$location', '$scope', 'ServiceAbstract', '$routeParams', 'MyStorageService', function($location, $scope, ServiceAbstract, $routeParams, MyStorageService) {

	var url = '/login';
	var urlUsuarioLogado = '/usuario/me';

	$scope.mensagem = false;

	if (MyStorageService.usuarioLogado.get()) {
		$location.path('/app/feed');
		return;
	}

	MyStorageService.usuarioLogado.clear();
	MyStorageService.nomeUsuarioLogado.clear();
	MyStorageService.imagemUsuarioLogado.clear();

	$scope.entrar = function(formValid) {
		if (!formValid) {
			return;
		}

		ServiceAbstract.post(url, $scope.login)
			.success(function(data, status, headers) {

				var tokenBearer = headers()['authorization'],
					token = tokenBearer.substring(7, tokenBearer.length);

				MyStorageService.token.set(token);

				ServiceAbstract.get(urlUsuarioLogado, token)
					.success(function(data) {
						//setando objeto usuario na storage
						MyStorageService.usuarioLogado.set(data.id);
						MyStorageService.nomeUsuarioLogado.set(data.nome);
						MyStorageService.imagemUsuarioLogado.set(data.image);
					})
					.error(function(data, status) {
						console.log('Erro ao carregar usuário logado! Erro: ' + data + ' - Status: ' + status);
					});

				$location.path('/app/feed');

			})
			.error(function(data, status) {
				$scope.mensagem = true;
			});

	}
}]);