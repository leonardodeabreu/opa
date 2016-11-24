app.service('ServiceAbstract', ['$http', function ($http) {
	return {
		getAll: function (url) {
			return $http.get(url);
		},
		post: function (url, data) {
			return $http.post(url, data);
		},
		postImage: function (url, file) {
			var fd = new FormData();
			fd.append('file', file);
			return $http.post(url, fd, {transformRequest: angular.identity, headers: { 'Content-Type': undefined} });
		},
		get: function(url, id) {
			return $http.get(url +'/'+ id);
		},
		modify: function(url, id) {
			return $http.put(url, id);
		},
		delete: function (url, id) {
			return $http.delete(url +'/'+ id);
		}
	};
}]);