libSysApp.factory("bchServ", function($http, bchConst){
	return{
		getAllBchsService: function(){
			return $http({
				url: bchConst.GET_ALL_BCHS_URL
			}).then(function(response){
				return response.data;
			})
		},
	
		getBchService: function(branchID){
			return $http({
				url: bchConst.GET_BCH_URL+branchID
			}).then(function(response){
				return response.data;
			})
		},
		
		initBchService: function(){
			return $http({
				url: bchConst.INIT_BCH_URL
			}).then(function(response){
				return response.data;
			})
		},

	}
})