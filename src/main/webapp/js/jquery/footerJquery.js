app.controller("footerController", ["$scope", "$location", "$routeParams", function($scope, $location, $routeParams) {

	$scope.$watch(function(scope) {
		setTimeout(function(){

			function footer(){
				var footer_height = $(".footer").outerHeight();
				var header_height = $(".header").outerHeight();
				var result = footer_height + header_height;
				$(".standard").css("min-height", "calc(100vh - " + result + "px)");
			}

			footer();

			$(window).resize(function(){
			  footer();
			});

		}, 1);

		var path = $location.path();
		path = path.replace(/\//g, '');
		path = path.replace(/\app/g, '');
		$("body").attr("class", path);

		if (path == "pedido-feed") {
			path = "Pedidos de Serviços";
		} else if (path == "feed") {
			path = "Serviços Disponíveis";
		} else if (path == "servico-cadastro") {
			path = "Anunciar Serviço";
		} else if (path == "usuario") {
			path = "Perfil";
		} else if (path == "servico-detalhes") {
			path = "Serviço";
		} else if (path == "servico-concluido-prestador" || path == "servico-concluido-solicitante") {
			path = "Serviços Concluídos";
		} else if (path == "servico-aberto-prestador") {
			path = "Serviços em Andamento";
		} else if (path == "servico-aberto-solicitante") {
			path = "Pedidos de Serviços em Andamento";
		} else if (path == "servico-gerenciar-prestador") {
			path = "Gerenciar Serviços";
		} else if (path == "pedido-nao-atendido") {
			path = "Pedido de Serviços Não Atendidos";
		}

		path = path.replace(/\-/g, ' ');

		path = path.substr(0, 1).toUpperCase() + path.substr(1);
		$("title").text("OPA | " + path);
	});

	//Jquery

	setTimeout(function() {
		//Funções Globais do Menu

		function blocker(item) {
			if ($("body").hasClass("blocker")) {
				$("body").removeClass("blocker");
			} else {
				$("body").addClass("blocker");
			}
			if (item == "close") {
				$("body").removeClass("blocker");
			}
		}

		// $(".tab-toggle").click(
		// 	function() {
		// 		parentTab = $(this).parent().attr("class");
		// 		tab = $(this).parents("body").find(".navigation-bars .nav-" + parentTab);
		// 		$(tab).toggleClass("open");
		// 		blocker();
		// 	}
		// );

		$(".header .user").click(
			function() {				
				$(".nav-user").toggleClass("open");
				blocker();
			}
		);

		$(".header .categories").click(
			function() {				
				$(".nav-categories").toggleClass("open");
				blocker();
			}
		);

		$(document).click(
			function(e) {
				target = $(e.target).context.className;
				tab1 = ".nav-categories.open";
				tab2 = ".nav-user.open";

				//if( target != tab1 || target != tab2 || target != "tab-toggle"){
				if (target != "tab-toggle" && $(tab1).has(e.target).length === 0 && $(tab2).has(e.target).length === 0) {
					$(".nav-categories.open, .nav-user.open").removeClass("open");
					blocker("close");
				}
			}
		);

		//CATEGORIAS

		//categoria-web

		function categoriaWeb() {
			var windowWidth = $(window).width();

			if (windowWidth > 992) {
				$(".nav-categories ul").addClass("container");
			} else {
				$(".nav-categories ul").removeClass("container");
			}
		}

		categoriaWeb();

		$(window).resize(function() {
			categoriaWeb();
		});


		// esconder aba categoria ao clicar seta
		$(".nav-categories h2").click(function() {
			$(this).parent().removeClass("open");
			blocker();
		});

		//comportamento aba categoria
		$(".nav-categories li ul").prepend("<span class='sub-categorie-title'></span>");

		$(".nav-categories li ul .sub-categorie-title").click(function() {
			$(this).parent().removeClass("open");
		});

		$(".nav-categories li").has("ul").addClass("has-ul").prepend("<span class='dropdown'></span>");

		$(".nav-categories .has-ul .dropdown").click(function() {
			$(this).parent().find("ul").addClass("open");
		});

		//USUÁRIO

		function navUserPosition() {
			var windowWidth = $(window).width();

			if (windowWidth > 992) {
				var container = $(".container").width();
				var pos = (windowWidth - container) / 2;
				$(".nav-user").css("right", pos - 15);
			}
		}

		navUserPosition();
		$(window).resize(function() {
			navUserPosition();
		});

		$(".nav-user li.has-ul").click(function() {
			$(this).find("ul").slideToggle();
			$(this).toggleClass("active");
		});

		//fechar tab ao clicar nos itens
		$(".nav-user ul li:not(:has(ul))").click(function() {
			$(".nav-user").removeClass("open");
			blocker();
		});

		//fechar tab ao clicar na imagem
		$(".nav-user article").click(function() {
			$(".nav-user").removeClass("open");
			blocker();
		});

	}, 1); //timeout


}]); //end