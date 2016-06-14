angular.module('xmlApp')
		.controller('searchNonActiveActsController', ['$scope', '$http', '$uibModal',
			function($scope, $http, $uibModal){
				$scope.searchText = "";
				$scope.selectedCategory = "Sadrzaj";
				
				$scope.search = function() {
					var text = ($scope.searchText.trim() == "" ? "_" : $scope.searchText.trim())
					var urlRest = $scope.selectedCategory != "Sadrzaj" ? "http://localhost:8080/XMLproj/rest/filter/nonActiveActs/" + text + "/" + $scope.selectedCategory
							: "http://localhost:8080/XMLproj/rest/searchText/nonActive/" + text;
					$http({
						method: "GET",
						url : urlRest
					}).then(function(result){
						$scope.$parent.$parent.acts = result.data.results.bindings;
					},function(reason){
						console.log(JSON.stringify(reason));
					});
				};
				
				//advanced search
				$scope.advancedSearch = function(){
					var modalInstance = $uibModal.open({
						animation: false,
						templateUrl: 'views/advancedSearchNonActive_m.html',
						controller: 'advancedSearchNonActiveController',
						resolve:{}
				});
				}
			}
		])
		
		.controller('advancedSearchNonActiveController', ['$scope', '$uibModalInstance', '$filter', 
		        function($scope, $uibModalInstance, $filter){
			
						$scope.search = function(){
							var oznaka = $scope.oznaka;
							var naziv = $scope.naziv;
							var vrsta = $scope.vrsta;
							var datumOd = $filter('date')($scope.datumOd, "yyyy-MM-dd");
							var datumDo = $filter('date')($scope.datumDo, "yyyy-MM-dd");
							var mesto = $scope.mesto;
							var brPozitivnih = $scope.brPozitivnih;
							var brUkupnih = $scope.brUkupnih;
							
							console.log(oznaka);
							console.log(naziv);
							console.log(vrsta);
							console.log(datumOd);
							console.log(datumDo);
							console.log(mesto);
							console.log(brPozitivnih);
							console.log(brUkupnih);
						}
			
						$scope.close = function(){
							$uibModalInstance.close();
						}
		}]);