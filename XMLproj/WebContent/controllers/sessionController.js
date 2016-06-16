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
						$scope.amendments = {"data":retVal.data.results.bindings, "glasMin":[], "glasMax":[], "primeni":[]};
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
			
			
			//---------------------------------------------------session--------------------------------------
			
			var changeActsCollection = function(result1){
				$http({
					method : "POST",
					url : "http://localhost:8080/XMLproj/rest/act/changeCollection/doneti",
					headers : {
						"Content-Type": "application/xml"
					},
					data : result1.data
				});
			}
			
			var changeAmendmentsCollection = function(result, result1){
				$http({
					method : "POST",
					url : "http://localhost:8080/XMLproj/rest/act/updateAct/" + actId,
					headers : {
						"Content-Type" : "application/xml"
					},
					data : result.data
				}).then(function(result){
					//promeni kolekciju akta
					changeActsCollection(result1);
				})
			}
			
			var changeAmendmentsStatus = function(result, result1){
				$http({
					method : "POST",
					url : "http://localhost:8080/XMLproj/rest/amendment/changeStatus/prihvacen",
					headers : {
						"Content-Type" : "application/xml"
					},
					data : result.data
				}).then(function(result){
					//promeni kolekciju amandmana
					changeAmendmentsCollection(result, result1);
				})
			}
			
			var getXmlByAmendmentsId = function(amendmentId, result, result1){
				$http({
					method : "GET",
					url : "http://localhost:8080/XMLproj/rest/amendment/xmlById/" + amendmentId,
				}).then(function(result){
					//prosledi sadrzaj metodi koja vrsi izmenu statusa amandmana
					changeAmendmentsStatus(result, result1);
					
				})
			}
			
			var changeActsStatus = function(result){
				$http({
					method : "POST",
					url : "http://localhost:8080/XMLproj/rest/act/changeStatus/donet",
					headers : {
						"Content-Type": "application/xml"
					},
					data : result.data
				}).then(function(result1){
					//primeni amandmane
					console.log(result1.data);
					for(var i=0; i<$scope.amendments.data.length; i++){
						var amendmentId = $scope.amendments.data[i].oznakaAmandman.value;
						//dobavi sadrzaj xml fajla na oznovu oznaka amandmana
						getXmlByAmendmentsId(amendmentId, result, result1);
					}
				}, function(reason){
					console.log(JSON.stringify(reason));
				});
			}
			
			var acceptActAndAmendments = function(actId){
				$http({
					method : "GET",
					url : "http://localhost:8080/XMLproj/rest/act/xmlById/" + actId,
				}).then(function(result){
					console.log(result.data);
					//prosledi sadrzaj metodi koja vrsi izmenu statusa akta
					changeActsStatus(result);
				}, function(reason){
					console.log(JSON.stringify(reason));
				});
			}
			
			
			$scope.sessionSubmit = function(actId) {
				//ukoliko se akt prihvata u celini, automatski prihvati i sve amandmane (promeni kolekcije)
				if($scope.uCelini == true){
					acceptActAndAmendments(actId);					
				}
				else{
					if($scope.uNacelu == true){
						console.log("Akt se prihvata u nacelu, promeni status u 'donet' i primeni amandmane koje treba primeniti")
					}
					else{
						if(acceptingAmendment()){	//postoje amandmani koje treba primeniti
							console.log("Primeni amandmane koje treba primeniti")
						}
						else{ //ne postoje amandmani
							console.log("Smesti akt u povucene i amandmane")
						}
					}
				}
				
				//-----------------------------------------/session--------------------------------------------
				
				
			/*	console.log(actId);
				console.log($scope.aktGlasMin);
				console.log($scope.aktGlasMax);
				console.log($scope.uNacelu);
				for(var i=0; i<$scope.amendments.data.length; i++){
					console.log($scope.amendments.data[i].oznakaAmandman.value);
					console.log($scope.amendments.glasMin[i]);
					console.log($scope.amendments.glasMax[i]);
					console.log($scope.amendments.primeni[i]);
				}
				console.log($scope.uCelini);*/
			};
		
		
		}
		]);