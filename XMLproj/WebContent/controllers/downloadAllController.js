angular.module('xmlApp')
		.controller('downloadAllController', ['$scope', '$http',
			function($scope, $http){
			
				$scope.msg = "IZVOZ SVIH DOKUMENATA U XHTML/PDF";
				$scope.lColor = "blue";
				
				$scope.downloadAll = function() {
					if($scope.lColor == "red") return;
					$scope.lColor = "red";
					$scope.msg = "Skidanje je u toku...";
					
					var urlRest =  "http://localhost:8080/XMLproj/rest/zip/getFiles";
					$http({
						method: "GET",
						url : urlRest,
						responseType: 'arraybuffer'
					}).then(function(result){
						console.log("Uspjesno skinuto.");
						var a = document.createElement('a');
						var blob = new Blob([result.data], {'type':"application/octet-stream"});
						a.href = URL.createObjectURL(blob);
						a.download = "XHTML_PDF.zip";
						a.click();
						$scope.msg = "IZVOZ SVIH DOKUMENATA U XHTML/PDF";
						$scope.lColor = "blue";
					},function(reason){
						console.log(JSON.stringify(reason));
					});
				};
		}]);