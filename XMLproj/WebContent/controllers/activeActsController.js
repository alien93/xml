angular.module('xmlApp')

	.controller('activeActsController', ['$scope',
	         function($scope){
					
		
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