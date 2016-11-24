app.controller('servicoDetalhesController', ['$location', '$filter', '$scope', 'ServiceAbstract', '$routeParams', 'MyStorageService', function($location, $filter, $scope, ServiceAbstract, $routeParams, MyStorageService) {

  var url = '/servico',
    url_usuario = '/usuario',
    url_hist_servico = '/historicoServico',
    jaClicou = false;

  $scope.image = MyStorageService.imagemUsuarioLogado.get() ? MyStorageService.imagemUsuarioLogado.get() : image;

  ServiceAbstract.get(url, $routeParams.id)
    .success(function(data) {
      $scope.servicoDetalhes = data;

      $scope.servicoDetalhes.image = data.image;
      $scope.servicoDetalhes.image2 = data.image2;
      $scope.servicoDetalhes.image3 = data.image3;
      $scope.servicoDetalhes.image4 = data.image4;


      if ($scope.servicoDetalhes.tipoPeriodo == "d") {
        $scope.servicoDetalhes.tipoPeriodo = "O DIA";
      } else
      if ($scope.servicoDetalhes.tipoPeriodo == 'h') {
        $scope.servicoDetalhes.tipoPeriodo = 'A HORA';
      } else if ($scope.servicoDetalhes.tipoPeriodo == 's') {
        $scope.servicoDetalhes.tipoPeriodo = 'A SEMANA';
      } else {
        $scope.servicoDetalhes.tipoPeriodo = '';
      }

      if ($scope.servicoDetalhes.valor == null || $scope.servicoDetalhes.valor == undefined) {
        $scope.servicoDetalhes.valor = 'false';
      }

      ServiceAbstract.get(url_usuario, $scope.servicoDetalhes.usuario)
        .success(function(data) {
          $scope.donoDoServico = data;
          $scope.donoDoServico.nome = $scope.donoDoServico.nome.split(" ")[0];

        })
        .error(function(data, status) {
          console.log('Erro ao carregar usuário dono do serviço!' + data + '. Status: ' + status);
        });

    })
    .error(function(data, status) {
      $('.modal').addClass("open");
      var erro = data + ' Status: ' + status;
      $('.modal .modal-message').text('Ei, ocorreu um erro ao carregar o detalhe de serviço!');
      $(".modal .btn.negative, .modal .btn.positive").click(function() {
        $(this).parents(".modal").toggleClass("open");
      });
    });

  $scope.verificaSeLogado = function() {

    if (MyStorageService.usuarioLogado.get() == null) {

      $('.modal').addClass("open");
      $('.modal .modal-message').text("eei! Para começar a utilizar o OPA você deve primeiro se logar! :)");
      $('.modal .btn.positive').attr("href", "#app/login");

      $(".modal .btn.positive").click(function() {
        $(this).parents(".modal").toggleClass("open");
      });
    } else {
      $(".telephone").removeClass("open");
      $(".telephone").addClass("open");
      disparaEventoServicoSolicitado();
    }
  }

  var disparaEventoServicoSolicitado = function() {
    $scope.historicoServico = {
      "usuarioRequisicao": MyStorageService.usuarioLogado.get(),
      "servico": $routeParams.id
    }

    ServiceAbstract.post(url_hist_servico, $scope.historicoServico)
      .success(function(data) {
        $scope.jaClicou = true;
      })
      .error(function(data, status) {
        console.log("Erro ao gerar a solicitação de serviço! Erro:" + erro + ", status:" + status);
      })
  }

  //botão voltar
  $(".telephone .back").click(function(){
    $(this).parent().removeClass("open");
  })


  // controlando itens Slide
  setTimeout(function() {
    $("#servico-detalhes-slider").bxSlider({
      minSlides: 1,
      maxSlides: 4,
      pager: false,
      moveSlides: 1
    });
  }, 20);


  //sem imagem
  setTimeout(function(){

    $("ul#servico-detalhes-slider li").each(function(){
      var este = $(this);
      var imgItem = este.find(".inner").attr("style");
      var categoryItem = este.parents(".servico-detalhes").find(".servico-detalhes-category").text();
      categoryItem = categoryItem.replace(/,/g,'');
      categoryItem = categoryItem.replace(/\s/g,'');
      categoryItem = categoryItem.toLowerCase();

      if(imgItem == "background-image: url()" && categoryItem == "alugueldelocais"){
        este.find(".inner").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
      }
      if(imgItem == "background-image: url()" && categoryItem == "animais"){
        este.find(".inner").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
      }          
      if(imgItem == "background-image: url()" && categoryItem == "belezasaúdeemoda"){
        este.find(".inner").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
      }
      if(imgItem == "background-image: url()" && categoryItem == "casamóveisedecoração"){
        este.find(".inner").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
      }
      if(imgItem == "background-image: url()" && categoryItem == "educaçãoetreinamento"){
        este.find(".inner").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
      }
      if(imgItem == "background-image: url()" && categoryItem == "festaseeventos"){
        este.find(".inner").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
      }
      if(imgItem == "background-image: url()" && categoryItem == "alimentosegastronomia"){
        este.find(".inner").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
      }
      if(imgItem == "background-image: url()" && categoryItem == "tecnologiaesuportetécnico"){
        este.find(".inner").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
      }
      if(imgItem == "background-image: url()" && categoryItem == "transporte"){
        este.find(".inner").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
      }
      if(imgItem == "background-image: url()" && categoryItem == "outras"){
        este.find(".inner").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
      }
    });

  },20);

}]);