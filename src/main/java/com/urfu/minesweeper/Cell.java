package com.urfu.minesweeper;/*
Cell is the lowest level of minesweeper. Each cell is an extended JButton
that also holds state information such as clicked/unclicked etc.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Cell extends JButton {
	
	GameData data;

	private boolean lonely;
	private boolean flagged;
	private boolean isMine;

	private int dangerousNeighbours;
	private List<ImageIcon> tiles;

	public Cell(GameData data_in){
		
		data = data_in;
		flagged = false;
		isMine = false;
		setEnabled(true);

		// Want to modify this. Seems inefficient.
		tiles = new ArrayList<ImageIcon>();
		tiles.add(new ImageIcon(this.getClass().getClassLoader().getResource("res/0.png")));
		tiles.add(new ImageIcon(this.getClass().getClassLoader().getResource("res/1.png")));
		tiles.add(new ImageIcon(this.getClass().getClassLoader().getResource("res/2.png")));
		tiles.add(new ImageIcon(this.getClass().getClassLoader().getResource("res/3.png")));
		tiles.add(new ImageIcon(this.getClass().getClassLoader().getResource("res/4.png")));
		tiles.add(new ImageIcon(this.getClass().getClassLoader().getResource("res/5.png")));
		tiles.add(new ImageIcon(this.getClass().getClassLoader().getResource("res/6.png")));
		tiles.add(new ImageIcon(this.getClass().getClassLoader().getResource("res/7.png")));
		tiles.add(new ImageIcon(this.getClass().getClassLoader().getResource("res/8.png")));
		tiles.add(new ImageIcon(this.getClass().getClassLoader().getResource("res/unclicked.png")));
		tiles.add(new ImageIcon(this.getClass().getClassLoader().getResource("res/mine.png")));
		tiles.add(new ImageIcon(this.getClass().getClassLoader().getResource("res/flag.png")));

		setIcon(tiles.get(9));

		// Use a mouseListener because we want right click as well
		MouseListener clicker = new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				if (isEnabled()) {
					if (SwingUtilities.isLeftMouseButton(e)) 
					{	
						leftClick();
					}
					else if (SwingUtilities.isRightMouseButton(e))
					{
						toggleFlag();
					}
				}
			}

			// Other methods that have to be included in the listener
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}

		};
		addMouseListener(clicker);
	}

	public void setMine(){
		isMine = true;
	}

	public boolean isMine(){
		return isMine;
	}

	private void toggleFlag(){
		if (flagged) 
		{
			flagged = false;
			setIcon(tiles.get(9));
			setDisabledIcon(tiles.get(9));
		} 
		else if(flagged == false)
		{
			flagged = true;
			setIcon(tiles.get(11));
			setDisabledIcon(tiles.get(11));
		}

		data.win();
	}

	private void leftClick(){

		data.inProgress();
		boolean autocleared = false;
		setEnabled(false);

		if (isMine) 
		{
			data.lose();
		} 
		else
		{	
			reveal();
		}

		while(autocleared == false)
		{	
			autocleared = data.autoRevealMap();
		}

		data.win();
	}

	public void reveal(){

		setEnabled(false);

		if (isMine()) 
		{
			setIcon(tiles.get(10));
			setDisabledIcon(tiles.get(10));
		}
		else
		{
			int index = getDangerousNeighbours();
			setIcon(tiles.get(index));
			setDisabledIcon(tiles.get(index));
		}
	}

	public boolean isFlagged(){
		return flagged;
	}

	public void setLonely(){
		lonely = true;
	}

	public boolean isLonely(){
		return lonely;
	}

	public int getDangerousNeighbours(){
		return dangerousNeighbours;
	}

	public void setDangerousNeighbours(int num){
		dangerousNeighbours = num;
	}
}
