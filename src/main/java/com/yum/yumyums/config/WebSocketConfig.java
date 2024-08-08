package com.yum.yumyums.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 클라이언트에서 보내는 메시지의 접두사와, 브로커에서 관리하는 엔드포인트 설정
        registry.setApplicationDestinationPrefixes("/send", "/connect");
        registry.enableSimpleBroker( "/chat/room", "/chat/alive", "/topic");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // STOMP 엔드포인트 등록, SockJS를 사용하여 WebSocket 지원하지 않는 브라우저에서도 사용 가능
        registry.addEndpoint("/ws-stomp", "/isConnect").withSockJS();
    }
}