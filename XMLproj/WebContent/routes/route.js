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
							"/gradjanin/aktaUProceduri",
							{
									templateUrl : "views/nonActiveActs.html"
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
						.when(
							"/prijava",
							{
									templateUrl: "views/login.html"
							}
						)
						.when(
							"/akti",
							{
									templateUrl: "views/acts.html"
							}
						)
						.otherwise(
							{
							redirectTo: "/gradjanin"
							}	
						);
	}
);