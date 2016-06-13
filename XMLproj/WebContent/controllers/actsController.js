angular.module('xmlApp')
		.controller('actsController', ['$rootScope','$scope', '$location', '$http',
			function($rootScope, $scope, $location, $http){
					if ($rootScope.user.role == "CITIZEN") {
						$location.path('/prijava');
					};					
						
					$scope.insertTextVisible = false;
					$scope.showTextPart = function() {
						$scope.insertTextVisible = !$scope.insertTextVisible;
					};
					$scope.status = "";
				
					$scope.addFileAct = function() {
						
					};
					
					$scope.addAct = function(){
						
						$http({
							method : "POST",
							url : "http://localhost:8080/XMLproj/rest/act/addAct",
							headers : {
								"Content-Type": "application/xml"
							},
							data : $scope.newAct
						}).then(function(resp){
							if(resp.statusText == "OK"){
								console.log("It's ok");
								$scope.status = "Dokument je sačuvan."
							}
						}, 
						function(err){
							if(err.statusText == "Bad Request"){
								$scope.status = "Dokument nije validan."
							}
						});
					};
				}
		]);