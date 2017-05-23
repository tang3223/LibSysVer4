libSysApp.controller("mainCtrl", function($scope, $http, $rootScope, bchServ, $uibModal){
	
	$rootScope.failIDCheck = true;
	
	bchServ.getAllBchsService().then(function(data){
		$scope.branchs = data;
	});
	
	$scope.checkBor = function(borrowerID){
		$scope.showPass = false;
		$scope.showError = false;
		$http.get("http://localhost:8080/libsys/bor/checkID/" + borrowerID).then(function(response){
			if (!response.data) {
				$scope.showError = true;
				$scope.errorMessage = "Card number not exist!";
				$rootScope.failIDCheck = true;
			}
			else {
				$scope.showPass = true;
				$rootScope.borrowerID = borrowerID;
				$rootScope.failIDCheck = false;
			}
		})
	}
	
	$scope.showLibrarianModal = function(){
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "librarian.html",
			controller: "libModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.showBorrowerModal = function(){
		$scope.showPass = false;
		$scope.showError = false;
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "brselectbranch.html",
			controller: "borModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	
});

libSysApp.controller("libModalCtrl", function($scope, $window, $rootScope, $uibModalInstance) {
	$scope.ok = function(){
		$rootScope.branchID = $scope.branchID;
		$window.location.href = '#/library';
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});

libSysApp.controller("borModalCtrl", function($scope, $window, $rootScope, $uibModalInstance) {
	$scope.check = function(borrowerID){
		$scope.checkBor(borrowerID)
	};
	
	$scope.ok = function(){
		if ($scope.isCheckOut){
			$window.location.href = '#/bor/checkbook';
		}
		else {
			$window.location.href = '#/bor/returnbook';
		}
		$rootScope.branchID = $scope.branchID;
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$rootScope.failIDCheck = true;
		$uibModalInstance.dismiss("cancel");
	};
});