libSysApp.controller("bookCtrl", function($scope, $http, $window, $uibModal, bookServ, authorServ, Pagination){
	
	$scope.multiSetting = {
	    display: {
	        fields: ['authorName']
	      },
	      select: {
	        fields: ['authorID', 'authorName']
	      }
	};
	
	bookServ.getAllBooksService().then(function(data){
		$scope.books = data;
		$scope.doPagination();
	});
	
	$scope.forceRefresh = function(){
		bookServ.getAllBooksService().then(function(data){
			$scope.books = data;
			$scope.doPagination();
		});
	}
	
	$scope.initBook = function(){
		bookServ.initBookService().then(function(data){
			$scope.book = data;
		});
	};

	$scope.saveBook = function(){
		$http.post("http://localhost:8080/libsys/admin/addBook", $scope.book).then(function(){
			bookServ.getAllBooksService().then(function(data){
				$scope.books = data;
				$scope.doPagination();
			});
		});
	};
	
	$scope.deleteBook = function(){
		$http.post("http://localhost:8080/libsys/admin/deleteBook", $scope.book).then(function(){
			bookServ.getAllBooksService().then(function(data){
				$scope.books = data;
				$scope.doPagination();
			});
		});
	};
		
	$scope.doPagination = function(){
		$scope.pagination = Pagination.getNew(10);
		$scope.pagination.numPages = Math.ceil($scope.books.length / $scope.pagination.perPage);
	};

	$scope.showAddBookModal = function(){
		/*$http.get("http://localhost:8080/libsys/admin/viewAuthors").then(function(response){*/
		$http.get("http://localhost:8080/libsys/admin/viewAuthorsOnly").then(function(response){
			$scope.authors = response.data;
/*			for (i=0; i<$scope.authors.length; i++){
				delete $scope.authors[i].books;
			}*/
		});
		$http.get("http://localhost:8080/libsys/admin/viewPublisherOnly").then(function(response){
			$scope.publishers = response.data;
/*			for (i=0; i<$scope.publishers.length; i++){
				delete $scope.publishers[i].books;
			}*/
		});
		$http.get("http://localhost:8080/libsys/admin/viewGenresOnly").then(function(response){
			$scope.genres = response.data;
/*			for (i=0; i<$scope.genres.length; i++){
				delete $scope.genres[i].books;
			}*/
		});
		
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "addbook.html",
			controller: "addBookModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.showEditBookModal = function(bookID){
		$http.get("http://localhost:8080/libsys/admin/viewAuthorsOnly").then(function(response){
			$scope.authors = response.data;
/*			for (i=0; i<$scope.authors.length; i++){
				delete $scope.authors[i].books;
			}*/
		});
		$http.get("http://localhost:8080/libsys/admin/viewPublisherOnly").then(function(response){
			$scope.publishers = response.data;
/*			for (i=0; i<$scope.publishers.length; i++){
				delete $scope.publishers[i].books;
			}*/
		});
		$http.get("http://localhost:8080/libsys/admin/viewGenresOnly").then(function(response){
			$scope.genres = response.data;
/*			for (i=0; i<$scope.genres.length; i++){
				delete $scope.genres[i].books;
			}*/
		});
		
		bookServ.getBookService(bookID).then(function(data){
			$scope.book = data;
		});
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "editbook.html",
			controller: "editBookModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.showDelBookModal = function(bookID){
		bookServ.getBookService(bookID).then(function(data){
			$scope.book = data;
		});
		var modalInstance = $uibModal.open({
			animation: true,
			templateUrl: "deletebook.html",
			controller: "delBookModalCtrl",
			scope: $scope
		});
		
		modalInstance.result.catch(function () { 
			modalInstance.close(); 
		});
	};
	
	$scope.updateBook = function(){
		if ($scope.book.authors != null){
			/*$scope.book.authorID = authorID;*/
			$http.post("http://localhost:8080/libsys/admin/updateBookWAuthors", $scope.book).then(function(){
				bookServ.getAllBooksService().then(function(data){
					$scope.books = data;
					$scope.doPagination();
				});
			});
		};
		if ($scope.book.publisher != null){
			/*$scope.book.publisherID = publisherID;*/
			$http.post("http://localhost:8080/libsys/admin/updateBookWPubs", $scope.book).then(function(){
				bookServ.getAllBooksService().then(function(data){
					$scope.books = data;
					$scope.doPagination();
				});
			});
		};
		if ($scope.book.genres != null){
			/*$scope.book.genreID = genreID;*/
			$http.post("http://localhost:8080/libsys/admin/updateBookWGenres", $scope.book).then(function(){
				bookServ.getAllBooksService().then(function(data){
					$scope.books = data;
					$scope.doPagination();
				});
			});
		};
	};
	
/*	$scope.searchAuthor = function(){
		authorServ.searchAuthorService($scope.searchAuthorName).then(function(data){
			$scope.authors = data;
			$scope.doPagination();
		});
	}*/
});

libSysApp.controller("addBookModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.saveBook();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});

libSysApp.controller("editBookModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.updateBook();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});

libSysApp.controller("delBookModalCtrl", function($scope, $uibModalInstance) {
	$scope.ok = function(){
		$scope.deleteBook();
		$uibModalInstance.close("ok");
	};
	
	$scope.cancel = function(){
		$uibModalInstance.dismiss("cancel");
	};
});