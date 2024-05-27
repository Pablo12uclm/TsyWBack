package edu.uclm.esi.juegos.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uclm.esi.juegos.entities.Move;
import org.springframework.stereotype.Service;

@Service
public class Connect4Service {

    private ObjectMapper objectMapper = new ObjectMapper();
    private String[][] board = new String[6][7];
    private int totalMoves = 0;

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
        if (board[move.getRowNum()][move.getCol()] == null) {
            board[move.getRowNum()][move.getCol()] = move.getPlayer();
            totalMoves++;
            boolean isWinner = checkWinner(board, move.getRowNum(), move.getCol());
            if (isWinner) {
                return objectMapper.writeValueAsString(new MoveResponse("win", move.getPlayer()));
            } else if (checkTie(board, totalMoves)) {
                return objectMapper.writeValueAsString(new MoveResponse("tie", null));
            } else {
                return objectMapper.writeValueAsString(new MoveResponse("continue", null));
            }
        }
        return objectMapper.writeValueAsString(new MoveResponse("invalid", null));
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    class MoveResponse {
        private String status;
        private String player;

        public MoveResponse(String status, String player) {
            this.status = status;
            this.player = player;
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
    }
}
