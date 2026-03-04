package COMP20050.Group4QuaxGame;

import javafx.scene.shape.Polygon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest
{
    Human human = new Human(PlayerColor.BLACK);
    Bot bot = new Bot(PlayerColor.WHITE);

    Polygon tile = new Polygon();


    // ----------------- getPlayer tests -----------------

    // get player from a human move
    @Test
    void getPlayerHuman()
    {
        Move humanMove = new Move(human, tile);

        assertEquals(human, humanMove.getPlayer());
    }

    // get player from a bot move
    @Test
    void getPlayerBot()
    {
        Move botMove = new Move(bot, tile);

        assertEquals(bot, botMove.getPlayer());
    }

    // ----------------- getTile tests -----------------

    // get tile from a move
    @Test
    void getTile()
    {
        Move move = new Move(human, tile);

        assertEquals(tile, move.getTile());
    }
}