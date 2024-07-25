var stompClientList = {};
var roomId = null;
var roomUserList = [];


var socketToServer = new SockJS("/isConnect");
var stompClientToServer = Stomp.over(socketToServer);


stompClientToServer.connect({}, function (frame) {
    console.log('Connected to server ' + loginUserId + ': ' + frame);

    // 사용자 연결 확인 구독
    stompClientToServer.subscribe('/chat/alive/' + loginUserId, function (chatMessage) {
        console.log('Received message: ' + chatMessage.body);

        const receivedMessage = chatMessage.body;
        const receivedMessageList = receivedMessage.split('/');
            $('.chat-room-list').append(`
                                          <li class="nav-item" >
                                              <a class="d-flex m-2 py-2 bg-light rounded-pill " data-bs-toggle="pill" data-room-id="${receivedMessageList[3]}" >
                                                  <span class="text-dark" style="width: 125px;"><div >${receivedMessageList[1]}</div></span>
                                                  <button type="button" class="btn-close del-room"  aria-label="Close"></button>
                                              </a>
                                          </li>
                                          `);
                                          connect(receivedMessageList[3]);

    });

    // 연결이 설정된 후에 연결 메시지 전송
//    stompClientToServer.send(`/connect/isAlive/${loginUserId}`, {}, JSON.stringify({ message: "User connected" }));
}, function (error) {
    setConnected(false);
    console.error('STOMP error: ' + error);
});

function setConnected(connected) {
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#chatting").html("");
}



function connect(roomId) {
    if (!stompClientList[roomId]) {
        var socket = new SockJS("/ws-stomp");
        var stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected to room ' + roomId + ': ' + frame);

            // 구독
            stompClient.subscribe('/chat/room/' + roomId, function (chatMessage) {
                showChat(JSON.parse(chatMessage.body), roomId);
            });
        });
        stompClientList[roomId] = stompClient;
    }
}

function disconnect(roomId) {
    if (stompClientList[roomId]) {
        stompClientList[roomId].disconnect();
        setConnected(false);
        console.log("Disconnected from room " + roomId);
        delete stompClientList[roomId];
    }
}

function sendChat() {
    var activeRoomId = $('.nav-item a.active').data('room-id');
    if ($("#message").val() != "" && stompClientList[activeRoomId]) {
        const messageContent = $("#message").val();
        const chatMessage = {
            content: messageContent,
            chatMember: {
                chat: {
                    id: activeRoomId,
                    name: "Chat Room Name"
                },
                member: {
                    memberId: loginUserId
                }
            }
        };
        stompClientList[activeRoomId].send(`/send/${activeRoomId}`, {}, JSON.stringify(chatMessage));
        $("#message").val('');
    }
}

// 저장된 채팅 불러오기
function loadChat(chatList) {
    if (chatList != null) {
        for (var chat in chatList) {
            if (chatList[chat].senderEmail == senderEmail) {
                $("#chatting").append(
                    "<div class='chatting_own'><tr><td>" + chatList[chat].message + "</td></tr></div>"
                );
            } else {
                $("#chatting").append(
                    "<div class='chatting'><tr><td>" + "[" + chatList[chat].sender + "] " + chatList[chat].message + "</td></tr></div>"
                );
            }
        }
    }
    $('.col-md-12').scrollTop($('.col-md-12')[0].scrollHeight);
}

// 보낸 채팅 보기
 function showChat(chatMessage, roomId) {
     var activeRoomId = $('.nav-item a.active').data('room-id');
     if (chatMessage.chatMember.member.memberId == loginUserId) {
         $("#chatting").append(
             "<div class='chatting_own'><tr><td>" + chatMessage.content + "</td></tr></div>"
         );
     } else {
         if (activeRoomId == chatMessage.chatMember.chat.id) {
             $("#chatting").append(
                 "<div class='chatting'><tr><td>" + "[" + chatMessage.chatMember.member.memberId + "] " + chatMessage.content + "</td></tr></div>"
             );
         }
     }
     $('.col-md-12').scrollTop($('.col-md-12')[0].scrollHeight);
 }

 // 모달 초기화
 function initModal() {
    $('.setUser').empty();
    $('#memo').val('');
    $('#keyword').val('');
    roomUserList=[];
 }

//친구 검색
const searchFriend = (e) => {
     const keyword = e.target.value.trim();
     $.ajax({
         url: '/chat/findFriend',
         data: { keyword: keyword },
         success: (data) => {
             $('.userList').empty();
             data.forEach((userId) => {
                let isNotIn = true;

                roomUserList.forEach((roomUser) => {
                    if(roomUser == userId){
                        isNotIn = false;
                    }
                })
                if(userId==loginUserId)
                    isNotIn = false;
                if(isNotIn){
                    $('.userList').append(`
                         <li class="list-group-item list-group-item-action add-user-id"
                             style="cursor:pointer;">${userId}</li>
                    `);
                }
             });
         }, error: (xhr, status, error) => {
              console.error('Error: ' + error);
              console.error('Status: ' + status);
              console.error(xhr);
         }
     });
}

$(document).ready(function() {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });

    $("#disconnect").click(function () {
        var roomId = $("#roomId").val();
        disconnect(roomId);
    });

    $("#send").click(function () { sendChat(); });

    $('.nav').on('click', '.nav-item .del-room', function() {
        const roomId = $(this).parent().data('room-id');
        $(this).parent().remove();
        $.ajax({
             url: '/chat/delRoom',
             data: { chatId: roomId, memberId:loginUserId },
             method: "post", // GET 메소드 사용
             success: (data) => {

             }, error: (xhr, status, error) => {
                  console.error('Error: ' + error);
                  console.error('Status: ' + status);
                  console.error(xhr);
             }
         });
    });

    $('.add-room').on('click', function(e) {
        let roomName = $("#memo").val();
        console.log(roomName);
        roomUserList.push(loginUserId);
        $.ajax({
            url: "/chat" ,
            data:{"memberIdList":roomUserList,"roomName": roomName},
            method: "post",
            success: function(data, status, xhr) {
                console.log(data);
                roomUserList=[];
                initModal();
                $('#staticBackdrop').modal('hide')
            },
            error: function(xhr, status, error) {
                console.error("Error occurred:", error);
                alert('채팅창 추가 실패')
            }
        });
    });

    $('.nav').on('click', '.nav-item a', function() {
          $("#chatting").empty();
          var roomId = $(this).data('room-id');

          $('.nav-item a').removeClass('active');
          $(this).addClass('active');
      });

    // 이벤트 위임을 사용하여 클릭 이벤트 바인딩
    $(".userList").on("click", ".add-user-id", function() {
        roomUserList.push($(this).text());

        $('.setUser').append(
            '<div class="btn btn-secondary">'+$(this).text()+"</div>");
        $('.userList').empty();
    });

});
window.BeforeUnloadEvent = function () {
    var activeRoomId = $('.nav-item a.active').data('room-id');
    disconnect(activeRoomId);
}
