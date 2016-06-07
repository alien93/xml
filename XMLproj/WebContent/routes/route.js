var xmlApp = angular.module('xmlApp', ['ngCookies', 'ngRoute','ngResource', 'ui.bootstrap']);

xmlApp.config(function($routeProvider){
				
				$routeProvider
						.when(
							"/",
							{
									templateUrl : "index.html"
							}
						)
						.when(
							"/gradjanin",
							{
									templateUrl : "citizen.html"
							}
						)
						.when(
							"/odbornik",
							{
									templateUrl : "alderman.html"
							}
						)
						.when(
							"/predsednik",
							{
									templateUrl: "president.html"
							}
						)
						.otherwise(
							{
							redirectTo: "/"
							}	
						);
	}
);
