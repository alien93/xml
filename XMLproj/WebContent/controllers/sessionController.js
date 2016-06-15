angular.module('xmlApp')
		.controller('sessionController', ['$rootScope', '$scope', '$location', '$http',
			function($rootScope, $scope, $location, $http){
				if ($rootScope.user.role == "CITIZEN") {
					$location.path('/prijava');
				};	
			
			//retrieving non active acts
				$http({
					method: "GET", 
					url : "http://localhost:8080/XMLproj/rest/act/nonActive",
				}).then(function(value) {
					$scope.acts = value.data.results.bindings;
				});		
			
			$scope.contentForActVisible=[];
			
			var closeAll = function(){
				for(i=0; i<$scope.contentForActVisible.length; i++){
					$scope.contentForActVisible[i] = false;
				}
			}
			
			$scope.expandAct = function(index, value) {
				
				closeAll();
				$scope.contentForActVisible[index] = true;
				console.log(value);
				var restUrl = "http://localhost:8080/XMLproj/rest/amendment/amendmentsForAct/" + value;
				console.log(restUrl);
				if($scope.contentForActVisible[index]){
					$http({
						method: "GET", 
						url : restUrl,
					}).then(function(retVal) {
						console.log(retVal);
						$scope.amendments = {"data":retVal.data.results.bindings, "glasMin":[], "glasMax":[]};
						console.log(retVal.data.results.bindings);
					});	
				}
			};
			$scope.testIfVisible = function(index) {
				return $scope.contentForActVisible[index];
			};
			
			$scope.openAmendment = function(amName) {
				$http({
					method : "GET",
					url : "http://localhost:8080/XMLproj/rest/amendment/amendmentId/" + amName,
					responseType: 'arraybuffer'
				}).then(function(result){
					var file = new Blob([result.data], {type: 'application/pdf'});
				    var fileURL = URL.createObjectURL(file);
				    window.open(fileURL);
				}, function(reason){
					console.log(JSON.stringify(reason));
				});
			};
			
			$scope.openAct = function(docName){
				console.log(docName);
				$http({
					method : "GET",
					url : "http://localhost:8080/XMLproj/rest/act/nonActiveId/" + docName,
					responseType: 'arraybuffer'
				}).then(function(result){
					var file = new Blob([result.data], {type: 'application/pdf'});
				    var fileURL = URL.createObjectURL(file);
				    window.open(fileURL);
				}, function(reason){
					console.log(JSON.stringify(reason));
				});
			};
			
			$scope.acceptAmendment = function(amendmentId){
				console.log(amendmentId);
			}
			
			$scope.sessionSubmit = function(actId) {
				//ukoliko se akt prihvata u celini, automatski prihvati i sve amandmane
				if($scope.uCelini == true){
					console.log("Akt se prihvata u celini");
				}
				else{
					//prihvati amandman
				}
				
				
				console.log(actId);
				console.log($scope.uNacelu);
				for(var i=0; i<$scope.amendments.data.length; i++){
					console.log($scope.amendments.data[i].oznakaAmandman.value);
					console.log($scope.amendments.glasMin[i]);
					console.log($scope.amendments.glasMax[i]);
				}
				console.log($scope.uCelini);
			};
		
		
		}
		]);