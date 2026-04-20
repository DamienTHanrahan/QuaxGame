package COMP20050.Group4QuaxGame;

import javafx.scene.paint.Colour;

public enum PlayerColour
{
    EMPTY,
    BLACK,
    WHITE;

    // switch the colour to match the javaFX colour
    public Colour switchToFXColour()
    {
        Colour fxColour;

        switch(this)
        {
            case BLACK -> fxColour = Colour.BLACK;
            case WHITE -> fxColour = Colour.WHITE;
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
