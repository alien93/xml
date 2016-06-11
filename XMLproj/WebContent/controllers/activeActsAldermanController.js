angular.module('xmlApp')

	.controller('activeActsAldermanController', ['$scope', '$http',
	         function($scope, $http){
					
					$http({
						method: "GET", 
						url : "http://localhost:8080/XMLproj/rest/act/active",
					}).then(function(value) {
						$scope.acts = value.data.results.bindings;
					});
		
					//row click
					$scope.getSelected = function(docName){
						console.log(docName);
					}
					/*
					$http
				    .get(generatePdfUrl)
				    .then(function(data){
				        //data is link to pdf
				        $window.open(data);
				    }); 
				    */
	}])
	
	
	.controller('nonActiveActsAldermanController', ['$scope', '$http',
	         function($scope, $http){
					
					$http({
						method: "GET", 
						url : "http://localhost:8080/XMLproj/rest/act/nonActive",
					}).then(function(value) {
						$scope.acts = value.data.results.bindings;
					});
		
					//row click
					$scope.getSelected = function(docName){
						console.log(docName);
					}
					/*
					$http
				    .get(generatePdfUrl)
				    .then(function(data){
				        //data is link to pdf
				        $window.open(data);
				    }); 
				    */
	}]);