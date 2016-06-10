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
							"/odbornik/aktaUProceduri",
							{
									templateUrl : "views/nonActiveActsAlderman.html"
							}
						)
						.when(
							"/odbornik/predlogAkta",
							{
									templateUrl : "views/acts.html"
							}
						)
						.when(
							"/odbornik/predlogAmandmana",
							{
									templateUrl : "views/amandments.html"
							}
						)
						.when(
							"/predsednik",
							{
									templateUrl: "views/president.html"
							}
						)
						.when(
							"/predsednik/aktaUProceduri",
							{
									templateUrl: "views/nonActiveActsPresident.html"
							}
						)
						.when(
							"/predsednik/predlogAkta",
							{
									templateUrl: "views/actsPresident.html"
							}
						)
						.when(
							"/predsednik/predlogAmandmana",
							{
									templateUrl: "views/amandmentsPresident.html"
							}
						)
						.when(
							"/predsednik/sednica",
							{
									templateUrl: "views/session.html"
							}
						)
						.when(
							"/prijava",
							{
									templateUrl: "views/login.html"
							}
						)
						.otherwise(
							{
							redirectTo: "/gradjanin"
							}	
						);
	}
);