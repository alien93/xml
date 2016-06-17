angular.module('xmlApp')
		.controller('downloadAllController', ['$scope', '$http',
			function($scope, $http){
				
				$scope.downloadAll = function() {
					var urlRest =  "http://localhost:8080/XMLproj/rest/zip/getFiles";
					$http({
						method: "GET",
						url : urlRest
					}).then(function(result){
						console.log("Uspjesno skinuto.");
					},function(reason){
						console.log(JSON.stringify(reason));
					});
				};
		}]);