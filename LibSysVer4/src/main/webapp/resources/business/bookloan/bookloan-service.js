libSysApp.factory("bklnServ", function($http, bklnConst){
	return{
		getAllBklnService: function(){
			return $http({
				url: bklnConst.GET_ALL_BKLNS_URL
			}).then(function(response){
				return response.data;
			})
		},
		
		initBklnService: function(){
			return $http({
				url: bklnConst.INIT_BKLN_URL
			}).then(function(response){
				return response.data;
			})
		},
	}
})