package com.yum.yumyums.controller;

import com.yum.yumyums.dto.TemplateData;
import com.yum.yumyums.dto.chat.ChatMemberDTO;
import com.yum.yumyums.dto.chat.ChatMessageDTO;
import com.yum.yumyums.dto.user.MemberDTO;
import com.yum.yumyums.entity.chat.Chat;
import com.yum.yumyums.entity.chat.ChatMember;
import com.yum.yumyums.entity.user.Member;
import com.yum.yumyums.service.chat.ChatMemberService;
import com.yum.yumyums.service.chat.ChatMessageService;
import com.yum.yumyums.service.chat.ChatService;
import com.yum.yumyums.service.user.MemberService;

import com.yum.yumyums.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
public class ChatController {
    private final ChatMemberService chatMemberService;
    private final ChatMessageService chatMessageService;
    private final ChatService chatService;
    private final MemberService memberService;
    private final SimpMessagingTemplate messagingTemplate;

    //페이지 호출
    @GetMapping("")
    public String showChatPage(Model model, TemplateData templateData,HttpSession session) {

            templateData.setViewPath("chat/chat");
            model.addAttribute("templateData", templateData);
            return "template";

    }
    //페이지 호출
    @GetMapping("/test")
    public String test(Model model, TemplateData templateData,HttpSession session) {
        SessionUtil sessionUtil =new SessionUtil();
        MemberDTO memberDTO = new MemberDTO();

        templateData.setViewPath("chat/test");
        model.addAttribute("templateData", templateData);
        return "template";

    }

    //채팅방 참가
    //추후 추가
    @PostMapping("/join")
    @ResponseBody
    public String joinChat(@RequestParam("memberIdList") List<String> memberIdList, HttpSession session) {
        try {
            for (String memberId : memberIdList ) {
                System.out.println(memberId);
            }

            return "success";
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return "fail";
        }
    }

    //채팅방 생성
    @PostMapping
    @ResponseBody
    public String createChat(@RequestParam("memberIdList") List<String> memberIdList, @RequestParam("roomName") String roomName, HttpSession session) {
        try {
            MemberDTO memberDto = new MemberDTO();
            Chat chat = new Chat();
            ChatMember chatMember = new ChatMember();
            Member member = new Member();

            chat.setName(roomName);
            //채팅방 생성
            chatService.save(chat);

            // 멤버 추가
            for (String memberId : memberIdList) {
                memberDto = memberService.findById(memberId);

                if (memberDto != null) {
                    member = Member.dtoToEntity(memberDto);
                    chatMember = new ChatMember();
                    chatMember.setMember(member);
                    chatMember.setChat(chat);
                    chatMember.setMemberSavedRoomName(roomName);
                    chatMemberService.saveChatMember(chatMember);
                    System.out.println("memberId"+memberId);
                }
                //원격으로 체팅방 생성
                // 0: c(생성), d(삭제) 1:roomName 2:memberId 3:chatId 4:member count 5:chat Member Id
                messagingTemplate.convertAndSend("/chat/alive/"+memberId , "c/"+roomName+"/"+memberId+"/"+chat.getId()+"/"+memberIdList.size()+"/"+chatMember.getId());
            }
            //생성한 쳇팅 아이디
            return  ""+chat.getId();
        }catch (Exception e) {
            System.out.println("error : "+e.getMessage());
            return "fail";
        }
    }

    //채팅 삭제
    @DeleteMapping
    @ResponseBody
    public String delRoom(@RequestParam("chatId") String strChatId,@RequestParam("memberId") String memberId, HttpSession session) {
        int chatId = Integer.parseInt(strChatId);
        try {
            System.out.println("chatId : "+chatId);
            System.out.println("memberId : "+memberId);
            //해당 멤버 삭제
            chatMemberService.deleteByChatIdAndMemberId(chatId,memberId);
            //적용 시킬 멤버 불러옴
            List<ChatMemberDTO> ChatMemberList = chatMemberService.findMemberIdByChatId(chatId);
            //해당 정보 송신
            for(ChatMemberDTO ChatMember : ChatMemberList ){
                messagingTemplate.convertAndSend("/chat/alive/"+ChatMember.getMember().getMemberId() , "d/"+ChatMember.getMemberSavedRoomName()+"/"+memberId+"/"+ChatMember.getChat().getId()+"/"+ChatMemberList.size());
            }
            return "success";
        }catch (Exception e) {
            return "fail";
        }
    }

    //친구 찾기
    @GetMapping("/findFriend")
    @ResponseBody
    public List<String> findFriend(@RequestParam("keyword") String keyword, HttpSession session) {
        List<String> returnList = new ArrayList<>();
        List<MemberDTO> memberDtoList = new ArrayList<>();

        try {
            memberDtoList = memberService.findByIdStartsWith(keyword);
            for (MemberDTO  findMember : memberDtoList) {
                returnList.add(findMember.getMemberId());
            }
            System.out.println(returnList);
            return returnList;
        }catch (Exception e) {
            System.out.println("error : "+e.getMessage());
            return null;
        }
    }

    //채팅 찾기
    @GetMapping("/findChatMessage")
    @ResponseBody
    public HashMap<String, Object> findChatMessage(@RequestParam("roomId") String strRoomId, HttpSession session) {
        int roomId = Integer.parseInt(strRoomId);
        List<ChatMessageDTO> chatMessageList = new ArrayList<>();
        List<ChatMemberDTO> chatMemberList = new ArrayList<>();

        HashMap<String, Object> returnData= new HashMap<String, Object>();

        try {
            chatMessageList = chatMessageService.findByChatId(roomId);
            chatMemberList =chatMemberService.findMemberIdByChatId(roomId);
            returnData.put("chatMessageList", chatMessageList);
            returnData.put("chatMemberList", chatMemberList);
            return returnData;
        }catch (Exception e) {
            System.out.println("error : "+e.getMessage());
            return null;
        }
    }


    //웹소켓
    //채팅 저장 및 보내기
    @MessageMapping("/{roomId}") //여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    @SendTo("/chat/room/{roomId}")   //구독하고 있는 장소로 메시지 전송 (목적지)  -> WebSocketConfig Broker 에서 적용한건 앞에 붙어줘야됨
    public ChatMessageDTO chat(@DestinationVariable Long roomId, ChatMessageDTO chatMessageDTO) {
        System.out.println("chatMessageDTO : "+chatMessageDTO);

        MemberDTO memberDTO = memberService.findById(chatMessageDTO.getMember().getMemberId());
        chatMessageDTO.setMember(memberDTO);
        chatMessageService.save(chatMessageDTO);
        return chatMessageDTO;
    }

    //채팅방 원격 생성
    @MessageMapping("/isAlive/{userId}")
    @SendTo("/chat/alive/{userId}")
    public String isConnect(@DestinationVariable String userId, String message) {
        System.out.println("isConnect userId: " + userId);

        // 클라이언트로 메시지 전송
        return "User " + userId + " is connected";
    }
}
