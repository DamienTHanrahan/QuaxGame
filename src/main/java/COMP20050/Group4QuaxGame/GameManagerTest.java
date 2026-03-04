package COMP20050.Group4QuaxGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static COMP20050.Group4QuaxGame.BoardController.tiles;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    private final GameManager gameManager = new GameManager();

    // demo tile for testing purposes
    private Polygon tile = new Polygon();


    // clear the tiles hash map before each
    @BeforeEach
    void clearTiles()
    {
        tiles.clear();
    }


    // -------------------- humanMove function TEST -------------------------
    // test if player can move on an empty tile
    @Test
    void humanMoveOnEmptyTile()
    {
        // set human to current player
        gameManager.currentPlayer = gameManager.human;

        // set tile color to be orange to pretend it's empty (i.e. not black or white)
        tile.setFill(Color.ORANGE);

        Move validMove = gameManager.humanMove(tile);
        assertNotNull(validMove);
    }

    // test if player can move on a tile already owned by black
    @Test
    void humanMoveOnBlackTile()
    {
        // set human to current player
        gameManager.currentPlayer = gameManager.human;

        // set tile to black to show owned by black
        tile.setFill(Color.BLACK);

        Move invalidMove = gameManager.humanMove(tile);
        assertNull(invalidMove);
    }

    // test if player can move on a tile already owned by white
    @Test
    void humanMoveOnWhiteTile()
    {
        // set human to current player
        gameManager.currentPlayer = gameManager.human;

        // set tile to black to show owned by white
        tile.setFill(Color.WHITE);

        Move invalidMove = gameManager.humanMove(tile);
        assertNull(invalidMove);
    }

    // test if the player is able to move twice in a row
    @Test
    void humanMoveTwiceBeforeBot()
    {
        // set human to current player
        gameManager.currentPlayer = gameManager.human;

        // set tile color to be orange to pretend it's empty (i.e. not black or white)
        tile.setFill(Color.ORANGE);

        // move 1
        Move validMove = gameManager.humanMove(tile);
        assertNotNull(validMove);

        // tile 2
        Polygon tile2 = new Polygon();
        tile2.setFill(Color.ORANGE);

        // move 2
        Move invalidMove = gameManager.humanMove(tile2);
        assertNull(invalidMove);
    }

    // test to check if player can move after bot
    @Test
    void humanMoveAfterBot()
    {
        // set human to current player
        gameManager.currentPlayer = gameManager.human;
        // bot to the other player
        gameManager.otherPlayer = gameManager.bot;

        // set tile color to be orange to pretend it's empty (i.e. not black or white)
        tile.setFill(Color.ORANGE);

        // player move 1
        gameManager.humanMove(tile);

        // make tile for bot to move to
        Polygon botTile = new Polygon();
        botTile.setFill(Color.ORANGE);
        tiles.put("OctA01", botTile);

        // bot move
        gameManager.botMove();

        // tile 2
        Polygon tile2 = new Polygon();
        tile2.setFill(Color.ORANGE);

        // player move 2
        assertNotNull(gameManager.humanMove(tile2));
    }



    // -------------------- botMove function TEST -------------------------
    // test if botMove with an empty board
    @Test
    void botMoveEmptyBoard()
    {
        // set bot to current player
        gameManager.currentPlayer = gameManager.bot;

        // create tiles
        Polygon tile2 = new Polygon();

        // set both to empty
        tile.setFill(Color.ORANGE);
        tile2.setFill(Color.ORANGE);

        // add to hashmap
        tiles.put("OctA01", tile);
        tiles.put("OctA02", tile2);

        // get bots move
        Move botMove = gameManager.botMove();

        // check it picks any of the tiles
        assertTrue(botMove.getTile().equals(tile) || botMove.getTile().equals(tile2));
    }

    // test if botMove always selects an unoccupied tile
    @Test
    void botMoveUnoccupied()
    {
        // set bot to current player
        gameManager.currentPlayer = gameManager.bot;

        // create tiles
        Polygon tile2 = new Polygon();
        Polygon tile3 = new Polygon();

        // set 2 to black and 1 to empty
        tile.setFill(Color.BLACK);
        tile2.setFill(Color.BLACK);
        tile3.setFill(Color.ORANGE);

        // add to hashmap
        tiles.put("OctA01", tile);
        tiles.put("OctA02", tile2);
        tiles.put("OctA03", tile3);

        // get bots move
        Move botMove = gameManager.botMove();

        assertEquals(tile3, botMove.getTile());
    }

    // test that the bot returns null if there is no available tiles left
    @Test
    void botMoveFullBoard()
    {
        // set bot to current player
        gameManager.currentPlayer = gameManager.bot;

        // create tiles
        Polygon tile2 = new Polygon();
        Polygon tile3 = new Polygon();

        // set tiles to Black
        tile.setFill(Color.BLACK);
        tile2.setFill(Color.BLACK);
        tile3.setFill(Color.BLACK);

        // add to tiles hashmap
        tiles.put("OctA01", tile);
        tiles.put("OctA02", tile2);
        tiles.put("OctA03", tile3);

        // get bots move
        Move botMove = gameManager.botMove();

        assertNull(botMove);
    }



    // -------------------- isBoardFull function TEST -------------------------
    // test if an empty board returns false
    @Test
    void isBoardFullEmptyBoard()
    {
        // create tiles
        Polygon tile2 = new Polygon();
        Polygon tile3 = new Polygon();

        // set tiles to empty
        tile.setFill(Color.ORANGE);
        tile2.setFill(Color.ORANGE);
        tile3.setFill(Color.ORANGE);

        // add to tiles hashmap
        tiles.put("OctA01", tile);
        tiles.put("OctA02", tile2);
        tiles.put("OctA03", tile3);

        assertFalse(gameManager.isBoardFull());
    }

    // test if board with some but not all tiles filled returns false
    @Test
    void isBoardFullMixedBoard()
    {
        // create 3 tiles
        Polygon tile2 = new Polygon();
        tile2.setFill(Color.ORANGE);
        Polygon tile3 = new Polygon();
        tile3.setFill(Color.ORANGE);

        // set one tile to black
        tile.setFill(Color.BLACK);

        // add to tiles hashmap
        tiles.put("OctA01", tile);
        tiles.put("OctA02", tile2);
        tiles.put("OctA03", tile3);

        assertFalse(gameManager.isBoardFull());
    }

    // test if a fully filled board returns true
    @Test
    void isBoardFullFilledBoard()
    {
        // create tiles
        Polygon tile2 = new Polygon();
        Polygon tile3 = new Polygon();

        // set tiles to Black
        tile.setFill(Color.BLACK);
        tile2.setFill(Color.BLACK);
        tile3.setFill(Color.BLACK);

        // add to tiles hashmap
        tiles.put("OctA01", tile);
        tiles.put("OctA02", tile2);
        tiles.put("OctA03", tile3);

        assertTrue(gameManager.isBoardFull());
    }


}