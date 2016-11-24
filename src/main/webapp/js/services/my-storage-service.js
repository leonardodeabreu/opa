app.service('MyStorageService', ['StorageService', function(StorageService) {
  return {
    token: {
      set: function(token) {
        StorageService.session.setItem("TOKEN", token);
      },
      get: function() {
        return StorageService.session.getItem("TOKEN");
      },
      clear: function() {
        StorageService.session.removeItem("TOKEN");
      }
    },
    usuarioLogado: {
      set: function(usuarioLogado) {
        StorageService.local.setItem("USUARIOLOGADO", usuarioLogado);
      },
      get: function() {
        return StorageService.local.getItem("USUARIOLOGADO");
      },
      clear: function() {
        StorageService.local.removeItem("USUARIOLOGADO");
      }
    },
    nomeUsuarioLogado: {
      set: function(nomeUsuarioLogado) {
        StorageService.local.setItem("NOMEUSUARIOLOGADO", nomeUsuarioLogado);
      },
      get: function() {
        return StorageService.local.getItem("NOMEUSUARIOLOGADO");
      },
      clear: function() {
        StorageService.local.removeItem("NOMEUSUARIOLOGADO");
      }
    },
    imagemUsuarioLogado: {
      set: function(imagemUsuarioLogado) {
        StorageService.local.setItem("IMGUSUARIOLOGADO", imagemUsuarioLogado);
      },
      get: function() {
        return StorageService.local.getItem("IMGUSUARIOLOGADO");
      },
      clear: function() {
        StorageService.local.removeItem("IMGUSUARIOLOGADO");
      }
    }

  };
}]);