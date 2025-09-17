package com.project42.sai.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 클라이언트에서 WebSocket 연결 시 사용할 엔드포인트 설정
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("*") // CORS 허용
                .withSockJS(); // fallback 지원
    }

    // 메시지 브로커 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 서버 → 클라이언트 전송 주소 prefix
        registry.enableSimpleBroker("/topic" ,"queue");

        // 클라이언트 → 서버 전송 주소 prefix
        registry.setApplicationDestinationPrefixes("/app");
        
        //개인 라우팅 : prefix : convertAndSendToUser()
        registry.setUserDestinationPrefix("/user"); 

    }
}