package edu.uclm.esi.juegos.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class SocketHandler extends TextWebSocketHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Mensaje recibido en el backend: " + payload);

        // Procesar el mensaje recibido (JSON)
        MyMessage receivedMessage = objectMapper.readValue(payload, MyMessage.class);

        // Responder con un mensaje JSON
        MyMessage responseMessage = new MyMessage("Mensaje recibido", receivedMessage.getContent());
        String jsonResponse = objectMapper.writeValueAsString(responseMessage);

        System.out.println("Enviando mensaje desde el backend: " + jsonResponse);
        session.sendMessage(new TextMessage(jsonResponse));
    }
}

class MyMessage {
    private String type;
    private String content;

    // Constructor, getters y setters
    public MyMessage() {}

    public MyMessage(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
