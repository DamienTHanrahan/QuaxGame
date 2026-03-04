package COMP20050.Group4QuaxGame;

import javafx.scene.paint.Color;

public enum PlayerColor
{
    EMPTY,
    BLACK,
    WHITE;

    // switch the color to match the javaFX color
    public Color switchToFXColor()
    {
        Color fxColor;

        switch(this)
        {
            case BLACK -> fxColor = Color.BLACK;
            case WHITE -> fxColor = Color.WHITE;
            default -> fxColor = null;
        }

        return fxColor;
    }

    // get name of the enum for printing on turn label
    public String getDisplayName()
    {
        return this.name();
    }
}
