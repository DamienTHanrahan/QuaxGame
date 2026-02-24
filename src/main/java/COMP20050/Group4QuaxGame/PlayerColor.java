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
        return switch(this)
        {
            case BLACK -> Color.BLACK;
            case WHITE -> Color.WHITE;
            default -> Color.ORANGE;
        };
    }

    public String getDisplayName()
    {
        return this.name();
    }
}
