angular.module('xmlApp')
		.controller('amendmentsController', ['$rootScope', '$scope', '$location', '$http',
			function($rootScope, $scope, $location, $http){
					if ($rootScope.user.role == "CITIZEN") {
						$location.path('/prijava');
					};	
					
					$scope.insertTextVisible = false;
					$scope.showTextPart = function() {
						$scope.insertTextVisible = !$scope.insertTextVisible;
					};
					
					$scope.selectedAct = {};
					$scope.amendmentsForAct = [];
					
					$http({
						method: "GET", 
						url : "http://localhost:8080/XMLproj/rest/act/nonActive",
					}).then(function(value) {
						$scope.acts = value.data.results.bindings;
						$scope.selectedAct = $scope.acts[0];
						$scope.showAmendments();
					});
												
					$scope.showAmendments = function(){
						var val = $scope.selectedAct.oznaka.value;
						$scope.amendmentsForAct = [];
						$http({
							method: "GET", 
							url : "http://localhost:8080/XMLproj/rest/amendment/amendmentsForAct/" + val, 
						}).then(function(retVal) {
							$scope.amendmentsForAct = retVal.data.results.bindings;
						});	
					};
					
					$scope.saveAmendment = function() {
						
					};
					
					$scope.openAmendment = function(amName) {
						console.log(amName);
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
					
					$scope.deleteAmendment = function(idx) {
						console.log(idx);
						$http({
							method : "GET",
							url : "http://localhost:8080/XMLproj/rest/amendment/xmlById/" + idx,
						}).then(function(result){
							console.log(result.data);
							$http({
								method : "POST",
								url : "http://localhost:8080/XMLproj/rest/amendment/changeCollection",
								headers : {
									"Content-Type": "application/xml"
								},
								data : result.data
							}).then(function(result){
								$http({
									method : "POST",
									url : "http://localhost:8080/XMLproj/rest/amendment/removeAmendment/" + idx,
								}).then(function(result){
									$scope.showAmendments();
									console.log(result);
								}, function(reason){
									console.log(JSON.stringify(reason));
								});
								console.log(result);
							}, function(reason){
								console.log(JSON.stringify(reason));
							});
						}, function(reason){
							console.log(JSON.stringify(reason));
						});
					};
				}
		]);