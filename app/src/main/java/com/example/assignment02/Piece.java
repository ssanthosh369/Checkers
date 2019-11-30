package com.example.assignment02;

class Piece {
    private int player;
    private boolean type;
    private int row;
    private int col;

    Piece(int p, int row, int col) {
        player = p;
        this.row = row;
        this.col = col;
    }


    void setCol(int col) {
        this.col = col;
    }

    void setRow(int row) {
        this.row = row;
    }

    int getCol() {
        return col;
    }

    int getRow() {
        return row;
    }

    int getPlayer() {
        return player;
    }

    boolean isType() {
        return type;
    }

    void toKing() {
        this.type = true;
    }
}