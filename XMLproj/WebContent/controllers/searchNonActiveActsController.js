angular.module('xmlApp')
		.controller('searchNonActiveActsController', ['$scope', '$http',
			function($scope, $http){
				$scope.searchText = "";
				$scope.selectedCategory = "Sadrzaj";
				
				$scope.search = function() {
					$http({
						method: "GET",
						url : "http://localhost:8080/XMLproj/rest/filter/nonActiveActs/" + ($scope.searchText.trim() == "" ? 
								"_" : $scope.searchText.trim()) + "/" + $scope.selectedCategory
					}).then(function(result){
						$scope.$parent.$parent.acts = result.data.results.bindings;
					},function(reason){
						console.log(JSON.stringify(reason));
					});
				};
			}
		]);