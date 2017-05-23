libSysApp.factory("libServ", function($http, libConst){
	return{
		getAllBchsService: function(){
			return $http({
				url: libConst.GET_ALL_BCHS_URL
			}).then(function(response){
				return response.data;
			})
		},
		
		getAllBooksService: function(){
			return $http({
				url: libConst.GET_ALL_BOOKS_URL
			}).then(function(response){
				return response.data;
			})
		},
	
		getBchService: function(branchID){
			return $http({
				url: libConst.GET_BCH_URL+branchID
			}).then(function(response){
				return response.data;
			})
		},
		
		getBookService: function(bookID){
			return $http({
				url: libConst.GET_BOOK_URL+bookID
			}).then(function(response){
				return response.data;
			})
		}

	}
})