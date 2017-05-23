libSysApp.factory("bookServ", function($http, bookConst){
	return{
		getAllBooksService: function(){
			return $http({
				url: bookConst.GET_ALL_BOOKS_URL
			}).then(function(response){
				return response.data;
			})
		},
	
		getBookService: function(bookID){
			return $http({
				url: bookConst.GET_BOOK_URL+bookID
			}).then(function(response){
				return response.data;
			})
		},
		
		initBookService: function(){
			return $http({
				url: bookConst.INIT_BOOK_URL
			}).then(function(response){
				return response.data;
			})
		}
		
/*		searchAuthorService: function(authorName){
			return $http({
				url: authorConst.GET_AUTHOR_SEARCH_URL+authorName
			}).then(function(response){
				return response.data;
			})
		}
*/
	}
})