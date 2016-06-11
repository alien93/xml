angular.module('xmlApp')
		//userlogin
		.controller('userController', ['$rootScope', '$scope', '$location', '$http',
			function($rootScope, $scope, $location, $http) {
				$rootScope.isRoleCitizen = true;
				$rootScope.isRoleAlderman = false;
				$rootScope.isRolePresident = false;
				$rootScope.user = {username: "", password: "", role: "citizen"};
								
				$scope.login = function(){
					var username = $scope.user.username;
					var password = $scope.user.password;
					var role = $scope.user.role;
					
					$rootScope.user.username = username;
					$rootScope.user.password = password;
					
					if (role == "alderman") {	
						$rootScope.user.role = role;
						$scope.showAldermanMenu();
					}
					else if (role == "president") {
						$rootScope.user.role = role;
						$scope.showPresidentMenu();
					}
					else {
						$rootScope.user.role = "citizen";
						$scope.showCitizenMenu();
					}	
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