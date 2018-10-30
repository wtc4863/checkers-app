package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JumpMoveTest {

    //
    // Constants
    //

    //
    // Attributes
    //
    private Position start;
    private Position middle;
    private Position validEnd;
    private Position invalidEnd;
    private Game game;
    private Board board;

    //
    // Setup
    //
    @BeforeEach
    public void setUp() {
        // Set up positions
        start = mock(Position.class);
        when(start.getRow()).thenReturn(0);
        when(start.getCell()).thenReturn(0);
        middle = mock(Position.class);
        when(middle.getRow()).thenReturn(1);
        when(middle.getCell()).thenReturn(1);
        validEnd = mock(Position.class);
        when(validEnd.getRow()).thenReturn(2);
        when(validEnd.getCell()).thenReturn(2);
        invalidEnd = mock(Position.class);
        when(invalidEnd.getRow()).thenReturn(4);
        when(invalidEnd.getCell()).thenReturn(4);

        // Create game
        game = mock(Game.class);
        board = mock(Board.class);
        when(game.getBoard()).thenReturn(board);

        // Create start space
        Space startSpace = mock(Space.class);
        when(startSpace.doesHasPiece()).thenReturn(true);
        when(startSpace.pieceInfo()).thenReturn(new Piece(Piece.PColor.red, Piece.PType.single));
        when(board.getSpace(start)).thenReturn(startSpace);
    }

    //
    // Tests
    //

    /**
     * Makes sure the constructor correctly calculates the position of the
     * piece that is being jumped.
     */
    @Test
    public void testMiddleCalculation() {
        JumpMove CuT = new JumpMove(start, validEnd);
        assertEquals(CuT.middle.row, 1, "Row of jumped position calculated incorrectly");
        assertEquals(CuT.middle.cell, 1, "Column of jumped position calculated incorrectly");
    }

    /**
     * Makes sure that a JumpMove with incorrect spacing does not pass the
     * validation check.
     */
    @Test
    public void testInvalidSpacingValidation() {
        JumpMove CuT = new JumpMove(start, invalidEnd);
        assertFalse(CuT.validateMove(game), "An invalidly-spaced move was returned as valid");
    }

    /**
     * Make sure that a jump move is not considered valid if the move will end
     * on an invalid space. This would only happen if the space was already
     * occupied.
     */
    @Test
    public void testEndSpaceIsInvalid() {
        JumpMove CuT = new JumpMove(start, validEnd);

        // Add a space that appears to have a piece on it to the destination on
        // the board
        Space fullSpace = mock(Space.class);
        when(fullSpace.isValid()).thenReturn(false);

        assertFalse(CuT.validateMove(game), "A move that would end on a full or white space was returned as valid");
    }

    /**
     * Make sure that a jump move that would end out of the bounds of the
     * checkers board is not considered valid, but also that the exception is
     * properly handled.
     */
    @Test
    public void testOutOfBoundsEndPosition() {
        // Set up the OOB position
        Position outOfBounds = mock(Position.class);
        when(outOfBounds.getRow()).thenReturn(9);
        when(outOfBounds.getCell()).thenReturn(9);
        when(board.spaceIsValid(outOfBounds)).thenThrow(new IndexOutOfBoundsException());

        JumpMove CuT = new JumpMove(start, outOfBounds);

        assertFalse(CuT.validateMove(game), "A move that would end outside of the board was returned as valid");
    }

    /**
     * Make sure that a jump move that doesn't actually jump over a piece is
     * not considered valid.
     */
    @Test
    public void testNoPieceInMiddleSpace() {
        // Make sure the destination space looks valid
        when(board.spaceIsValid(validEnd)).thenReturn(true);

        // Set up the middle piece
        Space middleSpace = mock(Space.class);
        when(middleSpace.doesHasPiece()).thenReturn(false);
        when(board.getSpace(middle)).thenReturn(middleSpace);

        JumpMove CuT = new JumpMove(start, middle, validEnd);

        assertFalse(CuT.validateMove(game), "A jump move that jumps over an empty space was returned as valid");
    }

    /**
     * Make sure that a jump move that jumps one of your own pieces is not
     * considered valid.
     */
    /*
    @Test
    public void testOwnPieceInMiddleSpace() {

    }
    */
}
