package com.urfu.minesweeper;
/*
BoardData contains all data attached to a single game of minesweeper. 
That is, level of difficulty, current mine map etc.
*/

import javax.swing.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

class GameData {
    class Tick extends TimerTask {
        public void run() {
            time++;
            timerPanel.refresh();
        }
    }

    private final Timer timer;
    protected int time = 0;
    private boolean inProgress;
    private final Cell[][] map;
    private int boardSize;
    private int mines;
    private final BoardPanel boardPanel;
    private final TimerPanel timerPanel;

    public GameData(BoardPanel boardPanel, TimerPanel timerPanel, DifficultyLevel difficultyLevel) {
        this.timerPanel = timerPanel;
        this.timerPanel.updateData(this);

        this.boardPanel = boardPanel;

        timer = new Timer();
        inProgress = false;
        setDifficulty(difficultyLevel);

        map = new Cell[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                map[i][j] = new Cell(this);
            }
        }
        generateMines(mines);
        setMineCounts();
    }

    public void inProgress() {
        if (!inProgress) {
            inProgress = true;
            timer.schedule(new Tick(), 0, 1000);
        }
    }

    private void setDifficulty(DifficultyLevel difficultyLevel) {
        boardSize = difficultyLevel.getBoardSize();
        mines = difficultyLevel.getMines();
    }

    private void generateMines(int number) {
        Random random = new Random();
        for (int i = 0; i < number; i++) {
            int randomX = random.nextInt(boardSize);
            int randomY = random.nextInt(boardSize);
            map[randomX][randomY].setMine();
        }
    }

    private void setMineCounts() {
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                map[x][y].setCountOfDangerousNeighbours(countDangerousNeighbours(x, y));
            }
        }
    }

    private int countDangerousNeighbours(int x, int y) {
        int countDangerousNeighbours = 0;
        for (int neighbourX = x - 1; neighbourX <= x + 1; neighbourX++) {
            for (int neighbourY = y - 1; neighbourY <= y + 1; neighbourY++) {
                if ((neighbourX != x || neighbourY != y) && inBounds(neighbourX, neighbourY) && map[neighbourX][neighbourY].isMine()) {
                    countDangerousNeighbours++;
                }
            }
        }
        return countDangerousNeighbours;
    }

    private boolean inBounds(int i, int j) {
        return i < boardSize && i >= 0 && j < boardSize && j >= 0;
    }

    protected boolean autoRevealMap() {
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                if (!map[x][y].isEnabled()
                        && map[x][y].getCountOfDangerousNeighbours() == 0
                        && !map[x][y].isLonely()
                        && !map[x][y].isMine()) {
                    autoReveal(x, y);
                    return false;
                }
            }
        }
        boardPanel.repaint();
        return true;
    }


    // Automatically clear those neighbours
    private void autoReveal(int x, int y) {
        map[x][y].setLonely();
        for (int neighbourX = x - 1; neighbourX <= x + 1; neighbourX++) {
            for (int neighbourY = y - 1; neighbourY <= y + 1; neighbourY++) {
                if (inBounds(neighbourX, neighbourY)) {
                    map[neighbourX][neighbourY].reveal();
                }
            }
        }
    }

    // Check to see if every mine has been flagged correctly
    protected void endIfGameWon() {
        if (isGameWon()) {
            timer.cancel();
            timer.purge();
            disableAll();
            JOptionPane.showMessageDialog(null, "You WIN!");
        }
    }

    private boolean isGameWon() {
        boolean gameWon = true;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Cell cell = map[i][j];
                if (cell.isMine() && !cell.isFlagged() || !cell.isMine() && cell.isFlagged()) {
                    gameWon = false;
                    break;
                }
            }
        }
        return gameWon;
    }

    protected void lose() {
        revealAll();
        stopTimer();
    }

    private void disableAll() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                map[i][j].setEnabled(false);
            }
        }
    }

    private void revealAll() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                map[i][j].reveal();
            }
        }
        boardPanel.repaint();
    }

    public int getBoardSize() {
        return boardSize;
    }

    public Cell getCell(int i, int j) {
        return map[i][j];
    }

    public void stopTimer() {
        timer.cancel();
        timer.purge();
    }
}
