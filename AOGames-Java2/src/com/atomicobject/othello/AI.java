package com.atomicobject.othello;

import java.net.Socket;
import java.util.Arrays;
import java.util.ListIterator;

import com.google.gson.Gson;

public class AI {
	
	static int INF = 10000;

	/*
	public AI(int[] moves) {
		
	}
	*/
	
	Boolean movesLeft(GameState state) {
		int[][] board = state.getBoard();
		int numColumns = board[0].length;
		for (int i = 0; i < numColumns; ++i) {
			if (board[0][i] == 0) {
				return true;
			}
		}
		return false;
	}
	
	int evaluate(GameState state) {
		int[][] b = state.getBoard();
		int numColumns = b[0].length;
		int numRows = b.length;	
		int p = state.getPlayer();
		
		// Check rows for win
		for (int row = 0; row < numRows; ++row) {
			for (int col = 0; col <= numColumns - 4; ++col) {
				if (b[row][col] == p && b[row][col+1] == p && b[row][col+2] == p && b[row][col+3] == p) {
					if (p == 1) {
						return 100;
					} else if (p == 2) {
						return -100;
					}
				}
			}
		}
		
		// Check columns for win
		for (int col = 0; col < numColumns; ++col) {
			for (int row = 0; row <= numRows - 4; ++row) {
				if (b[row][col] == p && b[row+1][col] == p && b[row+2][col] == p && b[row+3][col] == p) {
					if (p == 1) {
						return 100;
					} else if (p == 2) {
						return -100;
					}
				}
			}
		}
		
		// Check for diagonal win across all rows
		for (int row = 0; row <= numRows - 4; ++row) {
			for (int col = 0; col <= numColumns - 4; ++col) {
				if (b[row][col] == p && b[row+1][col+1] == p && b[row+2][col+2] == p && b[row+3][col+3] == p) {
					if (p == 1) {
						return 100;
					} else if (p == 2) {
						return -100;
					}
				}
			}
		}
		
		// Check for diagonal win across all columns
		for (int row = 0; row <= numRows - 4; ++row) {
			for (int col = 3; col < numColumns; ++col) {
				if (b[row][col] == p && b[row+1][col-1] == p && b[row+2][col-2] == p && b[row+3][col-3] == p) {
					if (p == 1) {
						return 100;
					} else if (p == 2) {
						return -100;
					}
				}
			}
		}
		
		// Else if none of them have won then return 0
		return 0;
	}
	
	int minimax(GameState state, int depth, Boolean isMaximizer) {
		int score = evaluate(state);
		
		// If Maximizer has won
		if (score == 100) {
			return score;
		}
		
		// If Minimizer has won
		if (score == -100) {
			return score;
		}
		
		// If there are no moves left and no winner
		// then there is a tie.
		if (movesLeft(state) == false) {
			return 0;
		}
	
		int[][] b = state.getBoard();
		int numColumns = b[0].length;
		int numRows = b.length;
		
		int[][] temp = new int[numRows][numColumns];
		
		for (int i = 0; i < numRows; ++i) {
			for (int j = 0; j < numColumns; ++j) {
				temp[i][j] = b[i][j];
			}
		}
		
		int p = state.getPlayer();
		
		// If this is the Maximizer's turn
		if (isMaximizer) {
			int best = -INF;
			
			// Traverse all cells
			for (int col = 0; col < numColumns; ++col) {
				for (int row = numRows - 1; row >= 0; --row) {
					if (b[row][col] == 0) {
						// Make the move
						temp[row][col] = p;
						state.setBoard(temp);
						
						int val = minimax(state, depth + 1, !isMaximizer);
						best = Math.max(best, val);
								
						// Undo the move
						temp[row][col] = 0;
						state.setBoard(temp);
						
						break;
					}
				}
			}
			return best - depth;
		} else {
			int best = INF;
			
			// Traverse all cells
			for (int col = 0; col < numColumns; ++col) {
				for (int row = numRows - 1; row >= 0; --row) {
					if (b[row][col] == 0) {
						// Make the move
						int q = 0;
						if (p == 1) {
							q = 2;
						} else {
							q = 1;
						}
						temp[row][col] = q;
						state.setBoard(temp);
						
						int val = minimax(state, depth + 1, isMaximizer);
						best = Math.min(best, val);
								
						// Undo the move
						temp[row][col] = 0;
						state.setBoard(temp);
						
						break;
					}
				}
			}
			return best + depth;
		}
	}
		
		int findBestMove(GameState state) {
			int bestVal = -INF;
			int move = -1;
			
			int[][] b = state.getBoard();
			int numColumns = b[0].length;
			int numRows = b.length;
			int p = state.getPlayer();
			
			int[][] temp = new int[numRows][numColumns];
			
			for (int i = 0; i < numRows; ++i) {
				for (int j = 0; j < numColumns; ++j) {
					temp[i][j] = b[i][j];
					System.out.printf("%d", temp[i][j]);
				}
				System.out.println();
			}
			
			System.out.printf("numColumns = %d%n", numColumns);
			System.out.printf("numRows = %d%n", numRows);
			for (int col = 0; col < numColumns; ++col) {
				for (int row = numRows - 1; row >= 0; --row) {
					System.out.println("Test0");
					if (b[row][col] == 0) {
						System.out.println("Test1");
						// Make the move
						temp[row][col] = p;
						state.setBoard(temp);
						
						// Compute evaluation function for this move
						int moveVal = minimax(state, 0, false);
						System.out.println("Test2");
						
						// Undo the move
						temp[row][col] = 0;
						state.setBoard(temp);
						System.out.printf("moveVal = %d%n", moveVal);
						if (moveVal > bestVal) {
							move = col;
							bestVal = moveVal;
							System.out.println("Test3");
						}
						System.out.printf("move = %d%n", move);
					}
				}
				System.out.printf("hmove = %d%n", move);
			}
			
			System.out.printf("fmove = %d", move);
			return move;
		}

	// Github test
	public int computeMove(GameState state) {
		System.out.println("AI returning canned move for game state - " + state);
		return 1;
	}
	
	// Used for testing only
	public static void main(String[] args) {
		GameState state = new GameState();
		state.setPlayer(1);
		state.setBoard(new int[][]{{0, 0, 0, 0, 0, 0, 0},
				                   {0, 0, 0, 0, 0, 0, 0},
				                   {0, 0, 0, 0, 0, 0, 0},
				                   {0, 2, 0, 0, 1, 0, 0},
				                   {0, 2, 0, 0, 1, 0, 0},
				                   {0, 2, 0, 0, 1, 0, 0},
				                   });
		AI ai = new AI();
		System.out.printf("%d", ai.findBestMove(state));
	}

}
