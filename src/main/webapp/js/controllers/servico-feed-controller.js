app.controller('servicoFeedController', ['$location', '$filter', '$scope', 'ServiceAbstract', '$routeParams', function($location, $filter, $scope, ServiceAbstract, $routeParams) {

  var url = '/servico';

  ServiceAbstract.getAll(url)
    .success(function(data) {
      $scope.feedServico = data;
      
      for (var i=0; i<$scope.feedServico.length; i++) {
        if ($scope.feedServico[i].tipoPeriodo == "d") {
          $scope.feedServico[i].tipoPeriodo = "O DIA";
        } else if ($scope.feedServico[i].tipoPeriodo == 'h') {
          $scope.feedServico[i].tipoPeriodo = 'A HORA';
        } else if ($scope.feedServico[i].tipoPeriodo == 's') {
          $scope.feedServico[i].tipoPeriodo = 'A SEMANA';
        } else {
          $scope.feedServico[i].tipoPeriodo = '';
        }
        
        if ($scope.feedServico[i].titulo.length > 20) {
          $scope.feedServico[i].titulo = $scope.feedServico[i].titulo.substr(0,20) + '..';  
        }

      }
      
    })
    .error(function(data, status) {
      $('.modal').addClass("open");
      var erro = data + ' Status: ' + status;
      $('.modal .modal-title').text('opss...');
      $('.modal .modal-message').text('Ei, ocorreu um erro ao carregar a listagem de serviços!.');
      $(".modal .btn.negative, .modal .btn.positive").click(function(){
        $(this).parents(".modal").toggleClass("open");
      });
    });

    //filtro

    $(".filter .btn").click(function(){
      $(this).parent().find(".filter-content").addClass("active");
    });

    $(".filter-content .btn").click(function(){
      $(this).parents(".filter-content").removeClass("active");
    });

    setTimeout(function(){
      $(".filter-content .switch").click(function() {
        var valorInput = $(this).parent().next(".sisters").find("input");
        $(this).toggleClass("true");
        $(this).parent().next(".sisters").find("span").toggleClass("disabled");

        if (valorInput.val()) {
          $(valorInput).val("");
        }
      });
    },1);

    //sem imagens

    $scope.$watch(function(scope) {

      setTimeout(function(){

        //sem imagem
        $(".feed-itens .col-xs-6").each(function(){
          var este = $(this).find(".white-block");
          var imgItem = este.find("figure").attr("style");
          var categoryItem = este.find(".category-text").text();
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

        //mesma altura
        var bigger = 0;
        var the_height =0;

        $(".feed-itens .col-xs-6").each(function(){
          var self = $(this).find(".feed-iten-content").height();

          if( self > bigger){
            bigger = self;
          }

          self = 0;
        });

        $(".feed-itens .col-xs-6").each(function() {
          $(this).find(".feed-iten-content").height(bigger);

        });

      },1);

    });

}]);