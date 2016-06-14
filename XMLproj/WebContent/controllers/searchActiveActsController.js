angular.module('xmlApp')
		.controller('searchActiveActsController', ['$scope', '$http', '$uibModal',
			function($scope, $http, $uibModal){
				$scope.searchText = "";
				$scope.selectedCategory = "Sadrzaj";
				
				$scope.search = function() {
					var text = ($scope.searchText.trim() == "" ? "_" : $scope.searchText.trim())
					var urlRest = $scope.selectedCategory != "Sadrzaj" ? "http://localhost:8080/XMLproj/rest/filter/activeActs/" + text + "/" + $scope.selectedCategory
							: "http://localhost:8080/XMLproj/rest/searchText/active/" + text;
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
							templateUrl: 'views/advancedSearchActive_m.html',
							controller: 'advancedSearchActiveController',
							resolve:{
								acts : function(){
									return $scope.$parent.$parent.acts;
								},
								parentScope : function(){
									return $scope;
								}
							}
					});
				}
			}
		])
		
		.controller('advancedSearchActiveController', ['$scope', '$uibModalInstance', '$http', '$filter', 'acts', 'parentScope', 
		        function($scope, $uibModalInstance, $http, $filter, acts, parentScope){
						
						$scope.search = function(){
							var oznaka = $scope.oznaka;
							var naziv = $scope.naziv;
							var vrsta = $scope.vrsta;
							var datumOd = $filter('date')($scope.datumOd, "yyyy-MM-dd"); 
							if(datumOd == undefined) datumOd = "_";
							var datumDo = $filter('date')($scope.datumDo, "yyyy-MM-dd");
							if(datumDo == undefined) datumDo = "_";
							var mesto = $scope.mesto;
							$http({
								method: "GET",
								url : "http://localhost:8080/XMLproj/rest/advanced/search/"+ "" +
										$scope.getVal(oznaka) + "/" + $scope.getVal(naziv) + "/" + $scope.getVal(vrsta) + "/" + 
										datumOd + "/" + datumDo + "/" + $scope.getVal(mesto) + "/" + 
										"_/_/_/_/" + "/propisi/akti/doneti"
							}).then(function(result){
								parentScope.$parent.$parent.acts = result.data.results.bindings;
								$uibModalInstance.close();
							},function(err){
								console.log(JSON.stringify(err));
							});
						}
						
						$scope.getVal = function(val){
							return (!val || "" == val.trim()) ? "_" : val;
						}
						
						$scope.close = function(){
							$uibModalInstance.close();
						}
		}]);