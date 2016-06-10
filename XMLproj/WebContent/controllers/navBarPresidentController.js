angular.module('xmlApp')

	.controller('navBarPresidentController', ['$scope', '$location',
	         function($scope, $location){
					$scope.activeAct = function(){
						$location.path('/predsednik');
					}					
					$scope.nonActiveAct = function(){
						$location.path('/predsednik/aktaUProceduri');
					}
					$scope.suggestAct = function(){
						$location.path('/predsednik/predlogAkta');
					}
					$scope.suggestAmendment = function(){
						$location.path('/predsednik/predlogAmandmana');
					}
					$scope.session = function(){
						$location.path('/predsednik/sednica')
					}
					$scope.logout = function(){
						$location.path('/prijava');
					}
	}]);