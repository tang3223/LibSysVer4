libSysApp.factory("ckrtServ", function($http, ckrtConst){
	return{	
		getAllBooksService: function(branchID){
			return $http({
				url: ckrtConst.GET_BCH_BOOKS_URL+branchID
			}).then(function(response){
				return response.data;
			})
		},
	
		getBookService: function(bookID){
			return $http({
				url: ckrtConst.GET_BOOK_URL+bookID
			}).then(function(response){
				return response.data;
			})
		},
		
		initBookLoanService: function(){
			return $http({
				url: ckrtConst.INIT_BOOKLOAN_URL
			}).then(function(response){
				return response.data;
			})
		},
		
		getLoanService: function(borrowerID){
			return $http({
				url: ckrtConst.GET_LOAN_URL+borrowerID
			}).then(function(response){
				return response.data;
			})
		}
	}
})