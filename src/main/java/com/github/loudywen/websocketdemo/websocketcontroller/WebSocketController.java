package com.github.loudywen.websocketdemo.websocketcontroller;

import com.github.loudywen.websocketdemo.model.Message;
import com.github.loudywen.websocketdemo.model.OutputMessage;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class WebSocketController {

    private SimpMessagingTemplate template;

    public WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public OutputMessage send(final Message message) throws Exception {
        return new OutputMessage(message.getFrom(), message.getText(), Instant.now().toString());
    }

    @PostMapping(value = "/send", produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendByRest(@RequestBody Message message) {
        this.template.convertAndSend("/topic/messages", new OutputMessage(message.getFrom(), message.getText(), Instant.now().toString()));
    }
}
