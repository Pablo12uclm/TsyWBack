package edu.uclm.esi.juegos.http;

import edu.uclm.esi.juegos.entities.Move;
import edu.uclm.esi.juegos.services.Connect4Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/connect4")
public class Connect4Controller {

    @Autowired
    private Connect4Service connect4Service;

    @PostMapping("/start")
    public @ResponseBody String startGame() {
        connect4Service.setBoard(new String[6][7]);
        return "Game started";
    }

    @PostMapping("/move")
    public @ResponseBody String makeMove(@RequestParam int row, @RequestParam int col, @RequestParam String player) throws Exception {
        Move move = new Move(row, col, player);
        return connect4Service.processMove(move);
    }

    @MessageMapping("/move")
    @SendTo("/topic/moves")
    public String processMove(Move move) throws Exception {
        return connect4Service.processMove(move);
    }
}
