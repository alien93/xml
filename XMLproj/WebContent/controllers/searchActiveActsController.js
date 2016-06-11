angular.module('xmlApp')
		.controller('searchActiveActsController', ['$scope', '$http',
			function($scope, $http){
				$scope.searchText = "";
				$scope.selectedCategory = "Sve kategorije";
				
				$scope.search = function() {
					
				};
			}
		]);