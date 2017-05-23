libSysApp.controller("bookloanCtrl", function($scope, $http, $window, $uibModal, bklnServ, Pagination){
	
	bklnServ.getAllBklnService().then(function(data){
		$scope.bookloans = data;
		$scope.doPagination();
	});
	
	bklnServ.initBklnService().then(function(data){
		$scope.bookloan = data;
	});
	
	$scope.doPagination = function(){
		$scope.pagination = Pagination.getNew(10);
		$scope.pagination.numPages = Math.ceil($scope.bookloans.length / $scope.pagination.perPage);
	}
	
	$scope.showEditBookLoanModal = function(bookId, branchID, borrowerId, dateout){
		$scope.bookloan.bookID = bookId;
		$scope.bookloan.branchID = branchID;
		$scope.bookloan.borrowerID = borrowerId;
		$scope.bookloan.dateOut = dateout;
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "editloan.html",
			controller: "editLoanModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.updateBookLoan = function(){
		var date = $scope.bookloan.dueDate;
		$scope.bookloan.dueDate = date.getTime();
		$http.post("http://localhost:8080/libsys/admin/updateDueDate", $scope.bookloan).then(function(){
			bklnServ.getAllBklnService().then(function(data){
				$scope.bookloans = data;
				$scope.doPagination();
			});
		})
	};
	
	$scope.dateOption = {
		    minDate: new Date(),
		    showWeeks: false
		  };
	
/*	$scope.searchAuthor = function(){
		authorServ.searchAuthorService($scope.searchAuthorName).then(function(data){
			$scope.authors = data;
			$scope.doPagination();
		});
	}*/
});



libSysApp.controller("editLoanModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.updateBookLoan();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});
