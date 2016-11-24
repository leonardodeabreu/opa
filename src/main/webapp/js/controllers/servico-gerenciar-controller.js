app.controller('servicoGerenciarController', ['$location', '$filter', '$scope', 'ServiceAbstract', '$routeParams','MyStorageService', function($location, $filter, $scope, ServiceAbstract, $routeParams, MyStorageService) {

  var url = '/servico/servicoPorDono',
      idUsuarioLogado = MyStorageService.usuarioLogado.get(),
      url_servico = '/servico';

  ServiceAbstract.get(url, idUsuarioLogado)
    .success(function(data) {
      $scope.servicoGerenciado = data;
    })
    .error(function(data, status) {
      $('.modal.a').addClass("open");
      var erro = data + ' Status: ' + status;
      $('.modal.a .modal-title').text('opss...');
      $('.modal.a .modal-message').text('Ei, ocorreu um erro ao carregar a listagem de serviços!.');
      $(".modal.a .btn.negative, .modal .btn.positive").click(function(){
        $(this).parents(".modal.a").toggleClass("open");
      });
    });  


  $scope.deletarServico = function(idServico) {
    $('.modal.deletar').addClass("open");
    $('.modal.deletar .modal-title').text('hey calma aí');
    $('.modal.deletar .modal-message').text('tem certeza que deseja deletar este serviço?');
    $(".modal.deletar .btn.nao").click(function(){
      $(this).parents(".modal.deletar").toggleClass("open");
    });
    $(".modal.deletar .btn.sim").click(function(){
      $(this).parents(".modal.deletar").toggleClass("open");
      confirmandoExclusao(idServico);
    });

  }

  var confirmandoExclusao = function(idServico) {
    ServiceAbstract.delete(url_servico, idServico)
      .success( function(data) {
        location.reload();
     
      })
      .error( function(data, status) {
        $('.modal.a').addClass("open");
        var erro = data + ' Status: ' + status;
        $('.modal.a .modal-title').text('opss...');
        $('.modal.a .modal-message').text('Ei, ocorreu um erro ao excluir o serviço!. Erro:' + erro);
        $(".modal.a .btn.negative, .modal .btn.positive").click(function(){
          $(this).parents(".modal.a").toggleClass("open");
        });
      })
    }

  //controlar elementos

  $(".pagelist-item-buttons .btn.negative").click(function(){
    $(this).parents(".pagelist-item").remove();
  });

  $scope.$watch(function(scope) {
      setTimeout(function(){

        $(".pagelist-list .col-sm-6").each(function(){

          var este = $(this).find(".white-block");
          var imgItem = este.find("figure").attr("style");
          var categoryItem = este.find("figure").attr("category");
          categoryItem = categoryItem.replace(/,/g,'');
          categoryItem = categoryItem.replace(/\s/g,'');
          categoryItem = categoryItem.toLowerCase();

          if(imgItem == "background-image: url();" && categoryItem == "alugueldelocais"){
            este.find("figure").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
          }
          if(imgItem == "background-image: url();" && categoryItem == "animais"){
            este.find("figure").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
          }          
          if(imgItem == "background-image: url();" && categoryItem == "belezasaúdeemoda"){
            este.find("figure").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
          }
          if(imgItem == "background-image: url();" && categoryItem == "casamóveisedecoração"){
            este.find("figure").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
          }
          if(imgItem == "background-image: url();" && categoryItem == "educaçãoetreinamento"){
            este.find("figure").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
          }
          if(imgItem == "background-image: url();" && categoryItem == "festaseeventos"){
            este.find("figure").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
          }
          if(imgItem == "background-image: url();" && categoryItem == "alimentosegastronomia"){
            este.find("figure").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
          }
          if(imgItem == "background-image: url();" && categoryItem == "tecnologiaesuportetécnico"){
            este.find("figure").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
          }
          if(imgItem == "background-image: url();" && categoryItem == "transporte"){
            este.find("figure").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
          }
          if(imgItem == "background-image: url();" && categoryItem == "outras"){
            este.find("figure").attr("style","background-image: url(img/misc/noimg-"+categoryItem+".png);");
          }


        });

      }, 200);
    });


}]);