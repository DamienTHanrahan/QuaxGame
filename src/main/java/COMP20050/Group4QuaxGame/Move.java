package COMP20050.Group4QuaxGame;

import javafx.scene.shape.Polygon;

public class Move
{
    private final Player player;
    private final Polygon tile;

    public Move(Player player, Polygon tile)
    {
        this.player = player;
        this.tile = tile;
    }

    public Player getPlayer()
    {
        return player;
    }

    public Polygon getTile()
    {
        return tile;
    }
}