package com.atomicobject.othello;
import java.net.Socket;
import java.util.Arrays;
import java.util.ListIterator;
import com.google.gson.Gson;
public class AI 
{
    
    static int INF = 10000;
    static int inc = 0;
    public AI() 
    {
        
    }
    
    public AI(int[] moves) 
    {
        
    }
    
    Boolean movesLeft(GameState state) 
    {
        int[][] board = state.getBoard();
        int numColumns = board[0].length;
        for (int i = 0; i < numColumns; i++) 
        {
            if (board[0][i] == 0) 
                return true;
        }
        return false;
    }
    int evaluate(GameState state) 
    {
        int[][] b = state.getBoard();
        int numColumns = b[0].length;
        int numRows = b.length; 
        int p = state.getPlayer();
        int q = -1;
        if(p == 1)
            q = 2;
        else
            q = 1;
        // Check rows for win
        for (int row = 0; row < numRows; row++) 
        {
            for (int col = 0; col <= numColumns - 4; col++) 
            {
                if (b[row][col] == b[row][col+1] && b[row][col+1] == b[row][col+2] && b[row][col+2] == b[row][col+3]) 
                {   
                    //if it's original player, then return 100
                    if (b[row][col] == p) 
                        return 100;
                    else if (b[row][col] == q) 
                        return -100;
                }
            }
        }
        
        // Check columns for win
        for (int col = 0; col < numColumns; col++) 
        {
            for (int row = 0; row <= numRows - 4; row++) 
            {
                if (b[row][col] == b[row+1][col] && b[row+1][col] == b[row+2][col] && b[row+1][col] == b[row+3][col]) 
                {
                    if (b[row][col] == p) 
                        return 100;
                    else if (b[row][col] == q) 
                        return -100;
                }
            }
        }
        
        // Check for diagonal win across all rows
        for (int row = 0; row <= numRows - 4; ++row) 
        {
            for (int col = 0; col <= numColumns - 4; ++col) 
            {
                if (b[row][col] == b[row+1][col+1] && b[row+1][col+1] == b[row+2][col+2] && b[row+1][col+1] == b[row+3][col+3]) 
                {
                    if (b[row][col] == p) 
                    {
                        return 100;
                    } 
                    else if (b[row][col] == q) 
                    {
                        return -100;
                    }
                }
            }
        }
        
        // Check for diagonal win across all columns
        for (int row = 0; row <= numRows - 4; ++row) 
        {
            for (int col = 3; col < numColumns; ++col) 
            {
                if (b[row][col] == b[row+1][col-1] && b[row+1][col-1] == b[row+2][col-2] && b[row+1][col-1] == b[row+3][col-3]) 
                {
                    if (b[row][col] == p) 
                    {
                        return 100;
                    } 
                    else if (b[row][col] == q) 
                    {
                        return -100;
                    }
                }
            }
        }
        
        // Else if none of them have won then return 0
        return 0;
    }
    
    int minimax(GameState state, int depth, Boolean isMaximizer, int alpha, int beta) 
    {
        int score = evaluate(state);
        
        // If Maximizer has won or if Minimizer has won
        if (score == 100)
            return score;// - depth;
        
        if(score == -100)
            return score;// + depth;
        
        // If there are no moves left and no winner then there is a tie.
        if (movesLeft(state) == false)
            return 0;
        
        if(depth == 9)
            return score;
        
        int[][] b = state.getBoard();
        int numColumns = b[0].length;
        int numRows = b.length;
        
        // fill temp with all the values of the board
        int[][] temp = new int[numRows][numColumns];
        
        for (int i = 0; i < numRows; i++) 
        {
            for (int j = 0; j < numColumns; j++) 
                temp[i][j] = b[i][j];
        }
        
        int p = state.getPlayer();
        
        // If this is the Maximizer's turn
        if (isMaximizer) 
        {
            int best = -INF;
            
            // Traverse all cells
            for (int col = 0; col < numColumns; ++col) {
                for (int row = numRows - 1; row >= 0; --row) {
                    if (b[row][col] == 0) 
                    {
                        // Make the move
                        temp[row][col] = p;
                        state.setBoard(temp);
                        
                        int val = minimax(state, depth + 1, !isMaximizer, alpha, beta); //false
                        best = Math.max(best, val);
                        alpha = Math.max(alpha, best);
                        
                        // Undo the move
                        temp[row][col] = 0;
                        state.setBoard(temp);
                        
                        //break once you find first available move in column
                        break;
                    }
                }
                // Alpha Beta Pruning
                if (beta <= alpha) 
                    break;
            }
            return best - depth;
        } 
        else 
        {
            int best = INF;
            int q = -1;
            // Traverse all cells
            for (int col = 0; col < numColumns; ++col) {
                for (int row = numRows - 1; row >= 0; --row) 
                {
                    if (b[row][col] == 0) 
                    {
                        // Make the move
                        if (p == 1)
                            q = 2;
                        else
                            q = 1;
                        
                        temp[row][col] = q;
                        state.setBoard(temp);
                        
                        int val = minimax(state, depth + 1, !isMaximizer, alpha, beta); //true
                        best = Math.min(best, val);
                        beta = Math.min(beta,  best);
                        
                        // Undo the move
                        temp[row][col] = 0;
                        state.setBoard(temp);
                       
                        break;
                    }
                }
                // Alpha Beta Pruning
                if (beta <= alpha) 
                    break;
            }
            return best + depth;
        }
    }
    
    public int computeMove(GameState state) 
    {
        int bestVal = -INF;
        int move = -1;
        int moveVal = 0;
        int[][] b = state.getBoard();
        int numColumns = b[0].length;
        int numRows = b.length;
        int p = state.getPlayer();
        
        //copy b state values into temp
        int[][] temp = new int[numRows][numColumns];
        
        for (int i = 0; i < numRows; ++i) 
        {
            for (int j = 0; j < numColumns; ++j) 
                temp[i][j] = b[i][j];
        }
        
        //check for valid moves
        for (int col = 0; col < numColumns; ++col) 
        {
            for (int row = numRows - 1; row >= 0; --row) 
            {
                if (b[row][col] == 0) 
                {
                    // Make the move
                    temp[row][col] = p;
                    state.setBoard(temp);
                    
                    // Compute evaluation function for this move
                    moveVal = minimax(state, 0, false, -INF, INF);
                    // Undo the move
                    temp[row][col] = 0;
                    state.setBoard(temp);         
                    
                    if (moveVal > bestVal) 
                    {
                        move = col;
                        bestVal = moveVal;
                    }
                    break;
                }
            }
        }
        
        if (inc < 2) {
	        ++inc;
	        return 3;
        }

        return move;
    }
    
    
    // Used for testing only
    public static void main(String[] args) 
    {
        GameState state = new GameState();
        state.setPlayer(1);
        state.setBoard(new int[][]{{0, 0, 0, 0, 0, 0, 0},
					               {0, 0, 0, 0, 0, 0, 0},
					               {0, 0, 0, 0, 0, 0, 0},
					               {0, 0, 2, 0, 0, 0, 0},
					               {0, 0, 2, 0, 0, 0, 0},
					               {0, 1, 2, 1, 0, 0, 1}});
        AI ai = new AI();
        System.out.printf("Final answer: %d", ai.computeMove(state));
    }
}
