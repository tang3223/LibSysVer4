libSysApp.controller("ckrtCtrl", function($scope, $rootScope, $http, $uibModal, ckrtServ){
	
	$scope.branchID = $rootScope.branchID;
	$scope.borrowerID = $rootScope.borrowerID;
	
	ckrtServ.getAllBooksService($scope.branchID).then(function(data){
		$scope.branch = data;
	});
	
	ckrtServ.getLoanService($scope.borrowerID).then(function(data){
		$scope.bookLoanReturn = data;
	});
	
	ckrtServ.initBookLoanService().then(function(data){
		$scope.bookLoan = data;
	});
	
	ckrtServ.initBookLoanService().then(function(data){
		$scope.bookLoanCom = data;
	});
	
	$scope.checkBook = function(){
		$scope.bookLoan.bookID = $scope.book.bookId;
		$scope.bookLoan.book = $scope.book;
		$scope.bookLoan.branchID = $scope.branchID;
		$scope.bookLoan.branch = $scope.branch;
		$scope.bookLoan.borrowerID = $scope.borrowerID;
		$http.post("http://localhost:8080/libsys/bor/checkOutBook", $scope.bookLoan).then(function(){
			ckrtServ.getAllBooksService($scope.branchID).then(function(data){
				$scope.branch = data;
			});
		});
	};
	
	$scope.showCheckBookModal = function(bookID){
		ckrtServ.getBookService(bookID).then(function(data){
			$scope.book = data;
		});
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "confirmcheck.html",
			controller: "checkBookModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.showReturnBookModal = function(bookID, bl){
		$scope.bookLoanCom = bl;
		ckrtServ.getBookService(bookID).then(function(data){
			$scope.book = data;
		});
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "confirmreturn.html",
			controller: "returnBookModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.returnBook = function(){	
/*		$scope.bookLoanCom.bookID = $scope.book.bookId;
		$scope.bookLoanCom.book = $scope.book;
		$scope.bookLoanCom.branchID = $scope.branchID;
		$scope.bookLoanCom.branch = $scope.branch;
		$scope.bookLoanCom.borrowerID = $scope.borrowerID;
		$scope.bookLoanCom.dateOut = $scope.dateOut;*/
		$http.post("http://localhost:8080/libsys/bor/returnBook", $scope.bookLoanCom).then(function(){
			ckrtServ.getLoanService($scope.borrowerID).then(function(data){
				$scope.bookLoanReturn = data;
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

libSysApp.controller("checkBookModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.checkBook();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});

libSysApp.controller("returnBookModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.returnBook();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});

