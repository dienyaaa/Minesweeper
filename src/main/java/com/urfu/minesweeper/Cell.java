package com.urfu.minesweeper;
/*
Cell is the lowest level of minesweeper. Each cell is an extended JButton
that also holds state information such as clicked/unclicked etc.
*/

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Cell extends JButton {

    GameData data;

    private boolean isLonely;
    private boolean isFlagged;
    private boolean isMine;

    private int countOfDangerousNeighbours;

    public Cell(GameData gameData) {

        data = gameData;
        isFlagged = false;
        isMine = false;
        setEnabled(true);

        setIcon(Icons.get("unclicked"));

        // Use a mouseListener because we want right click as well
        MouseListener clicker = new MouseListener() {

            public void mouseClicked(MouseEvent mouseEvent) {
                if (isEnabled()) {
                    if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                        leftClick();
                    } else if (SwingUtilities.isRightMouseButton(mouseEvent)) {
                        toggleFlag();
                    }
                }
            }

            // Other methods that have to be included in the listener
            public void mouseEntered(MouseEvent mouseEvent) {
            }

            public void mouseExited(MouseEvent mouseEvent) {
            }

            public void mousePressed(MouseEvent mouseEvent) {
            }

            public void mouseReleased(MouseEvent mouseEvent) {
            }

        };
        addMouseListener(clicker);
    }

    public void setMine() {
        isMine = true;
    }

    public boolean isMine() {
        return isMine;
    }

    private void updateIcon(String iconName) {
        setIcon(Icons.get(iconName));
        setDisabledIcon(Icons.get(iconName));
    }

    private void toggleFlag() {
        if (isFlagged) {
            isFlagged = false;
            updateIcon("unclicked");
        } else {
            isFlagged = true;
            updateIcon("flag");
        }

        data.endIfGameWon();
    }

    private void leftClick() {
        data.inProgress();
        boolean autoCleared = false;
        setEnabled(false);

        if (isMine) {
            data.lose();
        } else {
            reveal();
        }

        while (!autoCleared) {
            autoCleared = data.autoRevealMap();
        }

        data.endIfGameWon();
    }

    public void reveal() {
        setEnabled(false);

        if (isMine()) {
            updateIcon("mine");
        } else {
            String countOfDangerousNeighbours = String.valueOf(getCountOfDangerousNeighbours());
            updateIcon(countOfDangerousNeighbours);
        }
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setLonely() {
        isLonely = true;
    }

    public boolean isLonely() {
        return isLonely;
    }

    public int getCountOfDangerousNeighbours() {
        return countOfDangerousNeighbours;
    }

    public void setCountOfDangerousNeighbours(int countOfDangerousNeighbours) {
        this.countOfDangerousNeighbours = countOfDangerousNeighbours;
    }
}
