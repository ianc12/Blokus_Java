package com.ian.blokus;

public class Move {
    public Piece piece;
    public int x;
    public int y;
    
    public Move(Piece piece, int x, int y) {
        this.piece = piece;
        this.x = x;
        this.y = y;
    }
}

