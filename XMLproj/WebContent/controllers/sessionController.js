angular.module('xmlApp')
		.controller('sessionController', ['$rootScope', '$scope', '$location', '$http',
			function($rootScope, $scope, $location, $http){
				if ($rootScope.user.role == "CITIZEN") {
					$location.path('/prijava');
				};	
			
			
			//-------------------------test data----------------------
			var act = {
						name: "ODLUKA O IMENOVANJU GENERALNOG SEKRETARA NARODNE SKUPŠTINE",
						date: "6. jun 2016.",
						type: "Odluka", 
						amandments: [ {name: "Am1"}, {name: "Am2"}]
						};
			var act2 = {
						name: "ODLUKA O BROJU I IZBORU POTPREDSEDNIKA NARODNE SKUPŠTINE",
						date: "6. jun 2016.",
						type: "Odluka",
						amandments: [ {name: "Am1"}, {name: "Am2"}]
						};
			
			$scope.acts = {
							"active" : [act, act2],
						  	"nonActive" : [act, act2]
			    		  };		
			
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