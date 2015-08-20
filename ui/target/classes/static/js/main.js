/*
  ngRoute => Módulo do Angular para usar as funcionalidades de rotas;
  ui.bootstrap => Módulo de terceiros que usa componentes do bootstrap e funcionalidades do Angular;
*/
var mainApp = angular.module('mainApp',['ngRoute', 'ui.bootstrap']);

    /**
        Módulo para carregar dados do usuário, ao logar.
    */
    angular.module('mainApp').service("userService", function(){
        var userService  = this;
        userService.user = {
            username: "Labex",
            email: "labex@gmail.com",
            idUser:2
        };
    });
    /**
        Módulo para carregar dados de terceiros.
    */
    angular.module('mainApp').service("profileService", function(){
        var profileService  = this;
        profileService.user = {};
    });

    angular.module('mainApp').service("storeService", function(){
            var profileService  = this;
    });

    /**
            Módulo que cuida das rotas;
        */
    angular.module('mainApp').config(function ($routeProvider){
        $routeProvider.when("/profile",{
            templateUrl: "./views/profile.html"
        });
        $routeProvider.otherwise({
            templateUrl: "./views/home.html"
        });
    });

    //Módulo principal.
    angular.module('mainApp').controller('MainController', function($scope, $http, $modal, profileService){

    });



