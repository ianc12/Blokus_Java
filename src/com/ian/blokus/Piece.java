package com.ian.blokus;

import com.ian.blokus.Point;
import com.ian.blokus.Color;
import java.util.ArrayList;

public class Piece {
    
    private ArrayList<Point> points;
    private ArrayList<Piece> permutations;
    private int width;
    private int height;
    private Color color;
    
    
    public Piece(ArrayList<Point> points, int width, int height, Color color) {
        this.permutations = new ArrayList<Piece>();
        this.points = points;
        this.width = width;
        this.height = height;
        this.color = color;
    }
    
    
    /**
     * adds addpiece to list if the list does not contain a piece in the same orientation
     * 
     * @param list list of Pieces to compare addpiece to
     * @param addpiece Piece to compare to list
     * @return true if addpiece was not in list, false else
     */
    public static boolean addIfNotContains(ArrayList<Piece> list, Piece addpiece) {
        for (Piece listPiece : list) {
            boolean isSame = true;
            
            for (Point p : addpiece.getPoints()) {
                if (!Point.listContians(listPiece.getPoints(), p)) {
                    isSame = false;
                    break;
                }
            }
            
            if (isSame) {
                return false;
            }
        }
        
        list.add(addpiece);
        return true;
    
    }
    
    
    
    /**
     * 
     * @return a copy of the piece called on without permutations
     */
    public Piece duplicate() {
        ArrayList<Point> newpoints = new ArrayList<Point>();
        for (Point p : this.points) {
            Point newp = new Point(p);
            newpoints.add(newp);
        }
    
        return new Piece(newpoints, this.width, this.height, this.color);
    }


    public int size() {
        return this.points.size();
    }
    
    
    public void rotateClockwise(int degrees) {
       if (degrees == 90) {
           // (x,y) -> (y,-x)
           // switch width and height
           int temph = this.height;
           this.height = this.width;
           this.width = temph;
           
           ArrayList<Point> new_points = new ArrayList<Point>();  
           for (Point p : this.points) {
               new_points.add(new Point(p.y,-p.x + (this.height - 1)));
           }
               
           this.points = new_points;
       }
       
       else if (degrees == 180) {
           // (x,y) -> (-x,-y)
           ArrayList<Point> new_points = new ArrayList<Point>();  
           for (Point p : this.points) {
               new_points.add(new Point(-p.x + (this.width - 1) , -p.y + (this.height - 1)));
           }
               
           this.points = new_points;
       }
       
       else if (degrees == 270) {
           // (x,y) -> (-y,x)
           // switch width and height
           int temph = this.height;
           this.height = this.width;
           this.width = temph;
           ArrayList<Point> new_points = new ArrayList<Point>();  
           for (Point p : this.points) {
               new_points.add(new Point(-p.y + (this.width - 1), p.x));
           }
               
           this.points = new_points;
       }
    
       else {
           System.out.println("Invalid rotation");
       }
    }
    
    
    public void rotateCounterclockwise(int degrees) {
        if (degrees == 90) {
            this.rotateClockwise(270);
        }
        else if (degrees == 180) {
            this.rotateClockwise(180);
        }
        else if (degrees == 270) {
            this.rotateClockwise(90);
        }
        else {
            System.out.println("Invalid Rotation");
        }
    }
    
    
    public void reflectHoriz() {
        // (x,y) -> (-x,y)
        ArrayList<Point> new_points = new ArrayList<Point>();  
        for (Point p : this.points) {
            new_points.add(new Point(-p.x + (this.width - 1), p.y));
        }
            
        this.points = new_points;
    }
    
    
    public void reflectVert() {
        // (x,y) -> (x,-y)
        ArrayList<Point> new_points = new ArrayList<Point>();  
        for (Point p : this.points) {
            new_points.add(new Point(p.x, -p.y + (this.height - 1)));
        }
            
        this.points = new_points;
    }
    
    
    public ArrayList<Piece> permute() {
        ArrayList<Piece> perms = new ArrayList<Piece>();
        
        // try all rotations
        for (int i = 0; i < 4; i++) {
            Piece.addIfNotContains(perms, this.duplicate());
            this.rotateClockwise(90);
        }
        // all rotations after horiz reflect
        this.reflectHoriz();
        for (int i = 0; i < 4; i++) {
            Piece.addIfNotContains(perms, this.duplicate());
            this.rotateClockwise(90);
        }
        
        // all rotations only reflected vert
        this.reflectHoriz();
        this.reflectVert();
        for (int i = 0; i < 4; i++) {
            Piece.addIfNotContains(perms, this.duplicate());
            this.rotateClockwise(90);
        }
        
        // all rotations reflected horiz and vert
        this.reflectHoriz();
        for (int i = 0; i < 4; i++) {
            Piece.addIfNotContains(perms, this.duplicate());
            this.rotateClockwise(90);
        }
        // return to original position
        this.reflectHoriz();
        this.reflectVert();
        
        return perms;
    }
    
    
    public ArrayList<Point> getPoints() {
        return points;
    }


    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }


    public ArrayList<Piece> getPermutations() {
        return permutations;
    }


    public void setPermutations(ArrayList<Piece> permutations) {
        this.permutations = permutations;
    }


    public int getWidth() {
        return width;
    }


    public void setWidth(int width) {
        this.width = width;
    }


    public int getHeight() {
        return height;
    }


    public void setHeight(int height) {
        this.height = height;
    }


    public Color getColor() {
        return color;
    }


    public void setColor(Color color) {
        this.color = color;
    }
    
    
    
    
    
    
    
    
}
