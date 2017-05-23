libSysApp.controller("genreCtrl", function($scope, $http, $window, $uibModal, genreServ, Pagination){
	
	genreServ.getAllGenresService().then(function(data){
		$scope.genres = data;
		$scope.doPagination();
	});
	
	$scope.initGenre = function(){
		genreServ.initGenreService().then(function(data){
			$scope.genre = data;
		});
	};

	$scope.saveGenre = function(){
		$http.post("http://localhost:8080/libsys/admin/addGenre", $scope.genre).then(function(){
			genreServ.getAllGenresService().then(function(data){
				$scope.genres = data;
				$scope.doPagination();
			});
		});
	};
	
	$scope.deleteGenre = function(){
		$http.post("http://localhost:8080/libsys/admin/deleteGenre", $scope.genre).then(function(){
			genreServ.getAllGenresService().then(function(data){
				$scope.genres = data;
				$scope.doPagination();
			});
		});
	};
	
	$scope.doPagination = function(){
		$scope.pagination = Pagination.getNew(10);
		$scope.pagination.numPages = Math.ceil($scope.genres.length / $scope.pagination.perPage);
	}
	
	$scope.showAddGenreModal = function(){
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "addgenre.html",
			controller: "addGenreModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.showEditGenreModal = function(genreID){
		genreServ.getGenreService(genreID).then(function(data){
			$scope.genre = data;
		});
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "editgenre.html",
			controller: "editGenreModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.showDelGenreModal = function(genreID){
		genreServ.getGenreService(genreID).then(function(data){
			$scope.genre = data;
		});
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "deletegenre.html",
			controller: "delGenreModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.updateGenre = function(){
		$http.post("http://localhost:8080/libsys/admin/updateGenre", $scope.genre).then(function(){
			genreServ.getAllGenresService().then(function(data){
				$scope.genres = data;
				$scope.doPagination();
			});
		})
	};
	
/*	$scope.searchAuthor = function(){
		authorServ.searchAuthorService($scope.searchAuthorName).then(function(data){
			$scope.authors = data;
			$scope.doPagination();
		});
	}*/
});

libSysApp.controller("addGenreModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.saveGenre();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});

libSysApp.controller("editGenreModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.updateGenre();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});

libSysApp.controller("delGenreModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.deleteGenre();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});