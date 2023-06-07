package com.urfu.minesweeper;
/*
TimerPanel is the modified JPanel that will hold the game clock.
It attaches to a single game's GameData class.
*/

import javax.swing.*;

class TimerPanel extends JPanel {
	
	GameData gameData;
	JLabel timeLabel;

	public TimerPanel(){
		timeLabel = new JLabel("0");
		add(timeLabel);
	}

	public void updateData(GameData newData){
		gameData = newData;
		refresh();
	}

	public void refresh(){
		timeLabel.setText(Integer.toString(gameData.time));
		repaint();
	}
}
