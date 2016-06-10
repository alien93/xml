angular.module('xmlApp')
		.controller('amandmentsController', ['$scope',
			function($scope){
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
					
					$scope.addAmandment = function(index){
						
					}
					
					
				}
		]);