libSysApp.controller("libCtrl", function($scope, $rootScope, $http, $uibModal, libServ){
	
	libServ.getBchService($rootScope.branchID).then(function(data){
		$scope.branch = data;
	});
	
	libServ.getAllBooksService().then(function(data){
		$scope.books = data;
	});
	
	$scope.saveCopies = function(){
		$http.post("http://localhost:8080/libsys/lib/addNoOfCopies", $scope.branch).then(function(){
			libServ.getBchService($rootScope.branchID).then(function(data){
				$scope.branch = data;
			});
		});
	};

	$scope.deleteBrBk = function(){
		
		$http.post("http://localhost:8080/libsys/lib/deleteNoOfCopies", $scope.branch).then(function(){
			libServ.getBchService($rootScope.branchID).then(function(data){
				$scope.branch = data;
			});
		});
	};
	
	$scope.showAddBrBkModal = function(){
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "addbranchbook.html",
			controller: "addBrBkModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.showEditBranchModal = function(){

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
	
	$scope.showEditBrBkModal = function(bookID){
		$scope.branch.bookID = bookID;
		libServ.getBookService(bookID).then(function(data){
			$scope.book = data;
		});
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "editbranchbook.html",
			controller: "editBrBkModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.showDelBrBKModal = function(bookID){
		$scope.branch.bookID = bookID;
		libServ.getBookService(bookID).then(function(data){
			$scope.book = data;
		});
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "deletebranchbook.html",
			controller: "delBrBkModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.updateBranch = function(){
		$http.post("http://localhost:8080/libsys/lib/updateBranch", $scope.branch).then(function(){
			libServ.getBchService($rootScope.branchID).then(function(data){
				$scope.branch = data;
			});
		})
	};
	
	$scope.updateBrBk = function(){
		$http.post("http://localhost:8080/libsys/lib/updateNoOfCopies", $scope.branch).then(function(){
			libServ.getBchService($rootScope.branchID).then(function(data){
				$scope.branch = data;
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

libSysApp.controller("addBrBkModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.saveCopies();
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

libSysApp.controller("editBrBkModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.updateBrBk();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});

libSysApp.controller("delBrBkModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.deleteBrBk();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});