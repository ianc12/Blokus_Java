package com.ian.blokus;

import com.ian.blokus.Square;
import java.util.ArrayList;



public class Board {

    public static final Point PLAYER_ONE_START = new Point(4,4);
    public static final Point PLAYER_TWO_START = new Point(9,9);
    
    private int size;
    private Player playerOne;
    private Player playerTwo;
    private ArrayList<Square> playerOneFilled;
    private ArrayList<Square> playerTwoFilled;
    private int player1MaxX;
    private int player1MaxY;
    private int player2MaxX;
    private int player2MaxY;
    
    private Square[][] board;
    
    
    
    public Board(int size, Player playerOne, Player playerTwo) {
        this.size = size;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.board = new Square[size][size];
        this.playerOneFilled = new ArrayList<Square>();
        this.playerTwoFilled = new ArrayList<Square>();
        this.player1MaxX = 0;
        this.player1MaxY = 0;
        this.player2MaxX = 0;
        this.player2MaxY = 0;
        
        
        // create the squares
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                this.board[x][y] = new Square(x,y);
            }
        }
        
        // fill the corner and side neighbors in
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                Square square = this.getSquare(x, y);
                Square[] corners = new Square[4];
                int cornersIdx = 0;
                Square[] sides = new Square[4];
                int sidesIdx = 0;
                
                for (int dX = -1; dX <= 1; dX++) {  
                    for (int dY = -1; dY <= 1; dY++) {
                        
                        if (dX != 0 || dY != 0) {
                            Square s = this.getSquare(x + dX, y + dY);
                            
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
                square.setCornerNeighbors(corners);
                square.setSideNeighbors(sides);
            }
        }  
    }
    
    
    
    public Square getSquare(int x, int y) {
        
        if (x < 0 || x >= this.size) return null;
        if (y < 0 || y >= this.size) return null;
        
        return this.board[x][y];
    }

    
    
    public boolean isValidMove(Move move) {
        boolean touchesCorner = false;
        
        for (Point p : move.piece.getPoints()) {
            Square s = this.getSquare(move.x + p.x, move.y + p.y);
            
            if (s == null) {
                return false; // piece goes off the edge of the board
            }
            
            if (s.filled()) {
                return false; // square is already filled 
            }
            
            // cannot place piece adjacent to already placed piece
            for (Square sN : s.getSideNeighbors()) {
                if (sN != null && sN.getFillColor() == move.piece.getColor()) {
                    return false;
                }
            }
            
            for (Square cN : s.getCornerNeighbors()) {
                if (cN != null && cN.getFillColor() == move.piece.getColor()) {
                    touchesCorner = true;
                    break;
                }
            }
        }
        
        return touchesCorner;
    }

    
    /**
     * 
     * @param pieces list of pieces to apply to the current board state
     * @return ArrayList of all valid Moves using pieces provided
     */
    public ArrayList<Move> getAllValidMoves(ArrayList<Piece> pieces) {
        ArrayList<Move> validMoves = new ArrayList<Move>();
        
        for (int x = 0; x < this.size; x++) {
            for (int y = 0; y < this.size; y++) {
                //TODO: invalidate squares for optimization
                for (Piece piece : pieces) {
                    
                    int mindim = piece.minDimension();
                    if (x + mindim >= this.size || y + mindim >= this.size) {
                        continue;
                    }
                    
                    for (Piece perm : piece.getPermutations()) {
                        Move move = new Move(perm, x, y);
                        if (this.isValidMove(move)) {
                            validMoves.add(move);
                        }
                    } 
                }
            }
        }
        return validMoves;
    }
    
    
    /**
     * 
     * @return a deep copy of the board
     */
    public Board duplicate() {
        Board b = new Board(this.size, this.playerOne, this.playerTwo);
        for (int x = 0; x < this.size; x++) {
            for (int y = 0; y < this.size; y++) {
                Square ours = this.getSquare(x, y);
                Square theirs = b.getSquare(x, y);
                Color c = ours.getFillColor();
                theirs.setFillColor(c);
                if (c == this.playerOne.getColor()) {
                    b.playerOneFilled.add(theirs);
                } else if (c == this.playerTwo.getColor()) {
                    b.playerTwoFilled.add(theirs);
                }
            }
        }
        // TODO: other board info copy
        return b;
    }

    /**
     * 
     * @param move fills the squares on the board defined by move, no saftey checking
     */
    public void makeMove(Move move) {
        for (Point p : move.piece.getPoints()) {
            Square s = this.getSquare(move.x + p.x, move.y + p.y);
            Color c = move.piece.getColor();
            s.setFillColor(c);
            if (c == this.playerOne.getColor()) {
                this.playerOneFilled.add(s);
            } else if (c == this.playerTwo.getColor()) {
                this.playerTwoFilled.add(s);
            }
            
        }
    }

    
    /**
     * 
     * @param move removes fill on squares defined by move
     */
    public void unmakeMove(Move move) {
        for (Point p : move.piece.getPoints()) {
            Square s = this.getSquare(move.x + p.x, move.y + p.y);
            Color c = move.piece.getColor();
            s.setFillColor(Color.NONE);
            if (c == this.playerOne.getColor()) {
                this.playerOneFilled.remove(s);
            } else if (c == this.playerTwo.getColor()) {
                this.playerTwoFilled.remove(s);
            }
        }
    }
    
    
    
    
    public int evaluateCorners(Player player) {
        int numCorners = 0;
        if (player == this.playerOne) {
            for (Square s : this.playerOneFilled) {
                for (Square cornerN : s.getCornerNeighbors()) {
                    
                    if (cornerN != null && cornerN.getFillColor() == Color.NONE) {
                        boolean validCorner = true;
                    
                        for (Square sideN : cornerN.getSideNeighbors()) {
                            if (sideN != null && sideN.getFillColor() == this.playerOne.getColor()) {
                                validCorner = false; 
                            }
                        }
                        if (validCorner) {
                            numCorners++;
                        }
                    }
                }
            }
        } else if (player == this.playerTwo) {
            for (Square s : this.playerTwoFilled) {
                for (Square cornerN : s.getCornerNeighbors()) {
                    
                    if (cornerN != null && cornerN.getFillColor() == Color.NONE) {
                        boolean validCorner = true;
                    
                        for (Square sideN : cornerN.getSideNeighbors()) {
                            if (sideN != null && sideN.getFillColor() == this.playerTwo.getColor()) {
                                validCorner = false; 
                            }
                        }
                        if (validCorner) {
                            numCorners++;
                        }
                    }
                }
            }
            
        } 
        return numCorners;
    }
    
    /**
     * makes the move provided if it is a valid first move
     * 
     * @param move move to test as first move
     * @param isPlayerOne true if the player makes the first move of the game
     * @return true if the move passed is a valid move, false else
     */
    public boolean makeFirstMove(Move move, boolean isPlayerOne) {
        boolean containsP1Start = false;
        boolean containsP2Start = false;
        for (Point p : move.piece.getPoints()) {
            Square s = this.getSquare(move.x + p.x, move.y + p.y);
            if (s == null || s.getFillColor() != Color.NONE) {
                // invalid first move
                return false;
            }
            if (new Point(s.getX(),s.getY()).equals(PLAYER_ONE_START)) containsP1Start = true;
            if (new Point(s.getX(),s.getY()).equals(PLAYER_TWO_START)) containsP2Start = true;
        }
        
        if (isPlayerOne) {
            if (containsP1Start) {
                this.makeMove(move);
                return true;
            }
        } else {
            if (containsP2Start) {
                this.makeMove(move);
                return true;
            }
        }
        
        return false;
    }
    
    
    /**
     * 
     * @param color Color of pieces to evaluate
     * @return the number of filled sqares of fillColor 'color'
     */
    public int evaluateScore(Color color) {
        int score = 0;
        for (int x = 0; x < this.size; x++) {
            for (int y = 0; y < this.size; y++) {
                Square s = this.getSquare(x, y);
                if (s.getFillColor() == color) {
                    score++;
                }
            }
        }
        return score;
    }
    
    
    @Override
    public String toString() {
        String s = "";
        for (int y = this.size - 1; y >= 0; y--) {
            for (int a = 0; a < this.size; a++) {
                s += "------";
            }
            s += "\n";
            for (int x = 0; x < this.size; x++) {
                Square sq = this.getSquare(x, y);
                String col = sq.getFillColor().toString().substring(0, 2);
                if (col.equals("NO")) {
                    s += "|    |";
                }
                else {
                    s += "| " + col + " |";
                }
                
                
            }
            s += "\n";
        }
        return s;
    }



}
