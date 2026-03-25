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

        // add tile to map
        tiles.put("OctA01", tile);

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

        // add tile to map
        tiles.put("OctA01", tile);

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

        // add tile to map
        tiles.put("OctA01", tile);

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

        // add tile to map
        tiles.put("OctA01", tile);

        // move 1
        Move validMove = gameManager.humanMove(tile);
        assertNotNull(validMove);

        // tile 2
        Polygon tile2 = new Polygon();
        tile2.setFill(Color.ORANGE);
        // add tile2 to map
        tiles.put("OctA02", tile2);

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
        // add tile to map
        tiles.put("OctA01", tile);

        // player move 1
        gameManager.humanMove(tile);

        // make tile for bot to move to
        Polygon botTile = new Polygon();
        botTile.setFill(Color.ORANGE);
        tiles.put("OctA02", botTile);

        // bot move
        gameManager.botMove();

        // tile 2
        Polygon tile2 = new Polygon();
        tile2.setFill(Color.ORANGE);
        // add tile to map
        tiles.put("OctA03", tile2);

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


    // --------------------- setUpGame tests --------------------------

    // check variables after we set up game with human starting
    @Test
    void setUpGameHumanStarts()
    {
        gameManager.setUpGame(gameManager.human);

        // check current player and other player are correctly assigned
        assertEquals(gameManager.human, gameManager.getCurrentPlayer());
        assertEquals(gameManager.bot, gameManager.otherPlayer);
    }

    // check variables after we set up game with bot starting
    @Test
    void setUpGameBotStarts()
    {
        gameManager.setUpGame(gameManager.bot);

        // check current player and other player are correctly assigned
        assertEquals(gameManager.bot, gameManager.getCurrentPlayer());
        assertEquals(gameManager.human, gameManager.otherPlayer);
    }

    // check that player colors are assigned correct when human starts
    @Test
    void setUpGameHumanColor()
    {
        gameManager.setUpGame(gameManager.human);

        // first player should be black
        assertEquals(PlayerColor.BLACK, gameManager.currentPlayer.getPlayerColor());
        // second player should be white
        assertEquals(PlayerColor.WHITE, gameManager.otherPlayer.getPlayerColor());
    }

    // check that player colors are assigned correct when bot starts
    @Test
    void setUpGameBotColor()
    {
        gameManager.setUpGame(gameManager.bot);

        // first player should be black
        assertEquals(PlayerColor.BLACK, gameManager.currentPlayer.getPlayerColor());
        // second player should be white
        assertEquals(PlayerColor.WHITE, gameManager.otherPlayer.getPlayerColor());
    }


    // --------------------------- getCurrentPlayer test ----------------------------

    // check if current player can be bot
    @Test
    void getCurrentPlayerHuman()
    {
        gameManager.currentPlayer = gameManager.human;

        assertEquals(gameManager.human, gameManager.getCurrentPlayer());
    }

    @Test
    void getCurrentPlayerBot()
    {
        gameManager.currentPlayer = gameManager.bot;

        assertEquals(gameManager.bot, gameManager.getCurrentPlayer());
    }

    // check if current player swaps after move
    @Test
    void getCurrentPlayerAfterMove()
    {
        gameManager.currentPlayer = gameManager.human;
        gameManager.otherPlayer = gameManager.bot;

        // set tile to empty
        tile.setFill(Color.ORANGE);
        // add tile to map
        tiles.put("OctA01", tile);
        // make valid move
        gameManager.humanMove(tile);

        assertEquals(gameManager.bot, gameManager.getCurrentPlayer());
    }

    // check if current player doesn't change after an invalid move
    @Test
    void getCurrentPlayerBotAfterInvalidMove()
    {
        gameManager.currentPlayer = gameManager.human;
        gameManager.otherPlayer = gameManager.bot;

        // set tile to BLACK
        tile.setFill(Color.BLACK);
        // add tile to map
        tiles.put("OctA01", tile);
        // make an invalid move
        gameManager.humanMove(tile);

        assertEquals(gameManager.human, gameManager.getCurrentPlayer());
    }


    // -------------------------- getTurnCount tests ---------------------------------------

    // test if turn count starts at zero
    @Test
    void getTurnCountStartsAtZero()
    {
        assertEquals(0, gameManager.getTurnCount());
    }

    // check turn count increases after human moves
    @Test
    void getTurnCountAfterHumanMove()
    {
        // set human to current player
        gameManager.currentPlayer = gameManager.human;

        // set tile to empty
        tile.setFill(Color.ORANGE);
        // add tile to map
        tiles.put("OctA01", tile);
        // human move
        gameManager.humanMove(tile);

        assertEquals(1, gameManager.getTurnCount());
    }

    // check turn count increments after bot moves
    @Test
    void getTurnCountAfterBotMove()
    {
        // set bot to current player
        gameManager.currentPlayer = gameManager.bot;

        // set tile to empty
        tile.setFill(Color.ORANGE);
        tiles.put("OctA01", tile);

        // make bot move
        gameManager.botMove();

        assertEquals(1, gameManager.getTurnCount());
    }

    // check if turn count doesn't increment after an invalid move
    @Test
    void getTurnCountInvalidMove()
    {
        gameManager.currentPlayer = gameManager.human;

        // set tile to Black
        tile.setFill(Color.BLACK);
        // add tile to map
        tiles.put("OctA01", tile);
        gameManager.humanMove(tile);

        assertEquals(0, gameManager.getTurnCount());
    }


    // ----------------------- switchTurns test ----------------
    // NOTE Validation for switch turns tested above in getCurrentPlayer and getTurnCount test cases

    // check current player is bot after human moves
    @Test
    void switchTurnsAfterHumanMove()
    {
        gameManager.currentPlayer = gameManager.human;
        gameManager.otherPlayer = gameManager.bot;

        // simulate human move
        gameManager.switchTurns();

        assertEquals(gameManager.bot, gameManager.getCurrentPlayer());
    }

    // check current player is human after bot moves
    @Test
    void switchTurnsAfterBotMove()
    {
        gameManager.currentPlayer = gameManager.bot;
        gameManager.otherPlayer = gameManager.human;

        // simulate bot move
        gameManager.switchTurns();

        assertEquals(gameManager.human, gameManager.getCurrentPlayer());
    }

    // check switch turns method correctly increments turn count
    @Test
    void switchTurnsIncrementsTurnCount()
    {
        gameManager.currentPlayer = gameManager.bot;
        gameManager.otherPlayer = gameManager.human;

        // turn count starts at 0
        assertEquals(0, gameManager.getTurnCount());
        // simulate move
        gameManager.switchTurns();
        // check it incremented
        assertEquals(1, gameManager.getTurnCount());

    }


    // ----------------- activatePieRule tests ----------------------

    // check the colors swap correctly if pie rule used on humans turn
    @Test
    void activatePieRuleHuman()
    {
        gameManager.turnCount = 1;
        gameManager.currentPlayer = gameManager.human;
        gameManager.otherPlayer = gameManager.bot;
        // White player is always the player that gets to use pie rule
        gameManager.human.setPlayerColor(PlayerColor.WHITE);
        gameManager.bot.setPlayerColor(PlayerColor.BLACK);

        // call pie rule
        gameManager.activatePieRule(gameManager.human);

        // check colors updated
        assertEquals(PlayerColor.BLACK, gameManager.human.getPlayerColor());
        assertEquals(PlayerColor.WHITE, gameManager.bot.getPlayerColor());

    }

    // check the colors swap correctly if pie rule used on bots turn
    @Test
    void activatePieRuleBot()
    {
        gameManager.turnCount = 1;
        gameManager.currentPlayer = gameManager.bot;
        gameManager.otherPlayer = gameManager.human;
        // White player is always the player that gets to use pie rule
        gameManager.bot.setPlayerColor(PlayerColor.WHITE);
        gameManager.human.setPlayerColor(PlayerColor.BLACK);

        // call pie rule
        gameManager.activatePieRule(gameManager.bot);

        // check colors updated
        assertEquals(PlayerColor.BLACK, gameManager.bot.getPlayerColor());
        assertEquals(PlayerColor.WHITE, gameManager.human.getPlayerColor());

    }

    // check pie rule switches Turns after
    @Test
    void activatePieRuleSwitchesTurns()
    {
        gameManager.turnCount = 1;
        gameManager.currentPlayer = gameManager.bot;
        gameManager.otherPlayer = gameManager.human;

        gameManager.activatePieRule(gameManager.bot);

        assertEquals(gameManager.human, gameManager.getCurrentPlayer());
        assertEquals(gameManager.bot, gameManager.otherPlayer);
    }

    // check pie rule doesn't work if turn count isn't 1
    @Test
    void activatePieRuleNotOnCorrectTurn()
    {
        gameManager.turnCount = 5;
        gameManager.currentPlayer = gameManager.bot;
        gameManager.otherPlayer = gameManager.human;

        gameManager.activatePieRule(gameManager.bot);

        // check the current and other players are not changed
        assertEquals(gameManager.bot, gameManager.getCurrentPlayer());
        assertEquals(gameManager.human, gameManager.otherPlayer);
    }

    // check if pie rule doesn't work if the human uses it when not his turn
    @Test
    void humanActivatePieRuleNotOnHisTurn()
    {
        gameManager.turnCount = 1;
        gameManager.currentPlayer = gameManager.bot;
        gameManager.otherPlayer = gameManager.human;

        gameManager.activatePieRule(gameManager.human);

        // check the current and other players are not changed
        assertEquals(gameManager.bot, gameManager.getCurrentPlayer());
        assertEquals(gameManager.human, gameManager.otherPlayer);
    }

    // check if pie rule doesn't work if the human uses it when not his turn
    @Test
    void botActivatePieRuleNotOnHisTurn()
    {
        gameManager.turnCount = 1;
        gameManager.currentPlayer = gameManager.human;
        gameManager.otherPlayer = gameManager.bot;

        gameManager.activatePieRule(gameManager.bot);

        // check the current and other players are not changed
        assertEquals(gameManager.human, gameManager.getCurrentPlayer());
        assertEquals(gameManager.bot, gameManager.otherPlayer);
    }


    // ----------------------- validateMove test ----------------------

    // test if valid move works
    @Test
    void validateMoveValid()
    {
        // set tile to empty
        tile.setFill(Color.ORANGE);
        // add tile to map
        tiles.put("OctA01", tile);

        Move validMove = new Move(gameManager.human, tile);

        assertTrue(gameManager.validateMove(validMove));
    }

    // test if invalid move returns false
    @Test
    void validateMoveInvalid()
    {
        // set tile to White
        tile.setFill(Color.WHITE);
        // add tile to map
        tiles.put("OctA01", tile);

        Move invalidMove = new Move(gameManager.bot, tile);

        assertFalse(gameManager.validateMove(invalidMove));
    }

    // test if no tile returns false
    @Test
    void validateMoveNullTile()
    {
        Move nullTileMove = new Move(gameManager.human, null);

        assertFalse(gameManager.validateMove(nullTileMove));
    }

    // test if no player returns false
    @Test
    void validateMoveNullPlayer()
    {
        tile.setFill(Color.ORANGE);
        // add tile to map
        tiles.put("OctA01", tile);

        Move nullPlayerMove = new Move(null, tile);

        assertFalse(gameManager.validateMove(nullPlayerMove));
    }



    // ----------------------- isGameOver test ----------------------

    // test if game is over on black winning move
    @Test
    void isGameOverOnBlackWinningMove()
    {
        // TODO
    }

    // test if game is over on white winning move
    @Test
    void isGameOverOnWhiteWinningMove()
    {
        // TODO
    }

    // test if game is not over on a non-winning move
    @Test
    void isGameOverNonWinningMove()
    {
        // TODO
    }

    // test if game is not over on empty board
    @Test
    void isGameOverEmptyBoard()
    {
        // add tile to map
        tiles.put("OctA01", tile);

        // set tile color to be orange to pretend it's empty (i.e. not black or white)
        tile.setFill(Color.ORANGE);

        assertFalse(gameManager.isGameOver());
    }

    // test if game is over on full board
    @Test
    void isGameOverFullBoard()
    {
        // add tile to map
        tiles.put("OctA01", tile);

        // set tile color to be black to pretend the board in this case 1 tile is full
        tile.setFill(Color.BLACK);

        assertTrue(gameManager.isGameOver());
    }

    // test if human is unable to make a valid move on game over
    @Test
    void humanTriesMoveOnGameOver()
    {
        // set gameOver to true to simulate scenario
        gameManager.gameOver = true;

        // set human to current player
        gameManager.currentPlayer = gameManager.human;

        // add tile to map
        tiles.put("OctA01", tile);

        // set tile color to be orange to pretend it's empty (i.e. not black or white)
        tile.setFill(Color.ORANGE);

        Move invalidMove = gameManager.humanMove(tile);
        assertNull(invalidMove);
    }

    // test if bot is unable to make a valid move on game over
    @Test
    void botTriesMoveOnGameOver()
    {
        // set gameOver to true to simulate scenario
        gameManager.gameOver = true;

        // set human to current player
        gameManager.currentPlayer = gameManager.bot;

        // add tile to map
        tiles.put("OctA01", tile);

        // set tile color to be orange to pretend it's empty (i.e. not black or white)
        tile.setFill(Color.ORANGE);

        Move invalidMove = gameManager.botMove();
        assertNull(invalidMove);
    }


    // ----------------------- resetGame tests ----------------------

    // test resetting game works once game is over
    @Test
    void resetGameOnGameOver()
    {
        // simulate game is over
        gameManager.gameOver = true;

        assertTrue(gameManager.resetGame());
    }

    // test resetting game not allowed if game is not over
    @Test
    void resetGameNotFinished()
    {
        // simulate game still ongoing
        gameManager.gameOver = false;
        tiles.put("OctA01", tile);
        tile.setFill(Color.ORANGE);

        assertFalse(gameManager.resetGame());
    }

    // test if turn count resets
    @Test
    void resetGameTurnCount()
    {
        gameManager.turnCount = 5;
        gameManager.gameOver = true;

        gameManager.resetGame();

        assertEquals(0, gameManager.turnCount);
    }

    // test if pie rule used boolean resets
    @Test
    void resetGamePieRuleUsed()
    {
        gameManager.pieRuleUsed = true;
        gameManager.gameOver = true;

        gameManager.resetGame();

        assertFalse(gameManager.pieRuleUsed);
    }

    // test if game over boolean resets
    @Test
    void resetGameGameOverBoolean()
    {
        gameManager.gameOver = true;

        gameManager.resetGame();

        assertFalse(gameManager.gameOver);
    }

}