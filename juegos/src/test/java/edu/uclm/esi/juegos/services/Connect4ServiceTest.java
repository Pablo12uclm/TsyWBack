package edu.uclm.esi.juegos.services;

import edu.uclm.esi.juegos.entities.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Connect4ServiceTest {

    private Connect4Service connect4Service;
    private String[][] board;

    @BeforeEach
    void setUp() {
        connect4Service = new Connect4Service();
        board = new String[6][7];  // Inicializar el tablero de 6 filas x 7 columnas
    }

    @Test
    void testCheckWinnerHorizontal() {
        board[0][0] = "X";
        board[0][1] = "X";
        board[0][2] = "X";
        board[0][3] = "X";
        assertTrue(connect4Service.checkWinner(board, 0, 3));
    }

    @Test
    void testCheckWinnerVertical() {
        board[0][0] = "X";
        board[1][0] = "X";
        board[2][0] = "X";
        board[3][0] = "X";
        assertTrue(connect4Service.checkWinner(board, 3, 0));
    }

    @Test
    void testCheckWinnerDiagonalRight() {
        board[0][0] = "X";
        board[1][1] = "X";
        board[2][2] = "X";
        board[3][3] = "X";
        assertTrue(connect4Service.checkWinner(board, 3, 3));
    }

    @Test
    void testCheckWinnerDiagonalLeft() {
        board[3][0] = "X";
        board[2][1] = "X";
        board[1][2] = "X";
        board[0][3] = "X";
        assertTrue(connect4Service.checkWinner(board, 0, 3));
    }

    @Test
    void testCheckTie() {
        // Llenar el tablero sin ningún ganador
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                board[row][col] = (col % 2 == 0) ? "X" : "O";
            }
        }
        assertTrue(connect4Service.checkTie(board, 42));
    }

    @Test
    void testProcessMoveWin() throws Exception {
        Move move = new Move();
        move.setRowNum(0);
        move.setCol(0);
        move.setPlayer("X");

        board[0][1] = "X";
        board[0][2] = "X";
        board[0][3] = "X";

        String result = connect4Service.processMove(move);
        assertTrue(result.contains("\"status\":\"win\""));
    }

    @Test
    void testProcessMoveTie() throws Exception {
        Move move = new Move();
        move.setRowNum(5);
        move.setCol(6);
        move.setPlayer("X");

        // Llenar el tablero menos una celda
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

    @Test
    void testProcessMoveContinue() throws Exception {
        Move move = new Move();
        move.setRowNum(0);
        move.setCol(0);
        move.setPlayer("X");

        String result = connect4Service.processMove(move);
        assertTrue(result.contains("\"status\":\"continue\""));
    }

    @Test
    void testProcessMoveInvalid() throws Exception {
        Move move = new Move();
        move.setRowNum(0);
        move.setCol(0);
        move.setPlayer("X");

        board[0][0] = "O";

        String result = connect4Service.processMove(move);
        assertTrue(result.contains("\"status\":\"invalid\""));
    }
}
