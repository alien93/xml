angular.module('xmlApp')
		.controller('actsController', ['$rootScope','$scope', '$location', '$http',
			function($rootScope, $scope, $location, $http){
					if ($rootScope.user.role == "CITIZEN") {
						$location.path('/prijava');
					};					
						
					$scope.insertTextVisible = false;
					$scope.showTextPart = function() {
						$scope.status = "";
						$scope.insertTextVisible = !$scope.insertTextVisible;
					};
					$scope.status = "";

					//post act
					var postAct = function(act){
						$http({
							method : "POST",
							url : "http://localhost:8080/XMLproj/rest/act/addAct",
							headers : {
								"Content-Type": "application/xml"
							},
							data : act
						}).then(function(resp){
							if(resp.statusText == "OK"){
								console.log("It's ok");
								$scope.status = "Dokument je sačuvan."
								$scope.newAct = "";
							}
						}, 
						function(err){
							if(err.statusText == "Bad Request"){
								$scope.status = "Dokument nije validan."
							}
						});
					}
					
					//uploading act
					$scope.addFileAct = function() {
						$scope.status = "";
						var file = document.getElementById('file').files[0];
						var reader = new FileReader();
						reader.onloadend = function(e){
							var data = e.target.result;
							postAct(data);
						}
						reader.readAsBinaryString(file);
					};
					
					//adding act
					$scope.addAct = function(){
						postAct($scope.newAct);						
					};
				}
		]);