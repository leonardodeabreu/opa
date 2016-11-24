app.controller('quemFalouComigoController', ['$location','$scope', 'ServiceAbstract', '$routeParams','MyStorageService','$filter' , function($location, $scope, ServiceAbstract, $routeParams, MyStorageService, $filter) {

	var url = '/historicoServico/servicoPorDono',
			url_usuario = '/usuario',
			url_servico = '/servico',
			url_hist_servico = '/historicoServico';


      $scope.naoExisteNada = null;

	ServiceAbstract.get(url, MyStorageService.usuarioLogado.get())
    .success(function(data) {
      $scope.historicoServico = data;

     	for (var i=0; i<$scope.historicoServico.length; i++) {
     		recuperarUsuarioQueRequeriuServico($scope.historicoServico[i].usuarioRequisicao, i);
     		recuperarServicoRequerido($scope.historicoServico[i].servico, i);
        $scope.historicoServico[i].inicio = $filter('date')($scope.historicoServico[i].inicio, 'dd/MM/yyyy');
      }
      if ($scope.historicoServico.length == 0) {
         $scope.naoExisteNada = true;
      }else{
         $scope.naoExisteNada = false;
      }

    })
    .error(function(data, status) {
     	var erro = data + ' Status: ' + status;
			console.log('Erro a carregar a listagem de servicos solicitados ! Erro:' + erro);     
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

  $scope.removerSolicitacao = function(idServico) {
    $('.modal.deletar').addClass("open");
    $('.modal.deletar .modal-title').text('hey calma aí');
    $('.modal.deletar .modal-message').text('tem certeza que deseja cancelar a prestação deste serviço?');
    $(".modal.deletar .btn.nao").click(function(){
      $(".modal").removeClass("open");
    });
    $(".modal.deletar .btn.sim").click(function(){
      $(this).parents(".modal.a").toggleClass("open");
      confirmandoExclusao(idServico);
    });

  }

  var confirmandoExclusao = function(idServico) {
    ServiceAbstract.delete(url_hist_servico, idServico)
      .success( function(data) {
        location.reload();     
      })
      .error( function(data, status) {
        $('.modal.a').addClass("open");
        var erro = data + ' Status: ' + status;
        $('.modal.a .modal-title').text('opss...');
        $('.modal.a .modal-message').text('Ei, ocorreu um erro no cancelament da prestação do serviço!. Erro:' + erro);
        $(".modal.a .btn.negative, .modal .btn.positive").click(function(){
          $(this).parents(".modal.a").toggleClass("open");
        });
      })
    }

  $scope.recuperarObjOriginal = function(historicoServico) {

  	ServiceAbstract.get(url_hist_servico, historicoServico.id)
	    .success(function(data) {
	    	$scope.objetoOriginal = data;
	    	moverParaAndamento($scope.objetoOriginal);  
	    })
	    .error(function(data, status) {
	     	var erro = data + ' Status: ' + status;
				console.log('Erro a carregar o usuario que requeriu o serviço! Erro:' + erro);     
	    });

  }

  var moverParaAndamento = function(historico) {

		ServiceAbstract.modify(url_hist_servico, historico)
      .success( function(data) {
      	$('.modal.a').addClass("open");
        $('.modal.a .modal-title').text('hey!');
        $('.modal.a .modal-message').text('Este serviço agora está em andamento!');
           
      })
      .error( function(data, status) {
        $('.modal.a').addClass("open");
        var erro = data + ' Status: ' + status;
        $('.modal.a .modal-title').text('opss...');
        $('.modal.a .modal-message').text('Ei, ocorreu um erro no cancelament da prestação do serviço!. Erro:' + erro);
      })

    }

  //Controle de elementos da tela

	setTimeout(function(){
		//modal
		$(".modal-opener").click(function(){
			$(this).parents(".pagelist-item").find(".modal").toggleClass("open");
		});

		$(".modal .btn.negative, .modal .btn.positive").click(function(){
			$(this).parents(".modal").toggleClass("open");
		});

		$(".modal .btn.positive").click(function(){
			$(this).parents(".pagelist-item").addClass("next-phase");
		});

    //detalhes
    $(".btn.icon-info").click(function(){
      $(this).toggleClass("open");
      $(this).parents(".white-block").find(".details").slideToggle();
    });
	}, 200);


}]);