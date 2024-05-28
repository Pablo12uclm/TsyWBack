package edu.uclm.esi.juegos.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int rowNum; // Renombrado de 'row' a 'rowNum'
    private int col;
    private String player;

    @ManyToOne
    private Game game;

    // Constructor por defecto
    public Move() {}

    // Constructor con parámetros
    public Move(int rowNum, int col, String player) {
        this.rowNum = rowNum;
        this.col = col;
        this.player = player;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
