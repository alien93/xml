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
						}
					}
				});
			}
			else{
				//postoje amandmani koje treba primeniti
				if(acceptingAmendment()){	
					acceptAmendments(actId);
				}
				//Smesti akt u povucene i amandmane u odbijene
				else{
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

.controller('actDataController', ['$scope', '$rootScope', '$uibModalInstance', '$http', 'actId', 'scenario',  
                                  function($scope, $rootScope, $uibModalInstance, $http, actId, scenario){


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

	var changeAmendmentsCollection = function(result, result1, actId){
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

	var changeAmendmentsStatus = function(result, result1, actId){
		$http({
			method : "POST",
			url : "http://localhost:8080/XMLproj/rest/amendment/changeStatus/prihvacen",
			headers : {
				"Content-Type" : "application/xml"
			},
			data : result.data
		}).then(function(result){
			//promeni kolekciju amandmana
			changeAmendmentsCollection(result, result1, actId);
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
			console.log(result1.data);
			for(var i=0; i<$rootScope.amendments.data.length; i++){
				var amendmentId = $rootScope.amendments.data[i].oznakaAmandman.value;
				//dobavi sadrzaj xml fajla na oznovu oznaka amandmana
				getXmlByAmendmentsId(amendmentId, result, result1, actId);
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
			acceptActAndSomeAmendments(actId);
			$uibModalInstance.close();
		}
		else{
			$uibModalInstance.close();
		}
	}
}]);