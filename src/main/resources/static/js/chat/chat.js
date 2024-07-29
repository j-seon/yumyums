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
        if(receivedMessageList[0]=='c'){
            $('.chat-room-list').append(`
                                        <li class="nav-item">
                                            <a class="d-flex m-2 py-2 bg-light rounded-pill " data-bs-toggle="pill" data-room-id="${receivedMessageList[3]}" data-chat-member-id="${receivedMessageList[5]}">
                                                <span class="text-dark" style="width: 125px;">
                                                    <div class="inline-block room-name" >${receivedMessageList[1]} </div>
                                                    <div class="inline-block room-member-count" >${receivedMessageList[4]}</div>
                                                    <div class="inline-block" >명</div></span>
                                                <button type="button" class="btn-close del-room"  aria-label="Close"></button>
                                            </a>
                                        </li>
                                      `);
            connect(receivedMessageList[3]);
        }else if((receivedMessageList[0]=='d')){
            $('a[data-room-id="' + receivedMessageList[3] + '"] div.room-member-count').text(receivedMessageList[4]);
        }


    });

    // 연결이 설정된 후에 연결 메시지 전송
//    stompClientToServer.send(`/connect/isAlive/${loginUserId}`, {}, JSON.stringify({ message: "User connected" }));
}, function (error) {
    setConnected(false);
    console.error('STOMP error: ' + error);
});


function setConnected(connected) {

    $("#chatting").html("");
}

// 백드롭 수동 제거 함수
function removeModalBackdrop() {
    const backdrop = document.querySelector('.modal-backdrop');
    if (backdrop) {
        backdrop.parentNode.removeChild(backdrop);
    }
}
//연결
function connect(roomId) {
    if (!stompClientList[roomId]) {
        var socket = new SockJS("/ws-stomp");
        var stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected to room ' + roomId + ': ' + frame);

            // 구독
            stompClient.subscribe('/chat/room/' + roomId, function (chatMessage) {
                showChat(JSON.parse(chatMessage.body));
            });
        });
        stompClientList[roomId] = stompClient;
    }
}

//연결 종료
function disconnect(roomId) {
    if (stompClientList[roomId]) {
        stompClientList[roomId].disconnect();
        setConnected(false);
        console.log("Disconnected from room " + roomId);
        delete stompClientList[roomId];
    }
}

//채팅 전송
function sendChat() {
    var activeRoomId = $('.nav-item a.active').data('room-id');
    if(activeRoomId==null){
        alert('채팅방을 선택해 주세요');
        return;
    }
    var activeChatMemberId = $('.nav-item a.active').data('chat-member-id');
    if ($("#message").val() != "" && stompClientList[activeRoomId]) {
        const messageContent = $("#message").val();
        const chatMessage = {
            chat : {
                id :activeRoomId
            },
            content: messageContent,
            chatMember: {
                id : activeChatMemberId,
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


// 보낸 채팅 보기
 function showChat(chatMessage) {
     var activeRoomId = $('.nav-item a.active').data('room-id');

     if (chatMessage.chatMember.member.memberId == loginUserId) {
         $("#chatting").append(
             "<div class='chat-box'><div class='chatting_own'><tr><td>" + chatMessage.content + "</td></tr></div></div>"
         );
     } else {
         if (activeRoomId == chatMessage.chatMember.chat.id) {
             $("#chatting").append(
                 "<div class='chat-box'><div class='chatting'><tr><td>" + "[" + chatMessage.chatMember.member.memberId + "] " + chatMessage.content + "</td></tr></div></div>"
             );
         }else{
         $('a[data-room-id="' + chatMessage.chatMember.chat.id + '"]').addClass('message-not-read');

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

    //채팅 전송
    $("#send").click(function () { sendChat(); });

    //채팅방 선택
    $('.nav').on('click', '.nav-item .del-room', function() {
        const roomId = $(this).parent().data('room-id');
        $(this).parent().remove();
        $.ajax({
             url: '/chat',
             data: { chatId: roomId, memberId:loginUserId },
             method: "DELETE",
             success: (data) => {
                disconnect(roomId);
             }, error: (xhr, status, error) => {
                  console.error('Error: ' + error);
                  console.error('Status: ' + status);
                  console.error(xhr);
             }
         });
    });

    //채팅방 추가
    $('.add-room').on('click', function(e) {
        let roomName = $("#memo").val();
        roomUserList.push(loginUserId);
        $.ajax({
            url: "/chat" ,
            data:{"memberIdList":roomUserList,"roomName": roomName},
            method: "post",
            success: function(data, status, xhr) {
                console.log(data);
                roomUserList=[];
                initModal();
                  // Bootstrap 5 모달 닫기
                let modalElement = document.getElementById('staticBackdrop');
                let modalInstance = bootstrap.Modal.getInstance(modalElement);
                if (modalInstance) {
                    modalInstance.hide();
                }

                // 백드롭 수동 제거
                removeModalBackdrop();
            },
            error: function(xhr, status, error) {
                console.error("Error occurred:", error);
                alert('채팅창 추가 실패')
            }
        });
    });

    //채팅방 활성화
    $('.nav').on('click', '.nav-item a', function() {
          $("#chatting").empty();
          var roomId = $(this).data('room-id');
          $('.nav-item a').removeClass('message-not-read');
          $('.nav-item a').removeClass('active');
          console.log(roomId);
          $(this).addClass('active');
          $('#room-member').empty();
          // 채팅 불러오기
          $.ajax({
               url: '/chat/findChatMessage',
               data: {roomId : roomId },
               success: (data) => {
                   data.chatMessageList.forEach((message) => {
                      showChat(message);
                   });
                   data.chatMemberList.forEach((member) => {
                        $('#room-member').append(`
                             <li class=" chat-member-id"
                                 style="cursor:pointer;">${member.member.memberId} 님</li>
                        `);
                        if(member.member.memberId==loginUserId){
                            $("#room-info").text(member.memberSavedRoomName)
                        }
                  });
               }, error: (xhr, status, error) => {
                    console.error('Error: ' + error);
                    console.error('Status: ' + status);
                    console.error(xhr);
               }
          });
    });

    //채팅 추가
    $(".userList").on("click", ".add-user-id", function() {
        roomUserList.push($(this).text());
        $('.setUser').append(
            '<div class="btn btn-secondary">'+$(this).text()+"</div>");
        $('.userList').empty();
    });
});
//끝나기전 연결 제거
window.BeforeUnloadEvent = function () {
    var activeRoomId = $('.nav-item a.active').data('room-id');
    disconnect(activeRoomId);
}
