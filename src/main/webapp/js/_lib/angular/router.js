app.config(function($routeProvider, $locationProvider, $httpProvider) {


	$httpProvider.defaults.withCredentials = true;
	$httpProvider.interceptors.push('AuthInterceptor');

	$routeProvider
		.when("/app/login", {
			templateUrl: "views/login.html",
			controller: "loginController",
		})
		.when("/app/feed", {
			templateUrl: "views/feed.html",
			controller: "servicoFeedController"
		})
		.when("/app/servico-detalhes/:id?", {
			templateUrl: "views/servico-detalhes.html",
			controller: "servicoDetalhesController"
		})
		.when("/app/usuario-cadastro/:id?", {
			templateUrl: "views/usuario-cadastro.html",
			controller: "usuarioCadastroController"
		})
		.when("/app/servico-cadastro/:id?", {
			templateUrl: "views/servico-cadastro.html",
			controller: "servicoCadastroController",
			resolve: {
				"check": function(MyStorageService, $location) {
					if (!MyStorageService.usuarioLogado.get()) {
						$location.path('/app/login');
						return;
					}
				}
			}
		})
		.when("/app/usuario/:id?", {
			templateUrl: "views/usuario.html",
			controller: "usuarioController",
			resolve: {
				"check": function(MyStorageService, $location) {
					if (!MyStorageService.usuarioLogado.get()) {
						$location.path('/app/login');
						return;
					}
				}
			}
		})
		.when("/app/quem-falou-comigo", {
			templateUrl: "views/quem-falou-comigo.html",
			controller: "quemFalouComigoController",
			resolve: {
				"check": function(MyStorageService, $location) {
					if (!MyStorageService.usuarioLogado.get()) {
						$location.path('/app/login');
						return;
					}
				}
			}
		})
		.when("/app/servico-concluido-prestador", {
			templateUrl: "views/servico-concluido-prestador.html",
			controller: "servicoConcluidoPrestadorController",
			resolve: {
				"check": function(MyStorageService, $location) {
					if (!MyStorageService.usuarioLogado.get()) {
						$location.path('/app/login');
						return;
					}
				}
			}
		})
		.when("/app/servico-gerenciar-prestador", {
			templateUrl: "views/servico-gerenciar-prestador.html",
			controller: "servicoGerenciarController",
			resolve: {
				"check": function(MyStorageService, $location) {
					if (!MyStorageService.usuarioLogado.get()) {
						$location.path('/app/login');
						return;
					}
				}
			}
		})
		.when("/app/pedido-cadastro-pergunta", {
			templateUrl: "views/pedido-cadastro-pergunta.html",
			resolve: {
				"check": function(MyStorageService, $location) {
					if (!MyStorageService.usuarioLogado.get()) {
						$location.path('/app/login');
						return;
					}
				}
			}
		})
		.when("/app/pedido-cadastro/:id?", {
			templateUrl: "views/pedido-cadastro.html",
			controller: "pedidoCadastroController",
			resolve: {
				"check": function(MyStorageService, $location) {
					if (!MyStorageService.usuarioLogado.get()) {
						$location.path('/app/login');
						return;
					}
				}
			}
		})
		.when("/app/esqueci-minha-senha", {
			templateUrl: "views/esqueci-minha-senha.html",
			controller: "esqueciMinhaSenhaController",
		})
		.when("/app/servico-concluido-solicitante", {
			templateUrl: "views/servico-concluido-solicitante.html",
			controller: "servicoConcluidoSolicitanteController",
			resolve: {
				"check": function(MyStorageService, $location) {
					if (!MyStorageService.usuarioLogado.get()) {
						$location.path('/app/login');
						return;
					}
				}
			}
		})
		.when("/app/pedido-nao-atendido", {
			templateUrl: "views/pedido-nao-atendido.html",
			controller: "pedidoNaoAtendidoController",
			resolve: {
				"check": function(MyStorageService, $location) {
					if (!MyStorageService.usuarioLogado.get()) {
						$location.path('/app/login');
						return;
					}
				}
			}
		})
		.when("/app/pedido-feed", {
			templateUrl: "views/pedido-feed.html",
			controller: "pedidoFeedController",
		})
		.when("/app/servico-aberto-prestador", {
			templateUrl: "views/servico-aberto-prestador.html",
			controller: "servicoAbertoPrestadorController",
			resolve: {
				"check": function(MyStorageService, $location) {
					if (!MyStorageService.usuarioLogado.get()) {
						$location.path('/app/login');
						return;
					}
				}
			}
		})
		.when("/app/servico-aberto-solicitante", {
			templateUrl: "views/servico-aberto-solicitante.html",
			controller: "servicoAbertoSolicitanteController",
			resolve: {
				"check": function(MyStorageService, $location) {
					if (!MyStorageService.usuarioLogado.get()) {
						$location.path('/app/login');
						return;
					}
				}
			}
		})
		.when("/app/finalizar-prestador", {
			templateUrl: "views/finalizar-prestador.html",
			controller: "servicoFinalizarPrestadorController",
			resolve: {
				"check": function(MyStorageService, $location) {
					if (!MyStorageService.usuarioLogado.get()) {
						$location.path('/app/login');
						return;
					}
				}
			}
		})
		.when("/app/finalizar-solicitante", {
			templateUrl: "views/finalizar-solicitante.html",
			controller: "pedidoFinalizarSolicitanteController"
			,
			resolve: {
				"check": function(MyStorageService, $location) {
					if (!MyStorageService.usuarioLogado.get()) {
						$location.path('/app/login');
						return;
					}
				}
			}
		})

	.otherwise('/app/feed');

	$locationProvider.html5Mode(false);

});

app.factory('AuthInterceptor', ['$q', '$window', '$location', '$injector', function($q, $window, $location, $injector) {
	var MyStorageService = $injector.get("MyStorageService");
	return {
		request: function(config) {
			config.headers = config.headers || {};
			//insere o token no header do cabeçalho
			if (MyStorageService.token.get()) {
				config.headers.Authorization = MyStorageService.token.get();
			}
			return config || $q.when(config);
		},
		response: function(response) {
			return response || $q.when(response);
		},
		responseError: function(rejection) {
			if (rejection.status === 403) {
				//limpa o token do storage
				MyStorageService.token.clear();
				$location.path("/login");
			} else {
				var message = rejection.data + '<br><br><i>' + rejection.status + ' - ' + rejection.statusText + '</i>';
			}
			return $q.reject(rejection);
		}
	};
}]);