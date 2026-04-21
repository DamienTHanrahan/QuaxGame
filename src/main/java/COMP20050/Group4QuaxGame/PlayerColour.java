package COMP20050.Group4QuaxGame;

import javafx.scene.paint.Color;

public enum PlayerColour
{
    EMPTY,
    BLACK,
    WHITE;

    // switch the colour to match the javaFX colour
    public Color switchToFXColour()
    {
        Color fxColour;

        switch(this)
        {
            case BLACK -> fxColour = Color.BLACK;
            case WHITE -> fxColour = Color.WHITE;
            default -> fxColour = null;
        }

        return fxColour;
    }

    // get name of the enum for printing on turn label
    public String getDisplayName()
    {
        return this.name();
    }
}
