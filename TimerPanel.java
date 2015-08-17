/* 
TimerPanel is the modified JPanel that will hold the game clock. 
It attaches to a single game's GameData class.
*/

import javax.swing.*;
import java.awt.*;

class TimerPanel extends JPanel {
	
	GameData data;
	JLabel label;

	public TimerPanel(){
		label = new JLabel("0");
		add(label);
	}

	public void updateData(GameData data_in){
		data = data_in;
		label.setText(Integer.toString(data.time));
	}

	public void refresh(){
		label.setText(Integer.toString(data.time));
		repaint();
	}
}
