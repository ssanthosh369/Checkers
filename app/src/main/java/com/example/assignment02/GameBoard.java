package com.example.assignment02;

import java.util.ArrayList;

class GameBoard {
    private int[][] board;
    private ArrayList<Piece> pieces;

    GameBoard() {
        board = new int[8][8];
        pieces = new ArrayList<>();
        initialize();
    }

    private void initialize() {
        // pieces.clear();

        int size = 8;
        for (int row = 0; row < size; row++) {
            int a = 2;
            if (row < 3)
                a = 1;
            for (int col = 0; col < size; col++) {
                if ((row < 3 || row > 4) && (row + col) % 2 != 0) {
                    pieces.add(new Piece(a, row, col));
                    board[row][col] = 1;
                } else {
                    board[row][col] = 0;
                }

            }
        }
    }

    int getBoard(int row, int col) {

        return board[row][col];
    }

    Piece getPiece(int row, int col) {
        for (Piece p : pieces) {
            if (p.getCol() == col && p.getRow() == row) {
                return p;
            }
        }
        return null;
    }

    void setPiece(int row, int col, int newRow, int newCol) {
        for (Piece p : pieces) {
            if (p.getCol() == col && p.getRow() == row) {
                p.setCol(newCol);
                p.setRow(newRow);
                board[row][col] = 0;
                board[newRow][newCol] = 1;
                if (p.getPlayer() == 1 && newRow == 7)
                    p.toKing();
                else if (p.getPlayer() == 2 && newRow == 0)
                    p.toKing();
            }
        }
    }

    void removePiece(int row, int col) {
        int index = -1;
        for (int i = 0; i < pieces.size(); i++) {
            if (pieces.get(i).getCol() == col && pieces.get(i).getRow() == row) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            pieces.remove(index);
            board[row][col] = 0;
        }
    }

    int count(int player) {
        int num = 0;

        for (Piece p : pieces) {
            if (p.getPlayer() == player)
                num++;
        }

        return num;
    }

    ArrayList<Piece> getList() {
        return pieces;
    }
}
