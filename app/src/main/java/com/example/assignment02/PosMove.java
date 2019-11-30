package com.example.assignment02;

import java.util.ArrayList;

class PosMove {
    private ArrayList<Integer> move;
    private ArrayList<Integer> capture;


    PosMove(ArrayList<Integer> m, ArrayList<Integer> c) {
        move = m;
        capture = c;
    }


    boolean checkCapture() {
        return capture.isEmpty();
    }

    boolean checkMove() {
        return move.isEmpty();
    }

    ArrayList<Integer> getCapture() {
        return capture;
    }

    ArrayList<Integer> getMove() {
        return move;
    }
}
