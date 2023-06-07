package com.urfu.minesweeper;

public enum DifficultyLevel {
    EASY(10, 10),
    MEDIUM(15, 30),
    HARD(20, 60);

    private final int boardSize;
    private final int mines;

    DifficultyLevel(int boardSize, int mines) {
        this.boardSize = boardSize;
        this.mines = mines;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getMines() {
        return mines;
    }
}
