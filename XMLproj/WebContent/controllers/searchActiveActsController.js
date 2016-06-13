angular.module('xmlApp')
		.controller('searchActiveActsController', ['$scope', '$http',
			function($scope, $http){
				$scope.searchText = "";
				$scope.selectedCategory = "Sadrzaj";
				
				$scope.search = function() {
					var urlRest = $scope.selectedCategory != "Sadrzaj" ? "http://localhost:8080/XMLproj/rest/filter/activeActs/" + ($scope.searchText.trim() == "" ? 
							"_" : $scope.searchText.trim()) + "/" + $scope.selectedCategory
							: "http://localhost:8080/XMLproj/rest/searchText/active/" + $scope.searchText.trim();
					$http({
						method: "GET",
						url : urlRest
					}).then(function(result){
						$scope.$parent.$parent.acts = result.data.results.bindings;
					},function(reason){
						console.log(JSON.stringify(reason));
					});
				};
			}
		]);