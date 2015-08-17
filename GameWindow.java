/*
The gamewindow is the top-level container for all graphical components and
panels, including the control buttons.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

class GameWindow extends JFrame {

	private JPanel mainPanel;
	private TimerPanel tp;
	private BoardPanel bp;
	private ControlPanel cp;
	private GameData bd;
	private int difficulty = 0;

	public GameWindow(){

		// MAIN PANEL - background
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		// BOARD, CONTROL + TIMER PANELS
		cp = new ControlPanel(this);
		bp = new BoardPanel(); 	
		tp = new TimerPanel();			  
		bd = new GameData(bp, tp, difficulty); 
		bp.updateData(bd);

		initialise();
	}

	/* Setup the frame */
	private void initialise(){
		mainPanel.add(tp);
		mainPanel.add(bp);
		mainPanel.add(cp);
		getContentPane().add(mainPanel);
	}

	/* Trigger creation of a new game with current parameters */
	public void reset(){
		bd = new GameData(bp, tp, difficulty);
		bp.updateData(bd);
		pack();
	}

	/* Trigger creation of a new game with different parameters */
	public void difficulty(){
		difficulty = (difficulty+1)%3 ;
		bd = new GameData(bp, tp, difficulty);
		bp.updateData(bd);
		pack();
	}
}
