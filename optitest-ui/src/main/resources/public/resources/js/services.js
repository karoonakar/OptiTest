
var myApp = angular.module('myApp');

myApp.service('UtilService',['$rootScope', '$cookieStore', 'Notification',
	 function($rootScope, $cookieStore, Notification) 
	 {	
		 var self = this;
		 
		 self.fireInfoEvent = function(msg){
			 var message = {type: 'info', 'msg':msg};
         	 $rootScope.$emit('NotificationEvent', message);
		 };
		 
		 self.fireErrorEvent = function(msg){
			 var message = {type: 'error', 'msg':msg};
         	 $rootScope.$emit('NotificationEvent', message);
		 };
		 
		 self.notifyInfo = function(msg){
         	Notification.primary(msg);
		 };
		 
		 self.notifyError = function(msg){
         	 Notification.error(msg);
		 };
		 
	 }
]);

myApp.service('UserService',['$http', '$state', '$cookieStore', 
	 function($http, $state, $cookieStore) {
	
		var self = this;
			
		self.setUser = function(aUser){
			$cookieStore.put('authenticatedUser', aUser);
		};
		    
	    self.getUser = function(){
	    	return $cookieStore.get('authenticatedUser');
	    };
	    
	    self.isUserLoggedIn = function() {
	    	return $cookieStore.get('authenticatedUser') != null;
	    };
	    
	    self.getUserName = function(){
	    	var user = $cookieStore.get('authenticatedUser');
	    	return user.name;
	    };
	    
	    self.logout = function() {
	    	$cookieStore.remove('authenticatedUser');
	    	$http.post('logout')
	    	.success(function(response){
	    		console.log('Successfully logged out on server');
	  	    });
	  	};  		 	
	 }
]);


myApp.service('ProcessRequestService',['$resource', '$http', 
                              function($resource, $http){
                         		
                         	this.loadProcessRequest = function(page, pageSize){
                         		var promise = $http.get('api/processrequest/?pageNo='+page+'&pageSize='+pageSize)
                         			.then(function(response){
                         				return response.data;
                         			});
                         		return promise;
                         	 };
                         	 
                         	 
}]);


myApp.service('CodchnConfigService',['$resource', '$http', 
                                       function($resource, $http){
                                  		
                                  	this.loadAllCodchn = function(){
                                  		var promise = $http.get('api/getAllCodechn/')
                                  			.then(function(response){
                                  				return response.data;
                                  			});
                                  		return promise;
                                  	 };
                                  	 
                                  	this.loadPropertyByCodchn = function(codchn){
                                  		var promise = $http.get('api/getPropertyByCodchn/'+codchn+'/')
                                  			.then(function(response){
                                  				return response.data;
                                  			});
                                  		return promise;
                                  	 };
                                  	
}]);
