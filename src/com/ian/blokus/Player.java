package com.ian.blokus;

import java.lang.Runtime;

public class Player {

    public static final int MINIMUM_FREE_MEMORY = 100 * 1024 * 1024;
    public static final int NUM_MOVES_KEPT = 30;
    
    
    
    private Color color;
    private AgentType agentType;
    private Player opponent;
    private int thinkTime; // in seconds
    private int moveNum;
    
    
    
    public Player(Color color, AgentType agentType, int thinkTime) {
        this.color = color;
        this.agentType = agentType;
        this.thinkTime = thinkTime;
        this.moveNum = 1;
        this.opponent = null;
    }
    
    

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public Color getColor() {
        return color;
    }

    public AgentType getAgentType() {
        return agentType;
    }

    public int getThinkTime() {
        return thinkTime;
    }

    public int getMoveNum() {
        return moveNum;
    }



}
