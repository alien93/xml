angular.module('xmlApp')

	.controller('citizenController', ['$scope',
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
					$scope.acts = {"active" : [act, act2]};

					//-------------------------/test data----------------------

					
	}]);