package com.ian.blokus;

import java.util.ArrayList;

public class Point {

    
    public int x;
    public int y;
    
    public Point() {
        x = 0;
        y = 0;
    }
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
    }
    
    /**
     * 
     * @param points ArrayList of Points to compare p to
     * @param p Point to check if inside points
     * @return true if the list contains a Point equal to p, false else
     */
    public static boolean listContians(ArrayList<Point> points, Point p) {
        boolean contains = false;
        for (Point comp : points) {
            if (comp.equals(p)) {
                contains = true;
                break;
            }
        }
        return contains;
    }
    
    
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Point)) {
            return false;
        }
        Point p = (Point) other;
        return p.x == this.x && p.y == this.y;
    }

}
