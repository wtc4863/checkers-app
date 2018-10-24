package com.webcheckers.model;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")

public class BoardTest {

    Board board = new Board();

    @Test
    public void testConstructor() {
        assertTrue(board.boardArray[7][0].isBlack(), "Bottom-left space should be black");
        assertTrue(board.boardArray[0][7].isBlack(), "Top-right space should be black");
    }

    @Test
    public void testMoveFromEmptySpace() {
        board.move(board.boardArray[4][0], board.boardArray[3][0]);
        assertFalse(board.boardArray[3][0].doesHasPiece());
    }

    @Test
    public void testBadMoveWhiteSpace() {
        board.move(board.boardArray[5][0], board.boardArray[4][0]);
        assertFalse(board.boardArray[4][0].doesHasPiece());
        assertTrue(board.boardArray[5][0].doesHasPiece());
    }

    @Test
    public void testBadMoveTakenSpace() {
        board.move(board.boardArray[5][2], board.boardArray[6][3]);
        assertTrue(board.boardArray[5][2].doesHasPiece());
    }

    @Test
    public void testGoodMove() {
        board.move(board.boardArray[5][4], board.boardArray[4][5]);
        assertTrue(board.boardArray[4][5].doesHasPiece());
        assertFalse(board.boardArray[5][4].doesHasPiece());
    }
}
