angular.module('xmlApp')
		.controller('searchNonActiveActsController', ['$scope', '$http',
			function($scope, $http){
				$scope.searchText = "";
				$scope.selectedCategory = "Sve kategorije";
				
				$scope.search = function() {
					
				};
			}
		]);