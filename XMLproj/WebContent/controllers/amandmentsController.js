angular.module('xmlApp')
		.controller('amandmentsController', ['$scope',
			function($scope){
					$scope.amandmentsForAct = [];
					$scope.acts = [ 
							{ name: "Akt1", date: "", type: "", 
								amandments: ["Am1", "Am2", "Am3"] }, 
							{ name: "Akt2", date: "", type: "",
								amandments: ["Am1", "Am2", "Am3"] 	}, 
							{ name: "Akt3", date: "", type: "",
								amandments: ["Am1", "Am2", "Am3"] }, 
							];
					$scope.addAmandment = function(index){
						
					}
					
					$scope.showAmandments = function(index){
						console.log(index);
						$scope.amandmentsForAct = acts[0].amandments;
					}
				}
		]);