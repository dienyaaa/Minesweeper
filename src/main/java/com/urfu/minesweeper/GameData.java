package com.urfu.minesweeper;/*
BoardData contains all data attached to a single game of minesweeper. 
That is, level of difficulty, current mine map etc.
*/

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

class GameData {

	/* Nested class to increment the game clock - only ever used here */	
	class Tick extends TimerTask{
		public void run() {
			time++;
			t_panel.refresh();
	    }
	}

	private Timer timer;
	protected int time = 0;
	private boolean inProgress; 

	private Cell[][] map;
	private int size;
	private int mines;
	private BoardPanel b_panel;
	private TimerPanel t_panel;

	public GameData(BoardPanel bp, TimerPanel tp, int difficulty){
		
		t_panel = tp;
		t_panel.updateData(this);

		b_panel = bp;
		
		timer = new Timer();
		inProgress = false; 
		setDifficulty(difficulty);

		map = new Cell[size][size];
		for(int i = 0; i < size; i++)
		{
			for(int j=0; j < size; j++)
			{
				map[i][j] = new Cell(this);
			}
		}

		generateMines(mines);
		setMineCounts();
	}

	public void inProgress(){
		if(!inProgress){
			inProgress = true;
			timer.schedule(new Tick(), 0, 1000);
		} else {
			return;
		}
	}

	private void setDifficulty(int difficulty){
		if (difficulty == 0) {
			size = 10;
			mines = 10;
		}
		else if (difficulty == 1) {
			size = 15;
			mines = 30;
		}
		else if (difficulty == 2) {
			size = 20;
			mines = 60;
		}
	}

	private void generateMines(int number){

		for (int i = 0; i < number; i++) 
		{
			Random random = new Random();
			int rand_i = random.nextInt(size);
			int rand_j = random.nextInt(size);

			map[rand_i][rand_j].setMine();
		}
	}

	private void setMineCounts(){
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{	
				map[i][j].setDangerousNeighbours( countDangerousNeighbours(i, j) );
			}
		}
	}

	private int countDangerousNeighbours(int ti, int tj){

		int count = 0;

		for (int i = ti-1; i<= ti+1; i++) 
		{
			for (int j = tj-1; j<= tj+1; j++) 
			{	
				if (i != ti || j != tj) 
				{
					if ( inBounds(i, j) ) 
					{
						if ( map[i][j].isMine() ) 
						{
							count++;
						}
					}
				}
			}	
		}

		return count;
	}

	private boolean inBounds(int i, int j){

		if (i < size && i >= 0 && j < size && j >= 0) 
		{
			return true;
		}
		else 
		{
			return false;
		}
	}

	protected boolean autoRevealMap(){
		boolean unchanged = true;

		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{	
				if ( !map[i][j].isEnabled() 
					&& map[i][j].getDangerousNeighbours() == 0 
					&& !map[i][j].isLonely() 
					&& !map[i][j].isMine()) 
				{	
					autoReveal(i, j);
					unchanged = false;
				}
			}
		}

		b_panel.refresh();
		return unchanged;
	}

	// Automatically clear those neighbours
	private void autoReveal(int ti, int tj){

		map[ti][tj].setLonely();

		for(int i = ti-1; i <= ti+1; i++)
		{
			for(int j = tj-1; j <= tj+1; j++)
			{
				if (inBounds(i, j)) 
				{
					map[i][j].reveal();
				}
			}
		}
	}

	// Check to see if every mine has been flagged correctly
	protected void win(){
		
		boolean win = true;

		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{	
				Cell cell = map[i][j];

				if( cell.isMine() && !cell.isFlagged() )
				{
					win = false;
				}

				if( !cell.isMine() && cell.isFlagged() )
				{
					win = false;
				}
			}
		}

		if (win) 
		{	
			timer.cancel();
			timer.purge();
			disableAll();
			Utils.print("YOU WIN");
		}
	}

	protected void lose(){
		revealAll();
		timer.cancel();
		timer.purge();
	}

	private void disableAll(){
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{	
				map[i][j].setEnabled(false);
			}
		}
	}

	private void enableAll(){
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{	
				map[i][j].setEnabled(true);
			}
		}
	}

	private void revealAll(){
		for(int i = 0; i < size; i++)
		{ 
			for(int j = 0; j < size; j++)
			{	
				map[i][j].reveal();
			}
		}
		b_panel.refresh();
	}

	public int getSize(){
		return size;
	}

	public Cell getCell(int i, int j){
		return map[i][j];
	}

	public void stopTimer(){
		timer.cancel();
		timer.purge();
	}
}
