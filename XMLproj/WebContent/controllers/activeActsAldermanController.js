angular.module('xmlApp')

	.controller('activeActsAldermanController', ['$rootScope', '$scope', '$location', '$http',
	         function($rootScope, $scope, $location, $http){
				if ($rootScope.user.role == "CITIZEN") {
					$location.path('/prijava');
				};
					//retrieving active acts
					$http({
						method: "GET", 
						url : "http://localhost:8080/XMLproj/rest/act/active",
					}).then(function(value) {
						$scope.$parent.acts = value.data.results.bindings;
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
	}])
	
	
	.controller('nonActiveActsAldermanController', ['$rootScope', '$scope', '$location','$http',
	         function($rootScope, $scope, $location, $http){
				if ($rootScope.user.role == "CITIZEN") {
					$location.path('/prijava');
				};	
					//retrieving non active acts
					$http({
						method: "GET", 
						url : "http://localhost:8080/XMLproj/rest/act/nonActive",
					}).then(function(value) {
						$scope.$parent.acts = value.data.results.bindings;
					});
		
					//row click
					$scope.getSelected = function(docName){
						console.log(docName);
						$http({
							method : "GET",
							url : "http://localhost:8080/XMLproj/rest/act/nonActiveId/" + docName,
							responseType: 'arraybuffer'
						}).then(function(result){
							var file = new Blob([result.data], {type: 'application/pdf'});
						    var fileURL = URL.createObjectURL(file);
						    window.open(fileURL);
						}, function(reason){
							console.log(JSON.stringify(reason));
						});
					}
	}]);