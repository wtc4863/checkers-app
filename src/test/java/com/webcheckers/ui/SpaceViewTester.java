package com.webcheckers.ui;

import com.webcheckers.model.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SpaceViewTester {

    @BeforeEach
    public void testSetup() {
        // TODO this may not be needed
    }

    /**
     * Test the constructor to make sure white spaces will never have pieces on
     * them.
     */
    @Test
    public void should_notHavePiece_when_isWhiteSpace() {
        // TODO implement
    }

    /**
     * Test that white spaces will be reported as invalid.
     */
    @Test
    public void should_beInvalid_when_isWhiteSpace() {
        // TODO implement
    }

    /**
     * Test that spaces that have pieces on them will be reported as invalid.
     */
    @Test
    public void should_beInvalid_when_hasPiece() {
        // TODO implement
    }

    /**
     * Test that spaces without pieces that are black will be reported as
     * valid.
     */
    @Test
    public void should_beValid_when_blackSpaceWithoutPiece() {
        // TODO implement
    }

    /**
     * Test that spaces without pieces will return a null PieceView.
     */
    @Test
    public void should_returnNullPieceView_when_hasNoPiece() {
        // TODO implement
    }

    /**
     * Test that the cell index getter returns the correct value.
     */
    @Test
    public void should_returnIndexInRow_when_getCellIdx() {
        // TODO implement
    }
}
