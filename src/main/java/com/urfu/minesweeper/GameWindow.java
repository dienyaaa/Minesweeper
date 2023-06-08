package com.urfu.minesweeper;
/*
The GameWindow is the top-level container for all graphical components and
panels, including the control buttons.
*/

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

class GameWindow extends JFrame {

    private final JPanel mainPanel;
    private final TimerPanel timerPanel;
    private final BoardPanel boardPanel;
    private final ControlPanel controlPanel;
    private GameData gameData;
    private DifficultyLevel difficultyLevel = DifficultyLevel.EASY;

    public GameWindow() {

        // MAIN PANEL - background
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // BOARD, CONTROL + TIMER PANELS
        controlPanel = new ControlPanel(this);
        boardPanel = new BoardPanel();
        timerPanel = new TimerPanel();
        gameData = new GameData(boardPanel, timerPanel, difficultyLevel);
        boardPanel.updateData(gameData);
        try {
            InputStream iconStream = getClass().getClassLoader().getResourceAsStream("tileIcons/flag.png");
            if (iconStream != null) {
                Image icon = ImageIO.read(iconStream);
                this.setIconImage(icon);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        initialize();
    }

    /* Set up the frame */
    private void initialize() {
        mainPanel.add(timerPanel);
        mainPanel.add(boardPanel);
        mainPanel.add(controlPanel);
        getContentPane().add(mainPanel);
    }

    /* Trigger creation of a new game with current difficult */
    public void reset() {
        gameData = new GameData(boardPanel, timerPanel, difficultyLevel);
        boardPanel.updateData(gameData);
        pack();
    }

    /* Trigger creation of a new game with increased difficulty level */
    public void increaseDifficultyLevel() {
        difficultyLevel = difficultyLevel == DifficultyLevel.HARD ?
                DifficultyLevel.EASY :
                DifficultyLevel.values()[difficultyLevel.ordinal() + 1];
        reset();
    }
}
