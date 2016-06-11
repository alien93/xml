angular.module('xmlApp')
		//userlogin
		.controller('userController', ['$rootScope', '$scope', '$location', '$http',
			function($rootScope, $scope, $location, $http) {
				$rootScope.isRoleCitizen = true;
				$rootScope.isRoleAlderman = false;
				$rootScope.isRolePresident = false;
				$rootScope.user = {username: "admin", password: "admin", role: "CITIZEN"};
				
				$scope.changeRole = function(role){
					$scope.user.role = role;
				}
				$scope.user = $rootScope.user;
				$scope.errorMessage = "";
				$scope.error = false;
				
				$scope.login = function(){
					var username = $scope.user.username;
					var password = $scope.user.password;
					var role = $scope.user.role;
					
					if(role == "CITIZEN"){
						$scope.showCitizenMenu();
						return;
					}
					
					$http({
						method: "POST", 
						url : "http://localhost:8080/XMLproj/rest/user/login",
						data : JSON.stringify($scope.user)
					}).then(function(value) {
						if(value.data == "OK"){
							$rootScope.user.username = username;
							$rootScope.user.password = password;
							
							if (role == "ALDERMAN") {	
								$rootScope.user.role = role;
								$scope.showAldermanMenu();
							}
							else if (role == "PRESIDENT") {
								$rootScope.user.role = role;
								$scope.showPresidentMenu();
							}
						}else{
							$scope.errorMessage = value.data;
							$scope.errorMessage = value.data;
							$scope.error = true;
						}
					}, function(reason) {
						console.log(JSON.stringify(reason));
					});
				};
				
				$scope.showCitizenMenu = function() {
					$rootScope.isRoleCitizen = true;
					$rootScope.isRoleAlderman = false;
					$rootScope.isRolePresident = false;
					$location.path('/gradjanin');
				};
				
				$scope.showAldermanMenu = function() {
					$rootScope.isRoleCitizen = false;
					$rootScope.isRoleAlderman = true;
					$rootScope.isRolePresident = false;
					$location.path('/odbornik');
				};
				
				$scope.showPresidentMenu = function() {
					$rootScope.isRoleCitizen = false;
					$rootScope.isRoleAlderman = false;
					$rootScope.isRolePresident = true;
					$location.path('/predsednik');
				};
			}
		]);