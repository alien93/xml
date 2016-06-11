angular.module('xmlApp')

	.controller('activeActsController', ['$scope', '$http',
	         function($scope, $http){
					
		
					//-------------------------test data----------------------
					var act = {
								name: "ODLUKA O IMENOVANJU GENERALNOG SEKRETARA NARODNE SKUPŠTINE",
								date: "6. jun 2016.",
								type: "Odluka"
								}
					var act2 = {
								name: "ODLUKA O BROJU I IZBORU POTPREDSEDNIKA NARODNE SKUPŠTINE",
								date: "6. jun 2016.",
								type: "Odluka"
								}
					
					$scope.acts = {
									"active" : [act, act2],
								  	"nonActive" : [act2]
					    		  };

					//-------------------------/test data----------------------
					
					$http({
						method: "GET", 
						url : "http://localhost:8080/XMLproj/rest/act/active",
					}).then(function(value) {
						console.log(value.data.results.bindings);
						$scope.acts = value.data.results.bindings;
					});
					
					
					//row click
					$scope.getSelected = function(docName){
						console.log(docName);
					}
					/*
					$http
				    .get(generatePdfUrl)
				    .then(function(data){
				        //data is link to pdf
				        $window.open(data);
				    }); 
				    */
	}]);