angular.module('xmlApp')

	.controller('activeActsController', ['$scope', '$http',
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
						$http({
							method : "GET",
							url : "http://localhost:8080/XMLproj/rest/act/activeId/" + docName,
							responseType: 'arraybuffer'
						}).then(function(result){
							var file = new Blob([result.data], {type: 'application/pdf'});
						    var fileURL = URL.createObjectURL(file);
						    window.open(fileURL);
						}, function(reason){
							console.log(JSON.stringify(reason));
						});
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
	
	
	.controller('nonActiveActsController', ['$scope', '$http',
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