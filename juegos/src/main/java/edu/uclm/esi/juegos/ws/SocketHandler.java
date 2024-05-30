package edu.uclm.esi.juegos.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uclm.esi.juegos.entities.Move;
import edu.uclm.esi.juegos.services.Connect4Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SocketHandler extends TextWebSocketHandler {

    @Autowired
    private Connect4Service connect4Service;

    private ObjectMapper objectMapper = new ObjectMapper();
    private CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("Connection established: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Mensaje recibido en el backend: " + payload);

        MyMessage receivedMessage = objectMapper.readValue(payload, MyMessage.class);
        System.out.println("Parsed message: " + receivedMessage);

        switch (receivedMessage.getType()) {
            case "move":
                Move move = objectMapper.readValue(receivedMessage.getContent(), Move.class);
                String response = connect4Service.processMove(move);
                System.out.println("Processing move response: " + response);

                // Asegúrate de que la respuesta tiene un content no nulo
                if (response != null && !response.contains("\"content\"")) {
                    response = response.substring(0, response.length() - 1) + ", \"content\": \"\"}";
                }

                for (WebSocketSession webSocketSession : sessions) {
                    if (webSocketSession.isOpen()) {
                        webSocketSession.sendMessage(new TextMessage(response));
                    }
                }
                break;
            default:
                MyMessage responseMessage = new MyMessage("Mensaje recibido", receivedMessage.getContent());
                String jsonResponse = objectMapper.writeValueAsString(responseMessage);
                session.sendMessage(new TextMessage(jsonResponse));
                break;
        }
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Connection closed: " + session.getId());
    }

    public static class MyMessage {
        private String type;
        private String content;

        public MyMessage() {}

        public MyMessage(String type, String content) {
            this.type = type;
            this.content = content;
        }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }

        @Override
        public String toString() {
            return "MyMessage{" +
                    "type='" + type + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}
