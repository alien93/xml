angular.module('xmlApp')
		.controller('amandmentsController', ['$scope', '$uibModal',
			function($scope, $uibModal){
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
					
					$scope.newAmandment = function(){
						var modalInstance = $uibModal.open({
								animation: false,
								templateUrl: 'modalAddAmandment.html',
								controller: 'modalAddAmandmentController',
								resolve: {
									items: function(){
											return $scope.selectedAct;
										}
									}
						});
					}	
				}
		])
		//--------------------MODAL DIALOG for adding amandment to chosen act ----------------------
		.controller('modalAddAmandmentController', ['$scope', 'items', '$uibModalInstance',
				function($scope, items, $uibModalInstance){
						$scope.forAct = items;
						
						$scope.amForAct = { name: "", content: ""};
						
						$scope.ok = function(){
							$scope.amForAct.name = $scope.amandmentName;
							$scope.amForAct.content = $scope.amandmentContent;
							
							$scope.forAct.amandments.push($scope.amForAct);
							$uibModalInstance.close();
						}
						$scope.cancel = function(){
							$uibModalInstance.close();
						}
				}
		]);