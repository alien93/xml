angular.module('xmlApp')
		.controller('amandmentsController', ['$rootScope', '$scope', '$location',
			function($rootScope, $scope, $location){
					if ($rootScope.user.role == "CITIZEN") {
						$location.path('/prijava');
					};	
					
					$scope.insertTextVisible = false;
					$scope.showTextPart = function() {
						$scope.insertTextVisible = !$scope.insertTextVisible;
					};
					
					$scope.acts = [ 
							{ name: "Akt1", date: "", type: "", 
								amandments: [{ name: "Am1"}] }, 
							{ name: "Akt2", date: "", type: "",
								amandments: [{ name: "Am1"}, { name: "Am2"}] 	}, 
							{ name: "Akt3", date: "", type: "",
								amandments: [{ name: "Am1"}, { name: "Am2"}, { name: "Am3"}] }, 
							];
							
					$scope.selectedAct = $scope.acts[0];
					$scope.amandmentsForAct = $scope.selectedAct.amandments;
					
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