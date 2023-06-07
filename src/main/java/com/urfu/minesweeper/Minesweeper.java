package com.urfu.minesweeper;/*
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

		game.setTitle("MineSweepin'");
		game.pack();
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		game.setResizable(false); 

		int winx = dim.width/2 - game.getSize().width/2;
		int winy = dim.height/2 - game.getSize().height/2;
		game.setLocation(winx,winy); 
		game.setVisible(true);
	}
}
