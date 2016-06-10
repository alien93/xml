angular.module('xmlApp')

	.controller('navBarAldermanController', ['$scope', '$location',
	         function($scope, $location){
					$scope.activeAct = function(){
						$location.path('/odbornik');
					}					
					$scope.nonActiveAct = function(){
						$location.path('/odbornik/aktaUProceduri');
					}
					$scope.suggestAct = function(){
						$location.path('/odbornik/predlogAkta');
					}
					$scope.suggestAmendment = function(){
						$location.path('/odbornik/predlogAmandmana');
					}
					$scope.logout = function(){
						$location.path('/prijava');
					}
	}]);