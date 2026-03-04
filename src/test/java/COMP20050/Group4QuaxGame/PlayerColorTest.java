package COMP20050.Group4QuaxGame;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerColorTest
{
    // ---------------------------  switchToFXColor tests ----------------------------------

    // test black player color returns javaFX black
    @Test
    void switchToFXColorBlack()
    {
        PlayerColor playerColor = PlayerColor.BLACK;
        assertEquals(Color.BLACK, playerColor.switchToFXColor());
    }

    // test white player color returns javaFX white
    @Test
    void switchToFXColorWhite()
    {
        PlayerColor playerColor = PlayerColor.WHITE;
        assertEquals(Color.WHITE, playerColor.switchToFXColor());
    }

    // test empty player color returns null
    @Test
    void switchToFXColorEmpty()
    {
        PlayerColor playerColor = PlayerColor.EMPTY;
        assertNull(playerColor.switchToFXColor());
    }


    // ---------------------------  getDisplayName tests ----------------------------------

    // test black player color returns string "BLACK"
    @Test
    void getDisplayNameBlack()
    {
        PlayerColor playerColor = PlayerColor.BLACK;
        assertEquals("BLACK", playerColor.getDisplayName());
    }

    // test white player color returns string "WHITE"
    @Test
    void getDisplayNameWhite()
    {
        PlayerColor playerColor = PlayerColor.WHITE;
        assertEquals("WHITE", playerColor.getDisplayName());
    }

    // test empty player color returns string "EMPTY"
    @Test
    void getDisplayNameEmpty()
    {
        PlayerColor playerColor = PlayerColor.EMPTY;
        assertEquals("EMPTY", playerColor.getDisplayName());
    }

}