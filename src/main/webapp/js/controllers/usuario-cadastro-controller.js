app.controller('usuarioCadastroController', ['$location', '$filter', '$scope', 'ServiceAbstract', '$routeParams', 'MyStorageService', function($location, $filter, $scope, ServiceAbstract, $routeParams, MyStorageService) {

	var url = '/usuario',
		url_estado = '/estado',
		url_cidade = '/cidade/cidadePorEstado',
		url_verifica_login = '/verificaLoginExistente';

	$scope.temCidade = false;

	ServiceAbstract.getAll(url_estado)
		.success(function(data) {
			$scope.estados = data;

			if (MyStorageService.usuarioLogado.get()) {
				ServiceAbstract.get(url, MyStorageService.usuarioLogado.get())
					.success(function(data) {
						data.dataNascimento = new Date(data.dataNascimento);
						$scope.usuario = data;
						MyStorageService.imagemUsuarioLogado.set(data.image);

						$scope.usuario.estado = {
							"nome": data.estado
						};

						$.each($scope.estados, function(index, val) {
							if (val.nome == $scope.usuario.estado.nome) {
								ServiceAbstract.get(url_cidade, val.id)
									.success(function(response) {
										$scope.cidades = response;
									}).error(function(response) {
										console.log('error', response);
									});
							}
						});

						$scope.usuario.cidade = {
							"nome": data.cidade
						};
					})
					.error(function(data, status) {
						alert('Data: ' + data + ' Status: ' + status);
					});
			} else {
				var date = new Date();

				$scope.usuario = {
					"telefones": [{
						"tipo": "f",
						"telefone": ""
					}],
					"image": image,
					"estado": "",
					"cidade": "",
					"genero": "M",
					"dataNascimento": new Date(date.getFullYear() - 18, date.getMonth(), date.getDate()),
					"dataCadastro": new Date(date.getFullYear(), date.getMonth(), date.getDate())
				};
			}

		})
		.error(function(data, status) {
			alert('Data: ' + data + ' Status: ' + status);
		});


	$scope.removerTelefone = function(index) {
		var elements = [];

		for (var i = 0; i < $scope.usuario.telefones.length; i++) {
			if (i == index) {
				continue;
			} else {
				elements.push($scope.usuario.telefones[i]);
			}
		};
		$scope.usuario.telefones = elements;
	};

	$scope.getCidades = function() {
		ServiceAbstract.get(url_cidade, $scope.usuario.estado.id)
			.success(function(response) {
				$scope.cidades = response;
			}).error(function(response) {
				console.log('error', response);
			});

	};

	$scope.adicionarTelefone = function() {
		$scope.usuario.telefones.push({
			"tipo": "f",
			"telefone": ""
		});
	};

	$scope.uploadFile = function(file) {
		ServiceAbstract.postImage(url + '/uploadImage', file.target.files[0]).success(function(response) {
			if (response.msn == 'true') {
				$scope.usuario.image = response.hash;
				MyStorageService.imagemUsuarioLogado.set(response.hash);
			}
		}).error(function(response) {
			console.log('error', response);
		});
	};

	$scope.adicionarUsuario = function(formValid) {
		if (!formValid) {
			return;
		}

		nome = $scope.usuario.nome;
		$scope.usuario.estado = $scope.usuario.estado.nome;
		$scope.usuario.cidade = $scope.usuario.cidade.nome;
		$scope.usuario.dataNascimento = $filter('date')($scope.usuario.dataNascimento, 'yyyy-MM-dd');

		ServiceAbstract.post(url, $scope.usuario)
			.success(function(data) {
				$scope.usuario.estado = {
					"nome": $scope.usuario.estado
				};
				$scope.usuario.cidade = {
					"nome": $scope.usuario.cidade
				};
				if (MyStorageService.usuarioLogado.get()) {
					$('.modal').addClass("open");
					$('.modal .modal-title').text('Sucesso!');
					$('.modal .modal-message').text('Parabéns, alterado com sucesso!');
					$(".modal .btn.negative, .modal .btn.positive").click(function() {
						$(this).parents(".modal").toggleClass("open");
						window.location.replace("http://localhost:9090/#/app/feed");
					});
				} else {
					$('.modal').addClass("open");
					$('.modal .modal-title').text('Sucesso!');
					$('.modal .modal-message').text('Parabéns, cadastro realizado com sucesso!');
					$(".modal .btn.negative, .modal .btn.positive").click(function() {
						$(this).parents(".modal").toggleClass("open");
						window.location.replace("http://localhost:9090/#/app/login");
					});
				}

			})
			.error(function(data, status) {
				alert('Data: ' + data + ' Status: ' + status);
			});
	};

}]).directive('customOnChange', function() {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) {
			var onChangeHandler = scope.$eval(attrs.customOnChange);
			element.bind('change', onChangeHandler);
		}
	};
});