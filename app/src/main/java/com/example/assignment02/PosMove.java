package com.example.assignment02;

import java.util.ArrayList;

public class PosMove {
    private ArrayList<Integer> move;
    private ArrayList<Integer> capture;


    public PosMove(ArrayList<Integer> m,ArrayList<Integer> c) {
        move = m;
        capture = c;
    }


    public boolean checkCapture() {
        if(capture.isEmpty())
            return true;
        else
            return false;
    }

    public boolean checkMove() {
        if(move.isEmpty())
            return true;
        else
            return false;
    }

    public ArrayList<Integer> getCapture() {
        return capture;
    }

    public ArrayList<Integer> getMove() {
        return move;
    }
}
