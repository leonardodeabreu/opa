app.controller('pedidoCadastroController', ['$location', 'MyStorageService', '$filter', '$scope', 'ServiceAbstract', '$routeParams', function($location, MyStorageService, $filter, $scope, ServiceAbstract, $routeParams) {

  var url = '/pedido',
      url_estado = '/estado',
      url_categoria = '/categoria',
      url_cidade = '/cidade/cidadePorEstado/',
      url_historico_pedido = '/historicoPedido';

  ServiceAbstract.getAll(url_estado)
    .success(function(data) {
      $scope.estados = data;
    })
    .error(function(data, status) {
      alert('Data: ' + data + ' Status: ' + status);
    });

  $scope.cidades = [];

  ServiceAbstract.getAll(url_categoria)
    .success(function(data) {
      $scope.categorias = data;
    })
    .error(function(data, status) {
      alert('Data: ' + data + ' Status: ' + status);
    });

  if ($routeParams.id) {
    ServiceAbstract.get(url, $routeParams.id)
      .success(function(data) {
        data.dataNascimento = new Date(data.dataNascimento);
        $scope.pedido = data;

        $.each($scope.estados, function(index, val) {
          if (val.nome == $scope.pedido.estadoBrasil.nome) {
            ServiceAbstract.get(url_cidade, val.id)
              .success(function(response) {
                $scope.cidades = response;
              }).error(function(response) {
                console.log('error', response);
              });
          }
        });

        $scope.pedido.cidade = {
          "nome": data.cidade
        };
      })
      .error(function(data, status) {
        alert('Data: ' + data + ' Status: ' + status);
      });
  } else {

    $scope.pedido = {
      "titulo": "",
      "descricao": "",
      "valor": null,
      "estado": "ABERTO",
      "periodo": null,
      "tipoPeriodo": "H",
      "dataCadastro": $filter('date')(new Date(), 'yyyy-MM-dd'),
      "estadoBrasil": "",
      "cidadeEstado": "",
      "categoria": {},
      "usuarioId": MyStorageService.usuarioLogado.get()
    };
  }

  $scope.getCidades = function() {
    ServiceAbstract.get(url_cidade, $scope.pedido.estadoBrasil.id)
      .success(function(response) {
        $scope.cidades = response;
      }).error(function(response) {
        console.log('error', response);
      });
  };

  $scope.adicionarPedido = function(formValid) {
    if (!formValid) {
      return;
    }

    $scope.pedido.estadoBrasil = $scope.pedido.estadoBrasil.nome;
    $scope.pedido.cidadeEstado = $scope.pedido.cidadeEstado.nome;

    ServiceAbstract.post(url, $scope.pedido)
      .success(function(data) {
        $scope.pedido.estadoBrasil = {
          "nome": $scope.pedido.estadoBrasil
        };
        $scope.pedido.cidadeEstado = {
          "nome": $scope.pedido.cidadeEstado
        };
        if ($routeParams.id) {
          $('.modal').addClass("open");
          $('.modal .modal-title').text('Sucesso!');
          $('.modal .modal-message').text('Parabéns, alterado com sucesso!');
          $(".modal .btn.negative, .modal .btn.positive").click(function() {
            $(this).parents(".modal").toggleClass("open");
            window.location.replace("http://localhost:9090/#/app/pedido-feed");
          });
        } else {
          $('.modal').addClass("open");
          $('.modal .modal-title').text('Sucesso!');
          $('.modal .modal-message').text('Parabéns, cadastro realizado com sucesso!');
          $(".modal .btn.negative, .modal .btn.positive").click(function() {
            $(this).parents(".modal").toggleClass("open");
            window.location.replace("http://localhost:9090/#/app/pedido-feed");
            inserirStatusNoPedido(data.id);
          });
        }

      })
      .error(function(data, status) {
        alert('Data: ' + data + ' Status: ' + status);
      });
  }

  var inserirStatusNoPedido = function(idPedido) {
    $scope.historicoPedido = {
      "pedido" : idPedido,
      "usuarioRequisicao" : MyStorageService.usuarioLogado.get()
    }

    ServiceAbstract.post(url_historico_pedido, $scope.historicoPedido)
      .success(function(data) {
       
      }).error(function(data, status) {
        var erro = erro + '.Status: ' + status;
        console.log('Erro ao inserir status no serviço ! Erro:'+ erro);
      });
  }

  //controle de elementos da tela

  $(".preco .switch").click(function() {
    $(".preco .switch").toggleClass("true");
    $(".preco").toggleClass("disabled");
    $(".periodo .switch").toggleClass("over");

    if ($(".periodo .switch").hasClass("true") && !$(".periodo").hasClass("disabled")) {
      $(".periodo").addClass("disabled");
      $(".periodo .switch").toggleClass("true");
    }

    if ($('.preco input').val() || $('.periodo input').val()) {
      $('.preco input, .periodo input').val("");
    }
  });
  $(".periodo .switch").click(function() {
    $(".periodo .switch").toggleClass("true");
    $(".periodo").toggleClass("disabled");

    if ($('.periodo input').val()) {
      $('.periodo input').val("");
    }
  });

  $(".endereco .switch").click(function() {
    $(this).toggleClass("true");
    $(this).parents(".endereco").toggleClass("disabled");
  });

}]);