angular.module('mainApp')
       .controller('InteractionsController', function($scope, $http, $modal, $location, $filter,
                                                userService){

            $scope.templateUrl= { name: 'chat.html', url: '"/views/chat.html"'}
            $scope.hasErrors = false;
            $scope.message = "Testing scope";
            $scope.error = {};

            $scope.wsocket;
            $scope.serviceLocation = "ws://54.94.220.165:8383/looky-chat-1.0.0/chat/";


            $scope.user = userService.user;
            $scope.userContact = {};
            $scope.interactions = [];
            $scope.interaction = {};

            $scope.response = {};
            $scope.openChat = false;
            $scope.chatKey = "";

            var $message;
            var $chatWindow;
            var msg = {
                    message: "",
                    idFrom:  0,
                    sender:  "",
                    idTo: 0,
                    type:"M"
                };

            $scope.clear = function clear(){
                $scope.error = {};
                $scope.callForm = false;
                $scope.opened = false;

                $scope.user = {};
                $scope.users = [];
            }



            $scope.loadInteractions = function loadInteractions(){
                var url = "resource/user/"+userService.user.idUser+"/interactions/helping";
                $http.get(url)
                .success(function(data, status, headers, config) {
                     console.log(data);
                    $scope.interactions = data;
                  })
                 .error(function(data, status, headers, config) {
                    $scope.hasErrors = true;
                    $scope.error = data;
                  });
            }

            $scope.loadInteractions();

            $scope.openProfile = function openProfile(user){
                profileService.user = user;
                storeService.users = $scope.users;
                storeService.search = $scope.search;
                $location.path("/profile");
            }

            $scope.openChat = function openChat(interaction){
                $scope.userContact = interaction.content.userHelper.isUser == $scope.user.idUser ? interaction.content.userRequest : interaction.content.userHelper;
                var url = "/resource/users/interaction/"+interaction.content.userRequest.idUser+"/"+interaction.content.userHelper.idUser;
                $http.get(url)
                .success(function(data, status, headers, config) {
                     console.log(data);
                     $scope.chatKey = data.message;
                     $scope.openChat = true;
                     connectToChatserver();
                  })
                 .error(function(data, status, headers, config) {
                    $scope.hasErrors = true;
                    $scope.error = data;
                    $scope.openChat = false;
                  });
            }

            $scope.closeInteraction = function closeInteraction(interaction){

            }


            $scope.closeChat = function closeChat(){
                $scope.openChat = false;
            }

            function onMessageReceived(evt) {
                var msg = JSON.parse(evt.data); // native API
                var $messageLine = $('<tr><td class="received">' + msg.received
                        + '</td><td class="user label label-info">' + msg.sender
                        + '</td><td class="message badge">' + msg.message
                        + '</td></tr>');
                $chatWindow.append($messageLine);
            }

            function sendMessage() {
                msg.message= $message.val();
                msg.idFrom= $idFrom.val();
                msg.sender= $nickName.val();
                msg.idTo= $idTo.val();

                $scope.wsocket.send(JSON.stringify(msg));
                $message.val('').focus();
            }

            function connectToChatserver() {
                $scope.wsocket = new WebSocket($scope.serviceLocation+$scope.chatKey+"/"+$scope.user.idUser+"/"+$scope.user.username);
                $scope.wsocket.onmessage = onMessageReceived;
                $chatWindow = $('#response');
            }

            function leaveRoom() {
                $scope.wsocket.close();
                $chatWindow.empty();
                $('.chat-wrapper').hide();
                $('.chat-signin').show();
                $nickName.focus();
            }



       });
