libSysApp.factory("pubServ", function($http, pubConst){
	return{
		getAllPubsService: function(){
			return $http({
				url: pubConst.GET_ALL_PUBS_URL
			}).then(function(response){
				return response.data;
			})
		},
	
		getPubService: function(publisherID){
			return $http({
				url: pubConst.GET_PUB_URL+publisherID
			}).then(function(response){
				return response.data;
			})
		},
		
		initPubService: function(){
			return $http({
				url: pubConst.INIT_PUB_URL
			}).then(function(response){
				return response.data;
			})
		},

	}
})