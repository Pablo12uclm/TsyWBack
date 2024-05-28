package edu.uclm.esi.juegos.http;

import edu.uclm.esi.juegos.entities.Move;
import edu.uclm.esi.juegos.entities.User;
import edu.uclm.esi.juegos.entities.Game;
import edu.uclm.esi.juegos.services.Connect4Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/connect4")
public class Connect4Controller {

    @Autowired
    private Connect4Service connect4Service;

    @PostMapping("/join")
    public ResponseEntity<?> joinGame(@RequestBody User user) {
        try {
            Game game = connect4Service.joinGame(user);
            return new ResponseEntity<>(game, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/start")
    public ResponseEntity<?> startGame() {
        connect4Service.setBoard(new String[6][7]);
        return new ResponseEntity<>(new ApiResponse("Game started"), HttpStatus.OK);
    }

    @PostMapping("/move")
    public ResponseEntity<?> makeMove(@RequestParam int row, @RequestParam int col, @RequestParam String player) throws Exception {
        Move move = new Move(row, col, player);
        return new ResponseEntity<>(connect4Service.processMove(move), HttpStatus.OK);
    }

    @MessageMapping("/move")
    @SendTo("/topic/moves")
    public String processMove(Move move) throws Exception {
        return connect4Service.processMove(move);
    }

    static class ApiResponse {
        private String message;

        public ApiResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
