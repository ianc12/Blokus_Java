package com.ian.blokus;

import java.util.ArrayList;
import java.lang.Math;


public class Node {
 
    public static final double UCB_CONST = 1.0;
    
    private Node parent;
    private double ucbVal;
    private int visits;
    private int wins;
    private int ties;
    private ArrayList<Node> children; 
    private ArrayList<Move> moveStack;   
    
    
    public Node(Node parent, double initialVal, Move move) {
        this.parent = parent;
        this.visits = 0;
        this.wins = 0;
        this.ties = 0;
        this.children = new ArrayList<Node>();
        if (this.parent != null) {
            this.moveStack = (ArrayList<Move>) parent.getMoveStack().clone();
            this.moveStack.add(move);
        } else {
            this.moveStack = new ArrayList<Move>();
        }
        
        this.ucbVal = initialVal;
    }
 
    
    public static Node getLargestChild(Node root) {
        if (root.isLeaf()) {
            return null;
        }
        
        Node maxChild = null;
        double max = Double.MIN_VALUE;
        for (Node child : root.getChildren()) {
            if (child.getUcbVal() > max) {
                max = child.getUcbVal();
                maxChild = child;
            }
        }
    
        return maxChild;
    }
    
    public static Node visit(Node root) {
        root.setVisits(root.getVisits() + 1);
        if (root.isLeaf()) {
            return root;
        }
        
        return Node.visit(Node.getLargestChild(root));
    }

    public double calculateUCB() {
        
        if (this.visits == 0) {
            // skip calculation if this node has no plays
            return this.ucbVal;
        }
        double mean = (this.wins + (.5 * this.ties)) / this.visits; 
        // Upper bound for variance on a random variable bounded [0,1]
        // 0.25 is .5 squared in the variance calculation
        double variance = ((this.wins + (0.25 * this.ties)) / this.visits) - Math.pow(mean, 2) + Math.sqrt((2 * Math.log(this.parent.getVisits())) / this.visits);
        // UCB1 tuned algorithm
        double ucb = mean + Math.sqrt((Math.log(this.parent.getVisits()) / this.visits) * Math.min(0.25, variance));
        return ucb;
    }
    
    
    public void backpropogate(int result) {
        if (result == 1) {
            this.wins++;
        } else if (result == 2) {
            this.ties++;
        }
        
        for (Node n : this.children) {
            n.setUcbVal(n.calculateUCB());
        }
        
        if (this.parent != null) {
            this.parent.backpropogate(result);
        }
    }
    
    public boolean isLeaf() {
        return this.children.size() == 0;
    }
    
    
    public void addChild(Node node) {
        this.children.add(node);
    }



    public Node getParent() {
        return parent;
    }


    public double getUcbVal() {
        return ucbVal;
    }



    public void setUcbVal(double ucbVal) {
        this.ucbVal = ucbVal;
    }



    public int getVisits() {
        return visits;
    }



    public void setVisits(int visits) {
        this.visits = visits;
    }


    public int getWins() {
        return wins;
    }

    


    public void setWins(int wins) {
        this.wins = wins;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public ArrayList<Move> getMoveStack() {
        return moveStack;
    }


    public int getTies() {
        return ties;
    }


    public void setTies(int ties) {
        this.ties = ties;
    }
    
}
