angular.module('mainApp')
       .controller('HelperController', function($scope, $http, $modal, $location, $filter,
                                                userService, profileService, storeService){

            $scope.hasErrors = false;
            $scope.message = "Testing scope";
            $scope.error = {};
            $scope.callForm = false;
            $scope.opened = false;
            $scope.validNewArea = true;
            $scope.user = {};

            if(storeService.users){
                 $scope.users = storeService.users;
                 $scope.search = storeService.search;
            }else{
                $scope.search = "";
                $scope.users = [];
            }
            $scope.image = "study.jpg";

            $scope.helpWrapper = {
                user: userService.user,
                searchTerms: [],
                text:""
            };

            $scope.response = {};

            $scope.clear = function clear(){
                $scope.error = {};
                $scope.callForm = false;
                $scope.opened = false;

                $scope.user = {};
                $scope.users = [];
            }



            $scope.searchHelpers = function searchHelpers(){
                if($scope.search || $scope.search.length > 0){
                    var url = "resource/user/help/search";
                    var requestWrapper = {
                        user: userService.user,
                        searchTerms: $scope.search,
                        text: "Me ajude =D"
                    }
                    $http.post(url, requestWrapper)
                    .success(function(data, status, headers, config) {
                         console.log(data);
                         $scope.users = data;
                      })
                     .error(function(data, status, headers, config) {
                        $scope.hasErrors = true;
                        $scope.error = data;
                      });
                  }else{
                    $scope.users = [];
                  }
            }

            $scope.openProfile = function openProfile(user){
                profileService.user = user;
                storeService.users = $scope.users;
                storeService.search = $scope.search;
                $location.path("/profile");
            }


       });
