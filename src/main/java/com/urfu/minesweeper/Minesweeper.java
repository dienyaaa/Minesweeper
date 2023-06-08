package com.urfu.minesweeper;
/*
The Minesweeper class creates an instance of the main program and starts up
the graphics thread.
*/

import javax.swing.*;
import java.awt.*;

class Minesweeper {

	public static void main(String[] args) {
		Minesweeper program = new Minesweeper();
		SwingUtilities.invokeLater(program::run);
	}

	private void run(){
		GameWindow game = new GameWindow();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

		game.setTitle("Minesweeper");
		game.pack();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		game.setResizable(false); 

		int windowX = dim.width/2 - game.getSize().width/2;
		int windowY = dim.height/2 - game.getSize().height/2;
		game.setLocation(windowX, windowY);
		game.setVisible(true);
	}
}
