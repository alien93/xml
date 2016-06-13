angular.module('xmlApp')
		.controller('sessionController', ['$rootScope', '$scope', '$location', '$http',
			function($rootScope, $scope, $location, $http){
				if ($rootScope.user.role == "CITIZEN") {
					$location.path('/prijava');
				};	
			
			
			//-------------------------test data----------------------
				//retrieving non active acts
				$http({
					method: "GET", 
					url : "http://localhost:8080/XMLproj/rest/act/nonActive",
				}).then(function(value) {
					$scope.acts = value.data.results.bindings;
				});		
			
			// Expand/collapse content for act
			$scope.contentForActVisible = [false, false];
			$scope.expandAct = function(index) {
				console.log("expand " + index);
				$scope.contentForActVisible[index] = !$scope.contentForActVisible[index];
			};
			$scope.testIfVisible = function(index) {
				return $scope.contentForActVisible[index];
			};
			
			$scope.sessionSubmit = function() {
				
			};
		
		}
		]);