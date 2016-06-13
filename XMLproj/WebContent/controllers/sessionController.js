angular.module('xmlApp')
		.controller('sessionController', ['$rootScope', '$scope', '$location', '$http',
			function($rootScope, $scope, $location, $http){
				if ($rootScope.user.role == "CITIZEN") {
					$location.path('/prijava');
				};	
			
			//retrieving non active acts
				$http({
					method: "GET", 
					url : "http://localhost:8080/XMLproj/rest/act/nonActive",
				}).then(function(value) {
					$scope.acts = value.data.results.bindings;
				});		
			
			// Expand/collapse content for act
			$scope.contentForActVisible = [false, false];
			
			$scope.expandAct = function(index, value) {
				
				$scope.contentForActVisible[index] = !$scope.contentForActVisible[index];
				if($scope.contentForActVisible[index]){
					$http({
						method: "GET", 
						url : "http://localhost:8080/XMLproj/rest/amendment/amendmentsForAct" + value,
					}).then(function(value) {
						//$scope.acts = value.data.results.bindings;
						//$scope.amendments = 
					});	
				}
			};
			$scope.testIfVisible = function(index) {
				return $scope.contentForActVisible[index];
			};
			
			$scope.sessionSubmit = function() {
				
			};
		
		}
		]);