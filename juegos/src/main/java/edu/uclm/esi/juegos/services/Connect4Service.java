package edu.uclm.esi.juegos.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uclm.esi.juegos.entities.Game;
import edu.uclm.esi.juegos.entities.Move;
import edu.uclm.esi.juegos.entities.User;
import edu.uclm.esi.juegos.dao.GameDAO;
import edu.uclm.esi.juegos.dao.MoveDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class Connect4Service {

    @Autowired
    private GameDAO gameDAO;

    @Autowired
    private MoveDAO moveDAO;

    private ObjectMapper objectMapper = new ObjectMapper();
    private String[][] board = new String[6][7];
    private int totalMoves = 0;
    private User waitingUser = null;

    public synchronized Game joinGame(User user) throws Exception {
        if (user == null || user.getUsername() == null) {
            throw new Exception("User object is null or username is null");
        }
        System.out.println("joinGame called with user: " + user.getUsername());

        if (waitingUser == null) {
            waitingUser = user;
            throw new Exception("Waiting for another player to join.");
        } else {
            Game game = new Game();
            game.setPlayer1(waitingUser.getUsername());
            game.setPlayer2(user.getUsername());
            game.setFinished(false);
            gameDAO.save(game);
            waitingUser = null;
            return game;
        }
    }

    public boolean checkWinner(String[][] board, int lastRow, int lastCol) {
        String player = board[lastRow][lastCol];
        return (checkDirection(board, player, lastRow, lastCol, 1, 0) ||  // Horizontal
                checkDirection(board, player, lastRow, lastCol, 0, 1) ||  // Vertical
                checkDirection(board, player, lastRow, lastCol, 1, 1) ||  // Diagonal \
                checkDirection(board, player, lastRow, lastCol, 1, -1));  // Diagonal /
    }

    private boolean checkDirection(String[][] board, String player, int row, int col, int dRow, int dCol) {
        int count = 0;
        for (int i = -3; i <= 3; i++) {
            int newRow = row + i * dRow;
            int newCol = col + i * dCol;
            if (newRow >= 0 && newRow < board.length && newCol >= 0 && newCol < board[0].length &&
                board[newRow][newCol] != null && board[newRow][newCol].equals(player)) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    public boolean checkTie(String[][] board, int totalMoves) {
        return totalMoves == 42;  // Assuming a 7x6 board, tie if all cells are filled
    }

    public String processMove(Move move) throws Exception {
        System.out.println("Processing move: " + move);
        int row = move.getRowNum();
        int col = move.getCol();
        String player = move.getPlayer();

        System.out.println("Board state before move:");
        for (String[] rowState : board) {
            System.out.println(Arrays.toString(rowState));
        }

        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length || board[row][col] != null) {
            System.out.println("Invalid move detected: (" + row + ", " + col + ")");
            return objectMapper.writeValueAsString(new MoveResponse("invalid", null, null));
        }

        board[row][col] = player;
        totalMoves++;

        System.out.println("Board state after move:");
        for (String[] rowState : board) {
            System.out.println(Arrays.toString(rowState));
        }

        boolean isWinner = checkWinner(board, row, col);
        if (isWinner) {
            return objectMapper.writeValueAsString(new MoveResponse("win", player, null));
        } else if (checkTie(board, totalMoves)) {
            return objectMapper.writeValueAsString(new MoveResponse("tie", null, null));
        } else {
            // Aquí se asegura que la respuesta "continue" siempre tiene un campo content no nulo.
            String content = objectMapper.writeValueAsString(move);
            return objectMapper.writeValueAsString(new MoveResponse("continue", player, content));
        }
    }


    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    static class MoveResponse {
        private String status;
        private String player;
        private String content;  // Añadir campo content

        public MoveResponse(String status, String player, String content) {
            this.status = status;
            this.player = player;
            this.content = content;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPlayer() {
            return player;
        }

        public void setPlayer(String player) {
            this.player = player;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        @Override
        public String toString() {
            return "MoveResponse{" +
                    "status='" + status + '\'' +
                    ", player='" + player + '\'' +
                    ", content='" + content + '\'' +
                    '}';
        }
    }
}
