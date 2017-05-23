libSysApp.config(['$routeProvider', function($routeProvider){
	return $routeProvider.when('/',{
		redirectTo: '/home'
	}).when('/home', {
		templateUrl:'main.html'
	}).when('/admin',{
		templateUrl:'administrator.html'
	}).when('/admin/author', {
		templateUrl:'viewauthor.html'
	}).when('/admin/book', {
		templateUrl:'viewbook.html'
	}).when('/admin/borrower', {
		templateUrl:'viewborrower.html'
	}).when('/admin/branch', {
		templateUrl:'viewbranch.html'
	}).when('/admin/genre', {
		templateUrl:'viewgenre.html'
	}).when('/admin/publisher', {
		templateUrl:'viewpublisher.html'
	}).when('/admin/bookloan', {
		templateUrl:'viewloan.html'
	}).when('/library', {
		templateUrl:'managebranch.html'
	}).when('/bor/checkbook', {
		templateUrl:'checkbook.html'
	}).when('/bor/returnbook', {
		templateUrl:'returnbook.html'
	})
}]);

libSysApp.config(['$locationProvider', function($locationProvider){
	$locationProvider.hashPrefix('');
}]);