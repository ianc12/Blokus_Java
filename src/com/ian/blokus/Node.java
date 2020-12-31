package com.ian.blokus;

import java.util.ArrayList;
import java.lang.Math;

public class Node {
    
    
    
    private Node parent;
    private float ucbVal;
    private int visits;
    private int wins;
    private ArrayList<Node> children; 
    private ArrayList<Move> moveStack;   
    
    
    public Node(Node parent, float initialVal, Move move) {
        this.parent = parent;
        this.visits = 0;
        this.wins = 0;
        this.children = new ArrayList<Node>();
        if (this.parent != null) {
            this.moveStack = (ArrayList<Move>) parent.moveStack.clone();
            this.moveStack.add(move);
        } else {
            this.moveStack = new ArrayList<Move>();
        }
        
        this.ucbVal = initialVal;
    }
 
    
    
    
    
}
