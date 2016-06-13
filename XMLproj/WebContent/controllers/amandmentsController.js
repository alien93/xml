angular.module('xmlApp')
		.controller('amandmentsController', ['$rootScope', '$scope', '$location', '$http',
			function($rootScope, $scope, $location, $http){
					if ($rootScope.user.role == "CITIZEN") {
						$location.path('/prijava');
					};	
					
					$scope.insertTextVisible = false;
					$scope.showTextPart = function() {
						$scope.insertTextVisible = !$scope.insertTextVisible;
					};
					
					$http({
						method: "GET", 
						url : "http://localhost:8080/XMLproj/rest/act/nonActive",
					}).then(function(value) {
						$scope.acts = value.data.results.bindings;
					});
							
					//$scope.selectedAct = $scope.acts[0];
					//$scope.amandmentsForAct = $scope.selectedAct.amandments;
					
					$scope.showAmandments = function(){
						$scope.amandmentsForAct = $scope.selectedAct.amandments;
					}
					
					$scope.saveAmandment = function() {
						
					};
					
					$scope.openAmandment = function() {
						
					};
					
					$scope.deleteAmandment = function() {
						
					};
				}
		]);