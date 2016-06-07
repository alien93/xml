angular.module('xmlApp')
		.controller('amandmentsController', ['$scope', '$http',
			function($scope, $http){
					$scope.addAmandment = function(){
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