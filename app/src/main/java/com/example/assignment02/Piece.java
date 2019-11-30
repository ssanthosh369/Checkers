package com.example.assignment02;

public class Piece {
    private int player;
    private boolean type;
    private int row;
    private int col;

    public Piece() {

    }

    public Piece(int p) {
        player = p;
        type = false;
    }

    public Piece(int p, int row, int col) {
        player = p;
        this.row = row;
        this.col = col;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public int getPlayer() {
        return player;
    }

    public boolean isType() {
        return type;
    }

    public void toKing() {
        setType(true);
    }
}