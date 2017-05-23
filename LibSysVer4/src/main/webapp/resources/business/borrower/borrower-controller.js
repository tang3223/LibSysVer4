libSysApp.controller("borrowerCtrl", function($scope, $http, $window, $uibModal, borServ, Pagination){
	
	borServ.getAllBorsService().then(function(data){
		$scope.borrowers = data;
		$scope.doPagination();
	});
	
	$scope.initBorrower = function(){
		borServ.initBorService().then(function(data){
			$scope.borrower = data;
		});
	};

	$scope.saveBorrower = function(){
		$http.post("http://localhost:8080/libsys/admin/addBorrower", $scope.borrower).then(function(){
			borServ.getAllBorsService().then(function(data){
				$scope.borrowers = data;
				$scope.doPagination();
			});
		});
	};
	
	$scope.deleteBorrower = function(){
		$http.post("http://localhost:8080/libsys/admin/deleteBorrower", $scope.borrower).then(function(){
			borServ.getAllBorsService().then(function(data){
				$scope.borrowers = data;
				$scope.doPagination();
			});
		});
	};
	
	$scope.doPagination = function(){
		$scope.pagination = Pagination.getNew(10);
		$scope.pagination.numPages = Math.ceil($scope.borrowers.length / $scope.pagination.perPage);
	}
	
	$scope.showAddBorrowerModal = function(){
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "addborrower.html",
			controller: "addBorrowerModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.showEditBorrowerModal = function(borrowerID){
		borServ.getBorService(borrowerID).then(function(data){
			$scope.borrower = data;
		});
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "editborrower.html",
			controller: "editBorrowerModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.showDelBorrowerModal = function(borrowerID){
		borServ.getBorService(borrowerID).then(function(data){
			$scope.borrower = data;
		});
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "deleteborrower.html",
			controller: "delBorrowerModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.updateBorrower = function(){
		$http.post("http://localhost:8080/libsys/admin/updateBorrower", $scope.borrower).then(function(){
			borServ.getAllBorsService().then(function(data){
				$scope.borrowers = data;
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

libSysApp.controller("addBorrowerModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.saveBorrower();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});

libSysApp.controller("editBorrowerModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.updateBorrower();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});

libSysApp.controller("delBorrowerModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.deleteBorrower();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});