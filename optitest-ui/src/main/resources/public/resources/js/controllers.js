
var myApp = angular.module('myApp');

myApp.controller('LoginController', 
	[ '$rootScope','$scope', '$http', '$state','$location','$cookies', 'UserService','UtilService', 
     function ($rootScope, $scope,$http, $state, $location, $cookies, UserService, UtilService) {
		$scope.loginUser = {};
		
		$scope.authenticateUser = function(){
			
			$scope.loginFailed = false;
			$http({
			    method: 'POST',
			    url: 'authenticate',
			    data: $.param($scope.loginUser),
			    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			})
			
			.then(function(response) {
				UtilService.notifyInfo('Login successful');
				UserService.setUser(response.data);
				$state.transitionTo('processRequest');
			},function(response) {
				console.log('Login failed');
				$scope.loginFailed = true;
	        	UtilService.notifyError('Invalid Login Credentials');
			});
		};
		
	}
]);

myApp.controller('HomeController', 
   [ '$scope', '$rootScope', '$http', '$location','$sce', 'ProcessRequestService','UserService','UtilService',
   function ($scope, $rootScope, $http, $location, $sce,ProcessRequestService, UserService, UtilService) {
	
	$scope.userDetails={};
	$scope.loadProcessRequest = function(page){
		var pageSize = 10;
		
		ProcessRequestService.loadProcessRequest(page, pageSize)
			.then(
					function(data, status, headers, config){
						var processRequestList = data.processRequestList;
						angular.forEach(processRequestList, function(processRequestItem) {
							processRequestItem.contentPreview = $sce.trustAsHtml(processRequestItem.contentPreview);
						});
						$scope.userDetails = UserService.getUser();
						$scope.processRequestList = processRequestList;
						$scope.postsPagination = {
								hasNextPage : data.hasNextPage,
								hasPrevPage: data.hasPrevPage,
								currentPage: data.currentPage
						};
					},
					function(data, status, headers, config){
						UtilService.notifyError('Problem in loading Process Request status');
					}
			);
	};
	
	$scope.loadProcessRequest(0);
	
}]);


myApp.controller('ProcessRequestController',
	[ '$scope', '$rootScope', '$http', '$location','$sce','UtilService','UserService', 'CodchnConfigService',
	function ($scope, $rootScope, $http, $location, $sce, UtilService, UserService, CodchnConfigService) {					
		
		$scope.userDetails ={}
		$scope.propertyList = [];
		$scope.selectedPropertyList = [];
		$scope.formats = ['dd-MM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
        $scope.format = $scope.formats[0];
		
			$scope.loadAllCodchn = function(){
				
				CodchnConfigService.loadAllCodchn()
				.then(
						function(data, status, headers, config){
							var codchnList = data;
							angular.forEach(codchnList, function(codchn) {
								codchn.contentPreview = $sce.trustAsHtml(codchn.contentPreview);
							});
							$scope.codchnList = codchnList;
							
						},
						function(data, status, headers, config){
							UtilService.notifyError('Problem in loading codchn');
						}
				);
				
			};
			
			$scope.loadPropertyByCodchn = function(codchn){
				//$scope.propertyList = [ {id: 1, label: "Currency"}, {id: 2, label: "Strike"}, {id: 3, label: "Tenor"}];
				
				CodchnConfigService.loadPropertyByCodchn(codchn)
				.then(
						function(data, status, headers, config){
							var propertyList = data;
							angular.forEach(propertyList, function(property) {
								propertyList.contentPreview = $sce.trustAsHtml(property.contentPreview);
							});
							$scope.propertyList = propertyList;
							//console.log(propertyList);
						},
						function(data, status, headers, config){
							UtilService.notifyError('Problem in loading codchn');
						}
				);
					
			};
		
            $scope.serviceRequest={};
            $scope.propertyListModel=[];
            $scope.responseString="";
            $scope.show=false;
            $scope.dropDownsettings = {
                    scrollableHeight: '200px',
                    scrollable: true,
                    enableSearch: true
                };
            $scope.createProcessRequest = function(serviceRequest, propertyListModel){
            	
            	var propStr="";
            	for (i = 0; i < propertyListModel.length; i++) { 
            		propStr += propertyListModel[i].id + ", ";
            	}
            
            	$scope.userDetails = UserService.getUser();
            	
            	$http.get('api/createProcessRequest/?codchn='+serviceRequest.codchn+ '&description='+serviceRequest.description +'&batchDate='+serviceRequest.batchDate+'&selectedPropertyList='+propStr+'&email='+$scope.userDetails.email)
        		.success(function(data, status, headers, config){ 
        			
        			var responseString = data;
        			angular.forEach(responseString, function(response){
        				response.content = $sce.trustAsHtml(response.content);
        			});
        			$scope.responseString=responseString;
        			$scope.show=true;
        		})
        		.error(function(data, status, headers, config){
        			console.log('Error while request for sampling process');
        			UtilService.notifyError('Error ' + data.message);
        			$scope.show=false;
        		});
        	};
            
            
            $scope.minDate = new Date().setHours(-8000,0,0,0);
            $scope.maxDate =  new Date();         
            $scope.open = function($event) {
                $event.preventDefault();
                $event.stopPropagation();
                $scope.opened = true;
              };
              $scope.dateOptions = {
                formatYear: 'yy',
                minDate: new Date(),
                startingDay: 1,
                showWeeks: false
              };
             
             $scope.loadAllCodchn();
}]);
