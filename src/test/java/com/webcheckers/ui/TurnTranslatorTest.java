package com.webcheckers.ui;

import com.webcheckers.model.Position;
import com.webcheckers.model.Move;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.mockito.Mockito;

@Tag("UI-Tier")
public class TurnTranslatorTest {

    // Component Under Test
    private TurnTranslator CuT;

    // Friendly Objects

    // Mocked Objects

    @BeforeEach
    public void setup() {
        CuT = new TurnTranslator();
    }

    @AfterEach
    public void tearDown() {
        CuT = null;
    }

    @Test
    public void testFromUItoModel() {
    }


}
