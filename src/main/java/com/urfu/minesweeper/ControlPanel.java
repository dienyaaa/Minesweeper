package com.urfu.minesweeper;
/*
ControlPanel is a modified JPanel containing the control buttons
new game, change difficulty, quit
*/

import javax.swing.*;
import java.awt.event.*;

class ControlPanel extends JPanel implements ActionListener{

	GameWindow window;

	public ControlPanel(GameWindow gameWindow){
		window = gameWindow;
		add(createButton("New Game"));
		add(createButton("Change Difficulty"));
		add(createButton("Quit"));
	}

	private JButton createButton(String actionCommand) {
		JButton button = new JButton(actionCommand);
		button.addActionListener(this);
		button.setActionCommand(actionCommand);
		return button;
	}

	/* Handle a control panel button press */
	public void actionPerformed(ActionEvent event){
		String command = event.getActionCommand();

		switch (command) {
			case "New Game" -> window.reset();
			case "Change Difficulty" -> window.increaseDifficultyLevel();
			case "Quit" -> System.exit(0);
		}
	}
}

