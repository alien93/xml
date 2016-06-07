angular.module('xmlApp')

	.controller('navBarCitizenController', ['$scope', '$location',
	         function($scope, $location){
					$scope.activeAct = function(){
						$location.path('/gradjanin');
					}					
					$scope.nonActiveAct = function(){
						$location.path('/gradjanin/aktaUProceduri');
					}
					$scope.login = function(){
						$location.path('/prijava');
					}
	}]);