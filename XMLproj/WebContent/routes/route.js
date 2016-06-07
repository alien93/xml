var xmlApp = angular.module('xmlApp', ['ngCookies', 'ngRoute','ngResource', 'ui.bootstrap']);

xmlApp.config(function($routeProvider){
				
				$routeProvider
						.when(
							"/gradjanin",
							{
									templateUrl : "views/citizen.html"
							}
						)
						.when(
							"/odbornik",
							{
									templateUrl : "views/alderman.html"
							}
						)
						.when(
							"/predsednik",
							{
									templateUrl: "views/president.html"
							}
						)
						.otherwise(
							{
							redirectTo: "/gradjanin"
							}	
						);
	}
);
