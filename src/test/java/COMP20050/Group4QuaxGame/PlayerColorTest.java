package COMP20050.Group4QuaxGame;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerColourTest
{
    // ---------------------------  switchToFXColour tests ----------------------------------

    // test black player colour returns javaFX black
    @Test
    void switchToFXColourBlack()
    {
        PlayerColour playerColour = PlayerColour.BLACK;
        assertEquals(Color.BLACK, playerColour.switchToFXColour());
    }

    // test white player colour returns javaFX white
    @Test
    void switchToFXColourWhite()
    {
        PlayerColour playerColour = PlayerColour.WHITE;
        assertEquals(Color.WHITE, playerColour.switchToFXColour());
    }

    // test empty player colour returns null
    @Test
    void switchToFXColourEmpty()
    {
        PlayerColour playerColour = PlayerColour.EMPTY;
        assertNull(playerColour.switchToFXColour());
    }


    // ---------------------------  getDisplayName tests ----------------------------------

    // test black player colour returns string "BLACK"
    @Test
    void getDisplayNameBlack()
    {
        PlayerColour playerColour = PlayerColour.BLACK;
        assertEquals("BLACK", playerColour.getDisplayName());
    }

    // test white player colour returns string "WHITE"
    @Test
    void getDisplayNameWhite()
    {
        PlayerColour playerColour = PlayerColour.WHITE;
        assertEquals("WHITE", playerColour.getDisplayName());
    }

    // test empty player colour returns string "EMPTY"
    @Test
    void getDisplayNameEmpty()
    {
        PlayerColour playerColour = PlayerColour.EMPTY;
        assertEquals("EMPTY", playerColour.getDisplayName());
    }

}