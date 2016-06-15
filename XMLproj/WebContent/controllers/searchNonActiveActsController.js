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
		
		.controller('advancedSearchNonActiveController', ['$scope', '$uibModalInstance', '$http', '$filter', 'acts', 'parentScope', 
		        function($scope, $uibModalInstance, $http, $filter, acts, parentScope){
			
						$scope.search = function(){
							var oznaka = $scope.oznaka;
							var naziv = $scope.naziv;
							var vrsta = $scope.vrsta;
							var mesto = $scope.mesto;
							var brPozitivnih = $scope.brPozitivnih;
							var brUkupnih = $scope.brUkupnih;
							
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
									$scope.getVal(brPozitivnih) + "/" + $scope.getVal(brPozitivnih) + 
									"/" + $scope.getVal(brUkupnih) + "/" + $scope.getVal(brUkupnih) + "/" + "/propisi/akti/u_proceduri"
						}).then(function(result){
							parentScope.$parent.$parent.acts = result.data.results.bindings;
							$uibModalInstance.close();
						},function(err){
							console.log(JSON.stringify(err));
						});
							
						}
						
						$scope.getVal = function(val){
							if(typeof val == 'number'){
								if(!val) return "_"; else return val;
							}
							return (!val || "" == val.trim()) ? "_" : val;
						}
						
						$scope.close = function(){
							$uibModalInstance.close();
						}
		}]);