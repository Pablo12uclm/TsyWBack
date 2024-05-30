package edu.uclm.esi.juegos.services;

import edu.uclm.esi.juegos.entities.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Connect4ServiceTest {

    private Connect4Service connect4Service;

    @BeforeEach
    void setUp() {
        connect4Service = new Connect4Service();
    }

    @Test
    void testCheckTie() {
        String[][] board = new String[6][7];
        int totalMoves = 42;
        assertTrue(connect4Service.checkTie(board, totalMoves));
    }

    @Test
    void testProcessMoveInvalid() throws Exception {
        Move move = new Move();
        move.setRowNum(0);
        move.setCol(0);
        move.setPlayer("X");

        String[][] board = connect4Service.getBoard();
        board[0][0] = "O";

        String result = connect4Service.processMove(move);
        assertTrue(result.contains("\"status\":\"invalid\""));
    }

    /* @Test
   void testProcessMoveTie() throws Exception {
        Move move = new Move();
        move.setRowNum(5);
        move.setCol(6);
        move.setPlayer("X");

        String[][] board = connect4Service.getBoard();
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (!(row == 5 && col == 6)) {
                    board[row][col] = (col % 2 == 0) ? "X" : "O";
                }
            }
        }

        String result = connect4Service.processMove(move);
        assertTrue(result.contains("\"status\":\"tie\""));
    }
    */

    @Test
    void testProcessMoveWin() throws Exception {
        Move move = new Move();
        move.setRowNum(0);
        move.setCol(0);
        move.setPlayer("X");

        String[][] board = connect4Service.getBoard();
        board[0][1] = "X";
        board[0][2] = "X";
        board[0][3] = "X";

        String result = connect4Service.processMove(move);
        assertTrue(result.contains("\"status\":\"win\""));
    }

    @Test
    void testProcessMoveContinue() throws Exception {
        Move move = new Move();
        move.setRowNum(0);
        move.setCol(0);
        move.setPlayer("X");

        String result = connect4Service.processMove(move);
        assertTrue(result.contains("\"status\":\"continue\""));
    }

    // Additional tests for other win conditions...
}
