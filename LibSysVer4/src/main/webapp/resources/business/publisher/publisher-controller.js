libSysApp.controller("publisherCtrl", function($scope, $http, $window, $uibModal, pubServ, Pagination){
	
	pubServ.getAllPubsService().then(function(data){
		$scope.publishers = data;
		$scope.doPagination();
	});
	
	$scope.initPublisher = function(){
		pubServ.initPubService().then(function(data){
			$scope.publisher = data;
		});
	};

	$scope.savePublisher = function(){
		$http.post("http://localhost:8080/libsys/admin/addPublisher", $scope.publisher).then(function(){
			pubServ.getAllPubsService().then(function(data){
				$scope.publishers = data;
				$scope.doPagination();
			});
		});
	};
	
	$scope.deletePublisher = function(){
		$http.post("http://localhost:8080/libsys/admin/deletePublisher", $scope.publisher).then(function(){
			pubServ.getAllPubsService().then(function(data){
				$scope.publishers = data;
				$scope.doPagination();
			});
		});
	};
	
	$scope.doPagination = function(){
		$scope.pagination = Pagination.getNew(10);
		$scope.pagination.numPages = Math.ceil($scope.publishers.length / $scope.pagination.perPage);
	}
	
	$scope.showAddPublisherModal = function(){
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "addpublisher.html",
			controller: "addPublisherModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.showEditPublisherModal = function(publisherID){
		pubServ.getPubService(publisherID).then(function(data){
			$scope.publisher = data;
		});
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "editpublisher.html",
			controller: "editPublisherModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.showDelPublisherModal = function(publisherID){
		pubServ.getPubService(publisherID).then(function(data){
			$scope.publisher = data;
		});
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "deletepublisher.html",
			controller: "delPublisherModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.updatePublisher = function(){
		$http.post("http://localhost:8080/libsys/admin/updatePublisher", $scope.publisher).then(function(){
			pubServ.getAllPubsService().then(function(data){
				$scope.publishers = data;
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

libSysApp.controller("addPublisherModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.savePublisher();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});

libSysApp.controller("editPublisherModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.updatePublisher();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});

libSysApp.controller("delPublisherModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.deletePublisher();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});