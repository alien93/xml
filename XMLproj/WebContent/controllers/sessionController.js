angular.module('xmlApp')
.controller('sessionController', ['$rootScope', '$scope', '$location', '$http', '$uibModal',
                                  function($rootScope, $scope, $location, $http, $uibModal){
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
				$rootScope.amendments = {"data":retVal.data.results.bindings, "glasMin":[], "glasMax":[], "primeni":[]};
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

	$scope.changed = function(){
		if($scope.uNacelu == false && $scope.uCelini == false){
			for(var i=0; i<$scope.amendments.data.length; i++){
				$scope.amendments.primeni[i] = false;
			}
			return true;
		}
		else
			return false;
	}

	//proveri da li postoje amandmani koje treba prihvatiti
	var acceptingAmendment = function(){
		var retVal = false;
		for(var i=0; i<$scope.amendments.data.length; i++){
			if($scope.amendments.primeni[i] == true){
				retVal = true;
				break;
			}
		}
		return retVal;
	}

	var removeActFromPreviousCollection = function(amandmanXml, result1, actId){
		$http({
			method : "POST",
			url : "http://localhost:8080/XMLproj/rest/act/removeAct/" + actId,
		}).then(function(result){
			//$scope.acts.splice(rowIndex, 1);
			console.log(result);
		}, function(reason){
			console.log(JSON.stringify(reason));
		});
	}

	var changeActsCollection = function(amandmanXml, result1, actId){
		$http({
			method : "POST",
			url : "http://localhost:8080/XMLproj/rest/act/changeCollection/povuceni",
			headers : {
				"Content-Type": "application/xml"
			},
			data : result1.data
		})
		.success(function(result){
			removeActFromPreviousCollection(amandmanXml, result1, actId);
		});
	}


	var changeAmendmentsStatus = function(result, result1, actId){
		console.log("bitno");
		console.log(result.data);
		$http({
			method : "POST",
			url : "http://localhost:8080/XMLproj/rest/amendment/changeStatus/odbijen",
			headers : {
				"Content-Type" : "application/xml"
			},
			data : result.data
		}).then(function(amandmanXml){
			//promeni kolekciju amandmana
			changeActsCollection(amandmanXml, result1, actId);
		})
	}

	var getXmlByAmendmentsId = function(amendmentId, result, result1, actId){
		$http({
			method : "GET",
			url : "http://localhost:8080/XMLproj/rest/amendment/xmlById/" + amendmentId,
		}).then(function(result){
			//prosledi sadrzaj metodi koja vrsi izmenu statusa amandmana
			console.log('bitno2');
			console.log(result.data);
			changeAmendmentsStatus(result, result1, actId);

		})
	}

	var changeActsStatus = function(result, actId){
		$http({
			method : "POST",
			url : "http://localhost:8080/XMLproj/rest/act/changeStatus/odbijen",
			headers : {
				"Content-Type": "application/xml"
			},
			data : result.data
		}).then(function(result1){
			//primeni amandmane
			for(var i=0; i<$rootScope.amendments.data.length; i++){
				var amendmentId = $rootScope.amendments.data[i].oznakaAmandman.value;
				//dobavi sadrzaj xml fajla na oznovu oznaka amandmana
				getXmlByAmendmentsId(amendmentId, result, result1, actId);
			}

		}, function(reason){
			console.log(JSON.stringify(reason));
		});
	}

	var rejectAll = function(actId){
		$http({
			method : "GET",
			url : "http://localhost:8080/XMLproj/rest/act/xmlById/" + actId,
		}).then(function(result){
			console.log(result.data);
			//prosledi sadrzaj metodi koja vrsi izmenu statusa akta
			changeActsStatus(result, actId);
		}, function(reason){
			console.log(JSON.stringify(reason));
		});
	}

	/**
	 * Submit session data
	 */
	$scope.sessionSubmit = function(actId) {
		//ukoliko se akt prihvata u celini, automatski prihvati i sve amandmane (promeni kolekcije)
		if($scope.uCelini == true){
			var modalInstance = $uibModal.open({
				animation: false,
				templateUrl: 'views/actData_m.html',
				controller: 'actDataController',
				resolve:{
					actId : function(){
						return actId
					},
					scenario : function(){
						return 1;
					},
					amendments: function(){
						return $scope.amendments;
					}
				}
			});
		}
		else{
			//Akt se prihvata u nacelu, promeni status u 'donet' i primeni amandmane koje treba primeniti
			if($scope.uNacelu == true){
				var modalInstance = $uibModal.open({
					animation: false,
					templateUrl: 'views/actData_m.html',
					controller: 'actDataController',
					resolve:{
						actId : function(){
							return actId
						},
						scenario : function(){
							return 2;
						},
						amendments: function(){
							return $scope.amendments;
						}
					}
				});
			}
			else{
				//akt se ne prihvata ni u celini ni u nacelu, odbij sve
				if(!acceptingAmendment()){	
					rejectAll(actId);
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
])

.controller('actDataController', ['$scope', '$rootScope', '$uibModalInstance', '$http', 'actId', 'scenario', 'amendments', 
                                  function($scope, $rootScope, $uibModalInstance, $http, actId, scenario, amendments){

	var updateAct = function(amandmanXml, result1, actId){
		$http({
			method : "POST",
			url : "http://localhost:8080/XMLproj/rest/act/updateAct/" + actId,
			headers : {
				"Content-Type" : "application/xml"
			},
			data : amandmanXml.data
		}).then(function(result){			
			console.log(result);
		})
	}

	var removeActFromPreviousCollection = function(amandmanXml, result1, actId){
		$http({
			method : "POST",
			url : "http://localhost:8080/XMLproj/rest/act/removeAct/" + actId,
		}).then(function(result){
			//$scope.acts.splice(rowIndex, 1);
			if(amandmanXml != null)
				updateAct(amandmanXml, result1, actId);
			console.log(result);
		}, function(reason){
			console.log(JSON.stringify(reason));
		});
	}

	var changeActsCollection = function(amandmanXml, result1, actId){
		$http({
			method : "POST",
			url : "http://localhost:8080/XMLproj/rest/act/changeCollection/doneti",
			headers : {
				"Content-Type": "application/xml"
			},
			data : result1.data
		})
		.success(function(result){
			removeActFromPreviousCollection(amandmanXml, result1, actId);
		});
	}


	var changeAmendmentsStatus = function(result, result1, actId){
		$http({
			method : "POST",
			url : "http://localhost:8080/XMLproj/rest/amendment/changeStatus/prihvacen",
			headers : {
				"Content-Type" : "application/xml"
			},
			data : result.data
		}).then(function(amandmanXml){
			//promeni kolekciju amandmana
			changeActsCollection(amandmanXml, result1, actId);
		})
	}

	var getXmlByAmendmentsId = function(amendmentId, result, result1, actId){
		$http({
			method : "GET",
			url : "http://localhost:8080/XMLproj/rest/amendment/xmlById/" + amendmentId,
		}).then(function(result){
			//prosledi sadrzaj metodi koja vrsi izmenu statusa amandmana
			changeAmendmentsStatus(result, result1, actId);

		})
	}

	var changeActsStatus = function(result, actId){
		$http({
			method : "POST",
			url : "http://localhost:8080/XMLproj/rest/act/changeStatus/donet",
			headers : {
				"Content-Type": "application/xml"
			},
			data : result.data
		}).then(function(result1){
			//primeni amandmane
			if(scenario == 1){
				for(var i=0; i<$rootScope.amendments.data.length; i++){
					var amendmentId = $rootScope.amendments.data[i].oznakaAmandman.value;
					//dobavi sadrzaj xml fajla na oznovu oznaka amandmana
					getXmlByAmendmentsId(amendmentId, result, result1, actId);
				}
			}
			else if(scenario == 2){
				for(var i=0; i<$rootScope.amendments.data.length; i++){
					if($rootScope.amendments.primeni[i] == true){
						var amendmentId = $rootScope.amendments.data[i].oznakaAmandman.value;
						//dobavi sadrzaj xml fajla na oznovu oznaka amandmana
						getXmlByAmendmentsId(amendmentId, result, result1, actId);
					}
					else{
						changeActsCollection(null, result1, actId);
					}
				}
			}
		}, function(reason){
			console.log(JSON.stringify(reason));
		});
	}

	var acceptActAndAmendments = function(actId, odStrane, pravniOsnov){
		$http({
			method : "POST",
			url : "http://localhost:8080/XMLproj/rest/act/xmlById/" + actId,
			data : odStrane + "$$$$" + pravniOsnov
		}).then(function(result){
			console.log(result.data);
			//prosledi sadrzaj metodi koja vrsi izmenu statusa akta
			changeActsStatus(result, actId);
		}, function(reason){
			console.log(JSON.stringify(reason));
		});
	}

	$scope.close = function(){
		if(scenario == 1){
			acceptActAndAmendments(actId, $scope.odStrane, $scope.pravniOsnov);
			$uibModalInstance.close();
		}
		else if(scenario == 2){
			acceptActAndAmendments(actId, $scope.odStrane, $scope.pravniOsnov);
			$uibModalInstance.close();
		}
		else{
			$uibModalInstance.close();
		}
	}
}]);