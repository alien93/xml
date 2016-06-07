angular.module('xmlApp')
		//userlogin
		.controller('userController', ['$scope', '$http',
			function($scope, $http){
					$scope.login = function(){
						$http({
							method : "POST",
							url : "http://localhost:8080/rest/userLogin",
							data : $scope.user
						}).then(function(resp){
							alert(JSON.stringify(resp));
						}, 
						function(err){
							alert(JSON.stringify(err));
						});
					}
				}
		]);