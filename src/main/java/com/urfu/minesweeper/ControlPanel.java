package com.urfu.minesweeper;/*
ControlPanel is a modified JPanel containing the control buttons
new game, difficulty, quit
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class ControlPanel extends JPanel implements ActionListener{

	GameWindow window;

	public ControlPanel(GameWindow w){
		window = w;

		JButton b_restart = new JButton("New Game");
		b_restart.addActionListener(this);
		b_restart.setActionCommand("reset");
		add(b_restart);

		JButton b_diff = new JButton("Difficulty");
		b_diff.addActionListener(this);
		b_diff.setActionCommand("diff");
		add(b_diff);
		
		JButton b_quit = new JButton("Quit");
		b_quit.addActionListener(this);
		b_quit.setActionCommand("quit");
		add(b_quit);
	}

	/* Handle a control panel button press */
	public void actionPerformed(ActionEvent e){
		String command = e.getActionCommand();
 
		if (command.equals("quit")){
			System.exit(0);
		}
		
		else if (command.equals("diff")) {
			window.difficulty();
		}	
		
		else if (command.equals("reset")) {
			window.reset();
		}	
	}
}

