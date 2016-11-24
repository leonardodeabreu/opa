app.controller('servicoCadastroController', ['$location', '$filter', '$scope', 'ServiceAbstract', '$routeParams', 'MyStorageService', function($location, $filter, $scope, ServiceAbstract, $routeParams, MyStorageService) {

  var url = '/servico',
    url_estado = '/estado',
    url_cidade = '/cidade/cidadePorEstado';

  ServiceAbstract.getAll('/categoria')
    .success(function(data) {
      $scope.categorias = data;
    });

  ServiceAbstract.getAll(url_estado)
    .success(function(data) {
      $scope.estados = data;

      if ($routeParams.id) {
        ServiceAbstract.get(url, $routeParams.id)
          .success(function(data) {
            $scope.servico = data;
            $scope.servico.estado = {
              "nome": data.estado
            };

            $scope.servico.categoria = {
              "nome": data.categoria
            }

            $.each($scope.estados, function(index, val) {
              if (val.nome == $scope.servico.estado.nome) {
                ServiceAbstract.get(url_cidade, val.id)
                  .success(function(response) {
                    $scope.cidades = response;
                  }).error(function(response) {
                    console.log('error', response);
                  });
              }
            });

            $scope.servico.cidade = {
              "nome": data.cidade
            };

          })
          .error(function(data, status) {
            alert('Data: ' + data + ' Status: ' + status);
          });
      } else {
        $scope.servico = {
          "titulo": "",
          "categoria": {},
          "descricao": "",
          "image": "",
          "image2": "",
          "image3": "",
          "image4": "",
          "valor": "",
          "tipoPeriodo": "",
          "estado": {},
          "cidade": {}
        };
      }
    })
    .error(function(data, status) {
      alert('Data: ' + data + ' Status: ' + status);
    });


  $scope.adicionarServico = function(formValid) {
    if (!formValid) {
      return;
    }

    $scope.servico.categoria = $scope.servico.categoria.nome;
    $scope.servico.estado = $scope.servico.estado.nome;
    $scope.servico.cidade = $scope.servico.cidade.nome;
    $scope.servico.usuario = MyStorageService.usuarioLogado.get();

    ServiceAbstract.post(url, $scope.servico)
      .success(function(data) {
        //Modal
        $('.modal').addClass("open");
        $('.modal .modal-message').text("Serviço cadastrado com sucesso!");
        $('.modal .btn.positive').attr("href", "#app/feed");

        $(".modal .btn.positive").click(function() {
          $(this).parents(".modal").toggleClass("open");
        });

      })
      .error(function(data, status) {
        $('.modal').addClass("open");
        var erro = data + ' Status: ' + status;
        error = "Ei, você precisa preencher todos os dados"
        $('.modal .modal-message').text(error + '. Erro:' + erro);

        $(".modal .btn.negative, .modal .btn.positive").click(function() {
          $(this).parents(".modal").toggleClass("open");
        });
      });
  };

  $scope.uploadFile = function(file) {
    var id = file.target.id;

    ServiceAbstract.postImage(url + '/uploadImage', file.target.files[0]).success(function(response) {
      if (response.msn == 'true') {
        if (id == '1') {
          $scope.servico.image = response.hash;
        }
        if (id == '2') {
          $scope.servico.image2 = response.hash;
        }
        if (id == '3') {
          $scope.servico.image3 = response.hash;
        }
        if (id == '4') {
          $scope.servico.image4 = response.hash;
        }
      }
    }).error(function(response) {
      console.log('error', response);
    });
  };

  $scope.getCidades = function(valorCombo) {
    $scope.cidades = null;
    carregarCidades(valorCombo.id);
  }

  var carregarCidades = function(idEstado) {
    ServiceAbstract.getAll('/cidade/cidadePorEstado/' + idEstado)
      .success(function(data) {
        $scope.cidades = data;
      });
  }

  $scope.formataValorMonetario = function(valorElemento) {
    var valorDoElemento = valorElemento

    valorElemento = valorElemento.replace(/\D/g, ""); // permite digitar apenas numero
    valorElemento = valorElemento.replace(/(\d{1})(\d{14})$/, "$1.$2"); // coloca ponto antes dos ultimos digitos
    valorElemento = valorElemento.replace(/(\d{1})(\d{11})$/, "$1.$2"); // coloca ponto antes dos ultimos 11 digitos
    valorElemento = valorElemento.replace(/(\d{1})(\d{8})$/, "$1.$2"); // coloca ponto antes dos ultimos 8 digitos
    valorElemento = valorElemento.replace(/(\d{1})(\d{5})$/, "$1.$2"); // coloca ponto antes dos ultimos 5 digitos
    valorElemento = valorElemento.replace(/(\d{1})(\d{1,2})$/, "$1,$2"); // coloca virgula antes dos ultimos 2 digitos

    return valorElemento;
  }

  //Controle de elementos da tela de cadastro de serviço

  $("#categoria").keypress(function() {
    $("#categoria").append("<ul>OLA</ul")
  });

  $(".preco .switch").click(function() {
    $(".preco .switch").toggleClass("true");
    $(".preco").toggleClass("disabled");
    $(".periodo .switch").toggleClass("over");

    if ($(".preco input, .periodo select").attr("disabled")) {

      $(".preco input, .periodo select").removeAttr("disabled");
    } else {
      $(".preco input, .periodo select").attr("disabled");
    }


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

}]);