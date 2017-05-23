libSysApp.factory("genreServ", function($http, genreConst){
	return{
		getAllGenresService: function(){
			return $http({
				url: genreConst.GET_ALL_GENRES_URL
			}).then(function(response){
				return response.data;
			})
		},
	
		getGenreService: function(genreID){
			return $http({
				url: genreConst.GET_GENRE_URL+genreID
			}).then(function(response){
				return response.data;
			})
		},
		
		initGenreService: function(){
			return $http({
				url: genreConst.INIT_GENRE_URL
			}).then(function(response){
				return response.data;
			})
		},

	}
})