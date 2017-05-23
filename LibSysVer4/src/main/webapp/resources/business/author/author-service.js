libSysApp.factory("authorServ", function($http, authorConst){
	return{
		getAllAuthorsService: function(){
			return $http({
				url: authorConst.GET_ALL_AUTHORS_URL
			}).then(function(response){
				return response.data;
			})
		},
	
		getAuthorService: function(authorID){
			return $http({
				url: authorConst.GET_AUTHOR_URL+authorID
			}).then(function(response){
				return response.data;
			})
		},
		
		initAuthorService: function(){
			return $http({
				url: authorConst.INIT_AUTHOR_URL
			}).then(function(response){
				return response.data;
			})
		},
		
		searchAuthorService: function(authorName){
			return $http({
				url: authorConst.GET_AUTHOR_SEARCH_URL+authorName
			}).then(function(response){
				return response.data;
			})
		}

	}
})