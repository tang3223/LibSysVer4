libSysApp.factory("borServ", function($http, borConst){
	return{
		getAllBorsService: function(){
			return $http({
				url: borConst.GET_ALL_BORS_URL
			}).then(function(response){
				return response.data;
			})
		},
	
		getBorService: function(borrowerID){
			return $http({
				url: borConst.GET_BOR_URL+borrowerID
			}).then(function(response){
				return response.data;
			})
		},
		
		initBorService: function(){
			return $http({
				url: borConst.INIT_BOR_URL
			}).then(function(response){
				return response.data;
			})
		},

	}
})