package edu.uclm.esi.juegos.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import edu.uclm.esi.juegos.services.Connect4Service;

@Controller
@RequestMapping("/connect4")
public class Connect4Controller {

    @Autowired
    private Connect4Service connect4Service;

    private String[][] board = new String[6][7];
    private int totalMoves = 0;

    @PostMapping("/start")
    public @ResponseBody String startGame() {
        board = new String[6][7];
        totalMoves = 0;
        return "Game started";
    }

    @PostMapping("/move")
    public @ResponseBody String makeMove(@RequestParam int row, @RequestParam int col, @RequestParam String player) {
        return processMove(new Move(row, col, player));
    }

    @MessageMapping("/move")
    @SendTo("/topic/moves")
    public String processMove(Move move) {
        if (board[move.getRow()][move.getCol()] == null) {
            board[move.getRow()][move.getCol()] = move.getPlayer();
            totalMoves++;
            boolean isWinner = connect4Service.checkWinner(board, move.getRow(), move.getCol());
            if (isWinner) {
                return "Player " + move.getPlayer() + " wins!";
            } else if (connect4Service.checkTie(board, totalMoves)) {
                return "It's a tie!";
            } else {
                return "Move accepted";
            }
        }
        return "Invalid move";
    }

}


class Move {
    private int row;
    private int col;
    private String player;

    public Move() {}

    public Move(int row, int col, String player) {
        this.row = row;
        this.col = col;
        this.player = player;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }
}

