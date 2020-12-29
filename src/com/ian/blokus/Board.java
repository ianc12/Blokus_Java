package com.ian.blokus;

import com.ian.blokus.Square;
import java.util.ArrayList;

public class Board {

    private int size;
    private Square[][] board;
    
    
    
    public Board(int size) {
        this.size = size;
        this.board = new Square[size][size];
        
        // create the squares
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                this.board[x][y] = new Square(x,y);
            }
        }
        
        // fill the corner and side neighbors in
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Square square = this.getSqaure(x, y);
                Square[] corners = new Square[4];
                int cornersIdx = 0;
                Square[] sides = new Square[4];
                int sidesIdx = 0;
                
                for (int dX = -1; dX <= 1; dX++) {  
                    for (int dY = -1; dY <= 1; dY++) {
                        if (dX != 0 || dY != 0) {
                            Square s = this.getSqaure(x + dX, y + dY);
                            if (s != null) {
                                if (dX == dY || dX == -dY) {
                                    corners[cornersIdx] = s;
                                    cornersIdx++;
                                } 
                                else {
                                    sides[sidesIdx] = s;
                                    sidesIdx++;
                                }
                            }
                        }
                    }
                }
                square.setCornerNeigbors(corners);
                square.setSideNeighbors(sides);
            }
        }  
    }
    
    
    
    public Square getSqaure(int x, int y) {
        
        if (x < 0 || x >= this.size) return null;
        if (y < 0 || y >= this.size) return null;
        
        return this.board[x][y];
    }
}
