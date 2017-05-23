libSysApp.controller("authorCtrl", function($scope, $http, $window, $uibModal, authorServ, Pagination){
	
	authorServ.getAllAuthorsService().then(function(data){
		$scope.authors = data;
		$scope.doPagination();
	});
	
	$scope.initAuthor = function(){
		authorServ.initAuthorService().then(function(data){
			$scope.author = data;
		});
	};

	$scope.saveAuthor = function(){
		$http.post("http://localhost:8080/libsys/admin/addAuthor", $scope.author).then(function(){
			authorServ.getAllAuthorsService().then(function(data){
				$scope.authors = data;
				$scope.doPagination();
			});
		});
	};
	
	$scope.deleteAuthor = function(){
		$http.post("http://localhost:8080/libsys/admin/deleteAuthor", $scope.author).then(function(){
			authorServ.getAllAuthorsService().then(function(data){
				$scope.authors = data;
				$scope.doPagination();
			});
		});
	};
	
	$scope.doPagination = function(){
		$scope.pagination = Pagination.getNew(10);
		$scope.pagination.numPages = Math.ceil($scope.authors.length / $scope.pagination.perPage);
	}
	
	$scope.showAddAuthorModal = function(){
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "addauthor.html",
			controller: "addAuthorModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.showEditAuthorModal = function(authorID){
		authorServ.getAuthorService(authorID).then(function(data){
			$scope.author = data;
		});
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "editauthor.html",
			controller: "editAuthorModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.showDelAuthorModal = function(authorID){
		authorServ.getAuthorService(authorID).then(function(data){
			$scope.author = data;
		});
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "deleteauthor.html",
			controller: "delAuthorModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.updateAuthor = function(){
		$http.post("http://localhost:8080/libsys/admin/updateAuthor", $scope.author).then(function(){
			authorServ.getAllAuthorsService().then(function(data){
				$scope.authors = data;
				$scope.doPagination();
			});
		})
	};
	
	$scope.searchAuthor = function(){
		authorServ.searchAuthorService($scope.searchAuthorName).then(function(data){
			$scope.authors = data;
			$scope.doPagination();
		});
	}
});

libSysApp.controller("addAuthorModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.saveAuthor();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});

libSysApp.controller("editAuthorModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.updateAuthor();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});

libSysApp.controller("delAuthorModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.deleteAuthor();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});