package COMP20050.Group4QuaxGame;

import javafx.scene.shape.Polygon;

public class Move
{
    // move consists of the player making the move and the tile they picked
    private final Player player;
    private final Polygon tile;
    private final Node node;

    public Move(Player player, Polygon tile, Node node)
    {
        this.player = player;
        this.tile = tile;
        this.node = node;
    }

    // getters for the player and the tile
    public Player getPlayer()
    {
        return player;
    }

    public Polygon getTile()
    {
        return tile;
    }

    public Node getNode() {
        return node;
    }
}