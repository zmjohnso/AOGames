package com.atomicobject.othello;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AITest {

	@Test
	public void blockTest1() {
				AI ai = new AI();
				GameState state = new GameState();
				state.setPlayer(1);
				state.setBoard(new int[][]{{0, 0, 0, 0, 0, 0, 0},
						                   {0, 0, 0, 0, 0, 0, 0},
						                   {0, 0, 0, 0, 0, 0, 0},
						                   {0, 0, 2, 0, 0, 0, 0},
						                   {1, 0, 2, 0, 0, 0, 0},
						                   {1, 1, 2, 0, 0, 0, 0}});
				
				assertEquals(2, ai.computeMove(state));
	}
	
	@Test
	public void takeMid() {
				AI ai = new AI();
				GameState state = new GameState();
				state.setPlayer(1);
				state.setBoard(new int[][]{{0, 0, 0, 0, 0, 0, 0},
						                   {0, 0, 0, 0, 0, 0, 0},
						                   {0, 0, 0, 0, 0, 0, 0},
						                   {0, 0, 0, 0, 0, 0, 0},
						                   {0, 0, 0, 0, 0, 0, 0},
						                   {0, 0, 0, 0, 0, 0, 0}});
				
				assertEquals(3, ai.computeMove(state));
	}
	
	@Test
	public void complexMid() {
				AI ai = new AI();
				GameState state = new GameState();
				state.setPlayer(2);
				state.setBoard(new int[][]{{1, 1, 0, 0, 0, 0, 0},
						                   {2, 1, 0, 0, 0, 0, 0},
						                   {1, 1, 1, 0, 0, 0, 0},
						                   {2, 2, 2, 0, 0, 0, 0},
						                   {1, 1, 2, 0, 0, 0, 0},
						                   {2, 2, 2, 1, 0, 0, 0}});
				
				assertEquals(2, ai.computeMove(state));
	}
	
	@Test
	public void takeMid2() {
				AI ai = new AI();
				GameState state = new GameState();
				state.setPlayer(1);
				state.setBoard(new int[][]{{0, 0, 0, 0, 0, 0, 0},
						                   {0, 0, 0, 0, 0, 0, 0},
						                   {0, 0, 0, 0, 0, 0, 0},
						                   {0, 0, 0, 0, 0, 0, 0},
						                   {0, 0, 0, 0, 0, 0, 0},
						                   {0, 0, 2, 1, 0, 0, 0}});
				
				assertEquals(3, ai.computeMove(state));
	}
	
	@Test
	public void takeMid3() {
				AI ai = new AI();
				GameState state = new GameState();
				state.setPlayer(1);
				state.setBoard(new int[][]{{0, 0, 0, 0, 0, 0, 0},
						                   {0, 0, 0, 0, 0, 0, 0},
						                   {0, 0, 0, 0, 0, 0, 0},
						                   {0, 0, 2, 0, 0, 0, 0},
						                   {0, 0, 2, 0, 0, 0, 0},
						                   {0, 1, 2, 1, 0, 0, 1}});
				
				assertEquals(2, ai.computeMove(state));
	}

}
