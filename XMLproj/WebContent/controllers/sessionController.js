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
				console.log(value);
				var restUrl = "http://localhost:8080/XMLproj/rest/amendment/amendmentsForAct/" + value;
				console.log(restUrl);
				if($scope.contentForActVisible[index]){
					$http({
						method: "GET", 
						url : restUrl,
					}).then(function(retVal) {
						$scope.amendments = retVal.data.results.bindings;
						console.log(retVal.data.results.bindings);
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