package com.ian.blokus;
import com.ian.blokus.Color;
import java.lang.Math;

/**
 * 
 * @author ian
 * Represents one square on a Blokus board
 */
public class Square {
    
    private Color fillColor;
    private int x;
    private int y;
    private Square[] cornerNeighbors;
    private Square[] sideNeighbors;
    
    public Square(int x, int y) {
        this.fillColor = Color.NONE;
        this.x = x;
        this.y = y;
        this.cornerNeighbors = new Square[4];
        this.sideNeighbors = new Square[4];
    }
    
    /**
     * 
     * @return true if this square is filled by a piece, false else
     */
    public boolean filled() {
        if (this.fillColor == Color.NONE) {
            return false;
        }
        
        return true;
    }
    
    /**
     * 
     * @param other square to compare distance to
     * @param xdist distance in the x direction
     * @param ydist distance in the y direction
     * @return true if this square is within both xdist and ydist of the 'other' square, false else
     */
    public boolean within(Square other, int xdist, int ydist) {
        boolean withinX = Math.abs(this.x - other.getX()) <= xdist ;
        boolean withinY = Math.abs(this.y - other.getY()) <= ydist ;
        return withinX && withinY;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Square[] getCornerNeighbors() {
        return cornerNeighbors;
    }

    public void setCornerNeighbors(Square[] cornerNeighbors) {
        for (int i = 0; i < this.cornerNeighbors.length; i++) {
            this.cornerNeighbors[i] = cornerNeighbors[i]; 
        }
    }

    public Square[] getSideNeighbors() {
        return sideNeighbors;
    }

    public void setSideNeighbors(Square[] sideNeighbors) {
        for (int i = 0; i < this.sideNeighbors.length; i++) {
            this.sideNeighbors[i] = sideNeighbors[i]; 
        }
    }
    
    
    
    
}
