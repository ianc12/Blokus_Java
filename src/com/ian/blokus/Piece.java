package com.ian.blokus;

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
     * @return smallest dimension of this piece
     */
    public int minDimension() {
        if (this.width < this.height) {
            return this.width;
        }
        return this.height;
    }
    
    
    /**
     * 
     * @return a copy of the piece without permutations
     */
    public Piece duplicate() {
        ArrayList<Point> newpoints = new ArrayList<Point>();
        
        for (Point p : this.points) {
            Point newp = new Point(p);
            newpoints.add(newp);
        }
    
        return new Piece(newpoints, this.width, this.height, this.color);
    }


    /**
     * @return the number of points in this piece
     */
    public int size() {
        return this.points.size();
    }
    
    
    /**
     * Applies clockwise rotation of 90, 180, or 270 degrees to this pieces points
     * @param degrees degrees to rotate
     */
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
    
    
    /**
     * Applies counterclockwise rotation of 90, 180, or 270 degrees to this pieces points
     * @param degrees degrees to rotate
     */
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
    
    
    /**
     * Applies vertical reflection to this pieces points
     */
    public void reflectHoriz() {
        // (x,y) -> (-x,y)
        ArrayList<Point> new_points = new ArrayList<Point>();  
        for (Point p : this.points) {
            new_points.add(new Point(-p.x + (this.width - 1), p.y));
        }
            
        this.points = new_points;
    }
    
    
    /**
     * Applies vertical reflection to this pieces points
     */
    public void reflectVert() {
        // (x,y) -> (x,-y)
        ArrayList<Point> new_points = new ArrayList<Point>();  
        for (Point p : this.points) {
            new_points.add(new Point(p.x, -p.y + (this.height - 1)));
        }
            
        this.points = new_points;
    }
    
    
    /**
     * 
     * @return An ArrayList of pieces in all unique orientations based off of this piece
     */
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
    
    
    @Override
    public String toString() {
        String s = String.format("color: %s\n__________\n", this.color.toString());
        for (int y = this.height-1; y >= 0; y--) {
            for (int x = 0; x < this.width; x++) {
                if (Point.listContians(this.points,new Point(x,y))) {
                    s += "x";
                }   
                else {
                    s+= " ";
                }
            }
            s += "\n";
        }
        s += "__________\n";
        return s;
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
    
    
    /**
     * 
     * @param color Color of the pieces to create
     * @return An ArrayList of all starting Blokus pieces with their permutations set
     */
    public static ArrayList<Piece> createPieces(Color color) {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        
        int w;
        int h;
        Piece p;
        
        // 1
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(0,0));
        w = 1;
        h = 1;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        //2
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(1,0));
        w = 2;
        h = 1;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        //I3
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(1,0));
        points.add(new Point(2,0));
        w = 3;
        h = 1;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        //I4
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(1,0));
        points.add(new Point(2,0));
        points.add(new Point(3,0));
        w = 4;
        h = 1;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        //I5
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(1,0));
        points.add(new Point(2,0));
        points.add(new Point(3,0));
        points.add(new Point(4,0));
        w = 5;
        h = 1;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        //L4
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(0,1));
        points.add(new Point(1,0));
        points.add(new Point(2,0));
        w = 3;
        h = 2;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        //L5
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(0,1));
        points.add(new Point(1,0));
        points.add(new Point(2,0));
        points.add(new Point(3,0));
        w = 4;
        h = 2;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        //Y
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(1,1));
        points.add(new Point(1,0));
        points.add(new Point(2,0));
        points.add(new Point(3,0));
        w = 4;
        h = 2;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        // N
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(1,1));
        points.add(new Point(1,0));
        points.add(new Point(2,1));
        points.add(new Point(3,1));
        w = 4;
        h = 2;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        // Z4
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(1,1));
        points.add(new Point(1,0));
        points.add(new Point(2,1));
        w = 3;
        h = 2;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        // Z5
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(1,1));
        points.add(new Point(1,0));
        points.add(new Point(1,2));
        points.add(new Point(2,2));
        w = 3;
        h = 3;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        // Square, O
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(1,0));
        points.add(new Point(1,1));
        points.add(new Point(0,1));
        w = 2;
        h = 2;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        // +, X
        points = new ArrayList<Point>();
        points.add(new Point(0,1));
        points.add(new Point(1,1));
        points.add(new Point(2,1));
        points.add(new Point(1,2));
        points.add(new Point(1,0));
        w = 3;
        h = 3;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        // T4
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(1,1));
        points.add(new Point(1,0));
        points.add(new Point(2,0));
        w = 3;
        h = 2;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        // T5
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(1,1));
        points.add(new Point(1,0));
        points.add(new Point(2,0));
        points.add(new Point(1,2));
        w = 3;
        h = 3;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        //V3
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(0,1));
        points.add(new Point(1,0));
        w = 2;
        h = 2;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        //V5
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(0,1));
        points.add(new Point(1,0));
        points.add(new Point(0,2));
        points.add(new Point(2,0));
        w = 3;
        h = 3;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
      
        //U
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(0,1));
        points.add(new Point(1,0));
        points.add(new Point(2,0));
        points.add(new Point(2,1));
        w = 3;
        h = 2;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        //W
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(1,0));
        points.add(new Point(1,1));
        points.add(new Point(2,1));
        points.add(new Point(2,2));
        w = 3;
        h = 3;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        // P
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(0,1));
        points.add(new Point(1,0));
        points.add(new Point(1,1));
        points.add(new Point(0,2));
        w = 2;
        h = 3;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        //F
        points = new ArrayList<Point>();
        points.add(new Point(0,0));
        points.add(new Point(0,1));
        points.add(new Point(1,1));
        points.add(new Point(2,1));
        points.add(new Point(1,2));
        w = 3;
        h = 3;
        p = new Piece(points, w, h, color);
        p.setPermutations(p.permute());
        pieces.add(p);
        
        return pieces;
    }


}
