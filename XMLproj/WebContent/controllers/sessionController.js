angular.module('xmlApp')
.controller('sessionController', ['$rootScope', '$scope', '$location', '$http', '$uibModal',
                                  function($rootScope, $scope, $location, $http, $uibModal){
	if ($rootScope.user.role == "CITIZEN") {
		$location.path('/prijava');
	};	

	//retrieving non active acts
	retrieveData = function(){
		$http({
			method: "GET", 
			url : "http://localhost:8080/XMLproj/rest/act/nonActive",
		}).then(function(value) {
			$rootScope.acts = value.data.results.bindings;
			console.log($scope.acts);
		});	
		if (!$scope.$$phase) {
			$scope.$apply();
		}
	}
	retrieveData();

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

	
	/**
	 * Vraca true ukoliko akt nece biti prihvacen ni u nacelu ni
	 * u celini. Setuje prihvatanje amandmana na false i
	 * disable-uje izmenu dozvole primene amandmana.
	 */
	$scope.changed = function(){
		if($scope.uNacelu == false && $scope.uCelini == false){
			for(var i=0; i<$scope.amendments.data.length; i++){
				$scope.amendments.primeni[i] = false;
			}
			return true;
		}
		else if($scope.uCelini == true){
			for(var i=0; i<$scope.amendments.data.length; i++){
				$scope.amendments.primeni[i] = true;
			}
			$scope.uNacelu = true;
			return true;
		}
		else
			return false;
	}

	/**
	 * Proverava da li postoje amandmani koje treba prihvatiti
	 */
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

	/**
	 * Uklanja akt iz prethodne kolekcije
	 */
	var removeActFromPreviousCollection = function(amandmanXml, result1, actId){
		$http({
			method : "POST",
			url : "http://localhost:8080/XMLproj/rest/act/removeAct/" + actId,
		}).then(function(result){			
			console.log(result);
		}, function(reason){
			console.log(JSON.stringify(reason));
		});
	}

	/**
	 * Kopira akt iz kolekcije "u_proceduri" u "prihvaceni"
	 */
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

	/**
	 * Postavlja status amandmana na "odbijen"
	 */
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

	/**
	 * Dobavlja sadrzaj xml dokumenta amandmana na osnovu oznake amandmana
	 */
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

	/**
	 * Menja status akta na "odbijen"
	 */
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
			if($rootScope.amendments.data.length == 0){
				changeActsCollection(null, result1, actId);
			}

		}, function(reason){
			console.log(JSON.stringify(reason));
		});
	}

	/**
	 * Odbija sve akte i amandmane
	 */
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
	 * Prosledjuje podatke o odluci o aktu
	 */
	$scope.sessionSubmit = function(actId) {
		//Akt se prihvata u celini, automatski prihvati i sve amandmane (promeni kolekcije)
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
					var idx = -1;
					for(var i = 0; i<$scope.acts.length; i++){
						if($scope.acts[i].oznaka.value == actId){
							idx = i;
							break;
						}
					}
					if(idx!=-1){
						$scope.acts.splice(idx, 1);
					}
					
					/*
					 * $scope.remove = function(item) { 
  var index = $scope.bdays.indexOf(item);
  $scope.bdays.splice(index, 1);     
}
					 * 
					 * */
				}
			}
		}

		//-----------------------------------------/session--------------------------------------------
	};
}
])

.controller('actDataController', ['$scope', '$rootScope', '$uibModalInstance', '$http', 'actId', 'scenario', 'amendments', 
                                  function($scope, $rootScope, $uibModalInstance, $http, actId, scenario, amendments){
	
	var actRemoved = false;

	/**
	 * Dopuni akt odgovarajucim podacima iz amandmana
	 */
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

	/**
	 * Ukloni akt iz prethodne kolekcije ("u_proceduri")
	 */
	var removeActFromPreviousCollection = function(amandmanXml, result1, actId){
		$http({
			method : "POST",
			url : "http://localhost:8080/XMLproj/rest/act/removeAct/" + actId,
		}).then(function(result){
			if(amandmanXml != null)
				updateAct(amandmanXml, result1, actId);
			console.log(result);
		}, function(reason){
			console.log(JSON.stringify(reason));
		});
	}

	/**
	 * Kopiraj akt iz kolekcije "u_proceduri" u kolekciju "doneti"
	 */
	var changeActsCollection = function(amandmanXml, result1, actId){
		actRemoved = true;
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

	/**
	 * Promeni status amandmana na "prihvacen"
	 */
	var changeAmendmentsStatus = function(result, result1, actId){
		$http({
			method : "POST",
			url : "http://localhost:8080/XMLproj/rest/amendment/changeStatus/prihvacen",
			headers : {
				"Content-Type" : "application/xml"
			},
			data : result.data
		}).then(function(amandmanXml){
			//promeni kolekciju akta
			if(!actRemoved)
				changeActsCollection(amandmanXml, result1, actId);
			else
				updateAct(amandmanXml, result1, actId);
		})
	}

	/**
	 * Dobavi sadrzaj xml dokumenta amandmana na osnovu oznake amandmana
	 */
	var getXmlByAmendmentsId = function(amendmentId, result, result1, actId){
		$http({
			method : "GET",
			url : "http://localhost:8080/XMLproj/rest/amendment/xmlById/" + amendmentId,
		}).then(function(result){
			//prosledi sadrzaj metodi koja vrsi izmenu statusa amandmana
			changeAmendmentsStatus(result, result1, actId);

		})
	}

	/**
	 * Promeni status akta na "donet"
	 */
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
				if($rootScope.amendments.data.length == 0){
					changeActsCollection(null, result1, actId);
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
				if($rootScope.amendments.data.length == 0){
					changeActsCollection(null, result1, actId);
				}
			}
		}, function(reason){
			console.log(JSON.stringify(reason));
		});
	}

	/**
	 * Prihvati akte i amandmane
	 */
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

	/**
	 * Zatvaranje dijaloga
	 */
	$scope.close = function(){
		if(scenario == 1){		//prihvati sve akte i amandmane
			actRemoved = false;
			acceptActAndAmendments(actId, $scope.odStrane, $scope.pravniOsnov);
			var idx = -1;
			for(var i = 0; i<$rootScope.acts.length; i++){
				if($rootScope.acts[i].oznaka.value == actId){
					idx = i;
					break;
				}
			}
			if(idx!=-1){
				$rootScope.acts.splice(idx, 1);
			}			
			$uibModalInstance.close();
		}
		else if(scenario == 2){	//prihvati akt i neke amandmane
			actRemoved = false;
			acceptActAndAmendments(actId, $scope.odStrane, $scope.pravniOsnov);
			var idx = -1;
			for(var i = 0; i<$rootScope.acts.length; i++){
				if($rootScope.acts[i].oznaka.value == actId){
					idx = i;
					break;
				}
			}
			if(idx!=-1){
				$rootScope.acts.splice(idx, 1);
			}			
			$uibModalInstance.close();
		}
		else{
			$uibModalInstance.close();
		}
	}
}]);