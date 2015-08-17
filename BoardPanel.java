/* 
BoardPanel is the single JPanel that all games get loaded to. The game
data for a single game is stored in an attached GameData object.
*/

import javax.swing.*;
import java.awt.*;

class BoardPanel extends JPanel{

	private static final int ICONPIX = 32;
	private GameData data;
	
	public void updateData(GameData new_data){	

		if (data != null) {
			data.stopTimer();
		}

		removeAll();
		data = new_data;	

		int size = data.getSize();
		setLayout(new GridLayout(size, size, 0, 0));
		setPreferredSize(new Dimension( size * ICONPIX, size * ICONPIX));

		for(int i=0; i < size; i++)
		{
			for(int j=0; j < size; j++)
			{	
				Cell current = data.getCell(i,j);
				add(current);
			}
		}
		refresh();
	}

	public void refresh(){
		repaint();
	}

}
