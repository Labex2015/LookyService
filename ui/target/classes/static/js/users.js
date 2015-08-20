angular.module('mainApp')
       .controller('UserController', function($scope, $http, $modal, $location, $filter, userService){

            $scope.hasErrors = false;
            $scope.message = "Testing scope";
            $scope.error = {};
            $scope.callForm = false;
            $scope.opened = false;
            $scope.validNewArea = true;

            $scope.user = {};
            $scope.users = [];

            $scope.response = {};

            $http.get('resource/users')
            .success(function(data, status, headers,config){
                $scope.users = data;
            }).error(function(data, status, headers,config){
                $scope.users = [];
                $scope.error = data;
                $scope.hasErrors = true;
            });

            $scope.clear = function clear(){
                $scope.error = {};
                $scope.callForm = false;
                $scope.opened = false;

                $scope.user = {};
                $scope.users = [];
            }

            $scope.requestHelp = function requestHelp(id){
                var url = "resource/user/"+userService.user.idUser+"/request/"+id;
                var  verify = confirm("Solicitar ajuda?");
                if(verify == true){
                    $http.get(url)
                    .success(function(data, status, headers,config){
                        $scope.response = data;
                        alert($scope.response.message);
                    }).error(function(data, status, headers,config){
                        $scope.users = [];
                        $scope.error = data;
                        $scope.hasErrors = true;
                    });
                }
            }

       });
