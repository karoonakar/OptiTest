var myApp = angular.module('myApp',['ui.router','ngResource','ngCookies','ngSanitize','ui-notification','ui.bootstrap','ngLoadingSpinner','angularjs-dropdown-multiselect']);


myApp.factory('responseObserver',
  function responseObserver($q, $window, $rootScope) {
	
	return {
        request: function (config) {
            return config || $q.when(config);
        },
        requestError: function(request){
            return $q.reject(request);
        },
        response: function (response) {
            return response || $q.when(response);
        },
        responseError: function (response) {
            if (response && response.status === 412) {
            	var message = {type: 'error', 'msg':'Problem in processing your request.'};
            	$rootScope.$emit('NotificationEvent', message);
            	$rootScope.logout();
            }
            if (response && response.status === 401) {
            	var message = {type: 'error', 'msg':'Invalid Login Credentials or Session Expired.'};
            	$rootScope.$emit('NotificationEvent', message);
            	$rootScope.logout();
            }
            return $q.reject(response);
        }
    };
    
});


myApp.config(function($stateProvider, $urlRouterProvider, $locationProvider, $httpProvider) {
	$httpProvider.interceptors.push('responseObserver');

	$stateProvider
	.state('login', {
		url: '/login',
		templateUrl: 'templates/login.html', 
  		controller: 'LoginController',
  		access: 'public'
	})
	.state('home', {
		url: '/home',
		templateUrl: 'templates/home.html', 
  		controller: 'HomeController',
  		access: 'public'
	})
	.state('processRequest', {
		url: '/processRequest',
		templateUrl: 'templates/processRequest.html',
		controller: 'ProcessRequestController',	
	});
	
	$urlRouterProvider.otherwise('/home');
})

.run(['$rootScope', '$state', '$timeout','UserService','UtilService',
    function ($rootScope, $state, $timeout, UserService, UtilService) {
	
	$rootScope.$on('$stateChangeStart', function(evt, toState, toParams, fromState, fromParams) {
		var access = toState.access;
    	if(access != 'public'){
    		if(UserService.isUserLoggedIn()){
    			$rootScope.currentNavLink=toState.name;
    		} else {
    			evt.preventDefault();
    			console.log('redirect to login');
    			$state.go("login");
    		}
    	}
		
	});
	
	$rootScope.$on('NotificationEvent', function (event, message) {
	  	  $rootScope.message = message;
	  	  if(message.type == 'error'){
	  		UtilService.notifyError(message.msg);
	  	  } else {
	  		UtilService.notifyInfo(message.msg);
	  	  }
	  	  
	  	  $timeout(function(){
	  		  delete $rootScope.message;
	  	  }, 3000);
	 });
	
	$rootScope.isUserLoggedIn = function(){
        return UserService.isUserLoggedIn();
	}
 
	$rootScope.logout = function()
	{
		console.log('Logging out..');
		UserService.logout();
		$state.transitionTo("login");
	}
	    
}])
;
