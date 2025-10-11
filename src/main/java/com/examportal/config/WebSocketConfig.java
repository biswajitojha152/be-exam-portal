package com.examportal.config;

import com.examportal.security.jwt.JwtUtils;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setHandshakeHandler(new CustomHandshakeHandler(jwtUtils))
//                .setAllowedOriginPatterns("*")
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

}

class CustomHandshakeHandler extends DefaultHandshakeHandler{
    private final JwtUtils jwtUtils;
    public CustomHandshakeHandler(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        Map<String, String> queryParam = getQueryParam(request.getURI().getQuery());
        String token = queryParam.get("token");
        if(token!=null && token.startsWith("Bearer") && jwtUtils.validateJwtToken(token.split(" ")[1])){
            return new StompPrincipal(jwtUtils.getUserNameFromJwtToken(token.split(" ")[1]));
        }
        return null;
    }

    private Map<String, String> getQueryParam(String query){
        if(query == null) return new HashMap<>();
        Map<String, String> queryParams = new HashMap<>();
        for(String item : query.split("&")){
            queryParams.put(item.split("=")[0], item.split("=")[1]);
        }
        return queryParams;
    }
}

class StompPrincipal implements Principal{
    private final String name;

    public StompPrincipal(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}
