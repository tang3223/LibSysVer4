libSysApp.controller("branchCtrl", function($scope, $http, $window, $uibModal, bchServ, Pagination){
	
	bchServ.getAllBchsService().then(function(data){
		$scope.branchs = data;
		$scope.doPagination();
	});
	
	$scope.initBranch = function(){
		bchServ.initBchService().then(function(data){
			$scope.branch = data;
		});
	};

	$scope.saveBranch = function(){
		$http.post("http://localhost:8080/libsys/admin/addBranch", $scope.branch).then(function(){
			bchServ.getAllBchsService().then(function(data){
				$scope.branchs = data;
				$scope.doPagination();
			});
		});
	};
	
	$scope.deleteBranch = function(){
		$http.post("http://localhost:8080/libsys/admin/deleteBranch", $scope.branch).then(function(){
			bchServ.getAllBchsService().then(function(data){
				$scope.branchs = data;
				$scope.doPagination();
			});
		});
	};
	
	$scope.doPagination = function(){
		$scope.pagination = Pagination.getNew(10);
		$scope.pagination.numPages = Math.ceil($scope.branchs.length / $scope.pagination.perPage);
	}
	
	$scope.showAddBranchModal = function(){
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "addbranch.html",
			controller: "addBranchModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.showEditBranchModal = function(branchID){
		bchServ.getBchService(branchID).then(function(data){
			$scope.branch = data;
		});
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "editbranch.html",
			controller: "editBranchModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.showDelBranchModal = function(branchID){
		bchServ.getBchService(branchID).then(function(data){
			$scope.branch = data;
		});
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "deletebranch.html",
			controller: "delBranchModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.updateBranch = function(){
		$http.post("http://localhost:8080/libsys/admin/updateBranch", $scope.branch).then(function(){
			bchServ.getAllBchsService().then(function(data){
				$scope.branchs = data;
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

libSysApp.controller("addBranchModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.saveBranch();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});

libSysApp.controller("editBranchModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.updateBranch();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});

libSysApp.controller("delBranchModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.deleteBranch();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});