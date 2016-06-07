angular.module('xmlApp')
		.controller('actsController', ['$scope', '$http',
			function($scope, $http){
					$scope.addAct = function(){
						$http({
							method : "POST",
							url : "http://localhost:8080/rest/addAct",
							data : $scope.newAct
						}).then(function(resp){
							alert(JSON.stringify(resp));
						}, 
						function(err){
							alert(JSON.stringify(err));
						});
					}
				}
		]);