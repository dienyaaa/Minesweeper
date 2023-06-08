package com.urfu.minesweeper;
/*
BoardPanel is the single JPanel that all games get loaded to. The game
data for a single game is stored in an attached GameData object.
*/

import javax.swing.*;
import java.awt.*;

class BoardPanel extends JPanel{

	private static final int ICON_PIX = 32;
	private GameData gameData;
	
	public void updateData(GameData newData){

		if (gameData != null) {
			gameData.stopTimer();
		}

		removeAll();
		gameData = newData;

		int boardSize = gameData.getBoardSize();
		setLayout(new GridLayout(boardSize, boardSize, 0, 0));
		setPreferredSize(new Dimension( boardSize * ICON_PIX, boardSize * ICON_PIX));

		for(int i=0; i < boardSize; i++)
		{
			for(int j=0; j < boardSize; j++)
			{	
				Cell currentCell = gameData.getCell(i,j);
				add(currentCell);
			}
		}
		repaint();
	}
}
