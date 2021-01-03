package com.ian.blokus;

import java.util.ArrayList;
import java.util.Random;


public class Game {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ArrayList<Piece> pieces = Piece.createPieces(Color.BLUE);
//        for (Piece p : pieces) {
//            System.out.printf(p.toString());
//        }
//        for (Piece p : pieces.get(pieces.size() - 1).getPermutations()) {
//            System.out.printf(p.toString());
//        }
        //System.out.println(Color.BLUE.toString().substring(0, 2));
    Player p = new Player(Color.BLUE, AgentType.HUMAN,30);
    Player x = new Player(Color.RED, AgentType.HUMAN,30);
    
    Board b = new Board(14, x, p);
    b.makeMove(new Move(pieces.get(0), 0,0));
    ArrayList<Move> moves = b.getAllValidMoves(pieces);
    Random r = new Random();
    int i = r.nextInt(moves.size());
    b.makeMove(moves.get(i));
    System.out.printf("%d valid corners\n", b.evaluateCorners(p));
    System.out.println(b.toString());
        
    }

    
    
}
