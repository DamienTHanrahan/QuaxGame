package COMP20050.Group4QuaxGame;

import javafx.scene.paint.Colour;
import javafx.scene.shape.Polygon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static COMP20050.Group4QuaxGame.BoardController.tiles;

import static org.junit.jupiter.api.Assertions.*;

class GameManagerTest {

    private GameManager gameManager;


    // clear all tiles and node owners each test
    @BeforeEach
    void clearTiles()
    {
        gameManager = new GameManager();
        tiles.clear();
        gameManager.setBotEnabled(true);
    }

    // helper function for creating fake tiles to demo
    private Polygon createTile(String id, PlayerColour playerColour)
    {
        Polygon tile = new Polygon();
        tile.setId(id);
        tiles.put(id, tile);
        gameManager.board.getNode(id).setOwner(playerColour);
        return tile;
    }


    // -------------------- humanMove function TEST -------------------------
    // test if player can move on an empty tile
    @Test
    void humanMoveOnEmptyTile()
    {
        // set human to current player
        gameManager.currentPlayer = gameManager.human;

        // add empty tile
        Polygon tile = createTile("OctA01", PlayerColour.EMPTY);

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
        Polygon tile = createTile("OctA01", PlayerColour.BLACK);

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
        Polygon tile = createTile("OctA01", PlayerColour.WHITE);

        Move invalidMove = gameManager.humanMove(tile);
        assertNull(invalidMove);
    }

    // test if the player is able to move twice in a row
    @Test
    void humanMoveTwiceBeforeBot()
    {
        // set human to current player
        gameManager.currentPlayer = gameManager.human;

        // add tile to map
        Polygon tile = createTile("OctA01", PlayerColour.EMPTY);

        // move 1
        Move validMove = gameManager.humanMove(tile);
        assertNotNull(validMove);

        // add tile2 to map
        Polygon tile2 = createTile("OctA02", PlayerColour.EMPTY);

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

        // add tile to map
        Polygon tile = createTile("OctA01", PlayerColour.EMPTY);

        // player move 1
        gameManager.humanMove(tile);

        // make tile for bot to move to
        Polygon botTile = createTile("OctA02", PlayerColour.EMPTY);

        // bot move
        gameManager.botMove();

        // tile 3
        Polygon tile3 = createTile("OctA03", PlayerColour.EMPTY);

        // player move 2
        assertNotNull(gameManager.humanMove(tile3));
    }



    // -------------------- botMove function TEST -------------------------
    // test if botMove with an empty board
    @Test
    void botMoveEmptyBoard()
    {
        // set bot to current player
        gameManager.currentPlayer = gameManager.bot;

        // create tiles
        Polygon tile = createTile("OctA01", PlayerColour.EMPTY);
        Polygon tile2 = createTile("OctA02", PlayerColour.EMPTY);

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

        Polygon tile = createTile("OctA01", PlayerColour.BLACK);
        Polygon tile2 = createTile("OctA02", PlayerColour.BLACK);
        Polygon tile3 = createTile("OctA03", PlayerColour.EMPTY);

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

        // set all nodes owner to black to mimic full board
        for(Node node : gameManager.board.nodeMap.values())
        {
            node.setOwner(PlayerColour.BLACK);
        }

        // get bots move
        Move botMove = gameManager.botMove();

        assertNull(botMove);
    }

    // bot can move freely when enabled
    @Test
    void botMoveBotEnabled()
    {
        gameManager.setBotEnabled(true);
        gameManager.currentPlayer = gameManager.bot;
        Polygon tile = createTile("OctA01", PlayerColour.EMPTY);

        Move botMove = gameManager.botMove();
        // bot enabled so should be valid move
        assertNotNull(botMove);
    }

    // bot not allowed to move if its disabled
    @Test
    void botMoveBotDisabled()
    {
        gameManager.setBotEnabled(false);
        gameManager.currentPlayer = gameManager.bot;
        Polygon tile = createTile("OctA01", PlayerColour.EMPTY);

        Move botMove = gameManager.botMove();
        // bot disabled so should be invalid
        assertNull(botMove);
    }

    // bot disabled and tries move should switch turns
    @Test
    void botMoveBotDisabledSwitchesTurns()
    {
        gameManager.setBotEnabled(false);
        gameManager.currentPlayer = gameManager.bot;
        gameManager.otherPlayer = gameManager.human;

        Polygon tile = createTile("OctA01", PlayerColour.EMPTY);
        // bot disabled so invalid move
        Move botMove = gameManager.botMove();

        // current player should now be human
        assertEquals(gameManager.human, gameManager.getCurrentPlayer());
    }

    // bot disabled and tries move should switch turns
    @Test
    void botMoveAfterHumanEnablesBot() throws InterruptedException {
        // disable bot and set human as current player
        gameManager.setBotEnabled(false);
        gameManager.currentPlayer = gameManager.human;
        gameManager.otherPlayer = gameManager.bot;
        // avoids pie rule interfering
        gameManager.turnCount = 5;

        Polygon tile = createTile("OctA01", PlayerColour.EMPTY);
        // set bot enabled and simulate human move
        gameManager.setBotEnabled(true);
        gameManager.humanMove(tile);

        Polygon tile2 = createTile("OctA02", PlayerColour.EMPTY);
        // bot should now be able to move
        Move botMove = gameManager.botMove();
        assertNotNull(botMove);
    }



    // -------------------- isBoardFull function TEST -------------------------
    // test if an empty board returns false
    @Test
    void isBoardFullEmptyBoard()
    {
        // set all nodes owner to empty
        for(Node node : gameManager.board.nodeMap.values())
        {
            node.setOwner(PlayerColour.EMPTY);
        }
        assertFalse(gameManager.isBoardFull());
    }

    // test if board with some but not all tiles filled returns false
    @Test
    void isBoardFullMixedBoard()
    {
        Polygon tile = createTile("OctA01", PlayerColour.BLACK);
        Polygon tile2 = createTile("OctA02", PlayerColour.EMPTY);
        Polygon tile3 = createTile("OctA03", PlayerColour.EMPTY);

        assertFalse(gameManager.isBoardFull());
    }

    // test if a fully filled board returns true
    @Test
    void isBoardFullFilledBoard()
    {
        // set all nodes owner to black to mimic full board
        for(Node node : gameManager.board.nodeMap.values())
        {
            node.setOwner(PlayerColour.BLACK);
        }

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

    // check that player colours are assigned correct when human starts
    @Test
    void setUpGameHumanColour()
    {
        gameManager.setUpGame(gameManager.human);

        // first player should be black
        assertEquals(PlayerColour.BLACK, gameManager.currentPlayer.getPlayerColour());
        // second player should be white
        assertEquals(PlayerColour.WHITE, gameManager.otherPlayer.getPlayerColour());
    }

    // check that player colours are assigned correct when bot starts
    @Test
    void setUpGameBotColour()
    {
        gameManager.setUpGame(gameManager.bot);

        // first player should be black
        assertEquals(PlayerColour.BLACK, gameManager.currentPlayer.getPlayerColour());
        // second player should be white
        assertEquals(PlayerColour.WHITE, gameManager.otherPlayer.getPlayerColour());
    }

    // test if bot is enabled by default on game set up
    @Test
    void setUpGameBotEnabledByDefault()
    {
        gameManager.setUpGame(gameManager.bot);
        assertTrue(gameManager.isBotEnabled());
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

        Polygon tile = createTile("OctA01", PlayerColour.EMPTY);
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

        Polygon tile = createTile("OctA01", PlayerColour.BLACK);
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

        Polygon tile = createTile("OctA01", PlayerColour.EMPTY);
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

        Polygon tile = createTile("OctA01", PlayerColour.EMPTY);
        // make bot move
        gameManager.botMove();

        assertEquals(1, gameManager.getTurnCount());
    }

    // check if turn count doesn't increment after an invalid move
    @Test
    void getTurnCountInvalidMove()
    {
        gameManager.currentPlayer = gameManager.human;

        Polygon tile = createTile("OctA01", PlayerColour.BLACK);
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

    // check the colours swap correctly if pie rule used on humans turn
    @Test
    void activatePieRuleHuman()
    {
        gameManager.turnCount = 1;
        gameManager.currentPlayer = gameManager.human;
        gameManager.otherPlayer = gameManager.bot;
        // White player is always the player that gets to use pie rule
        gameManager.human.setPlayerColour(PlayerColour.WHITE);
        gameManager.bot.setPlayerColour(PlayerColour.BLACK);

        // call pie rule
        gameManager.activatePieRule(gameManager.human);

        // check colours updated
        assertEquals(PlayerColour.BLACK, gameManager.human.getPlayerColour());
        assertEquals(PlayerColour.WHITE, gameManager.bot.getPlayerColour());

    }

    // check the colours swap correctly if pie rule used on bots turn
    @Test
    void activatePieRuleBot()
    {
        gameManager.turnCount = 1;
        gameManager.currentPlayer = gameManager.bot;
        gameManager.otherPlayer = gameManager.human;
        // White player is always the player that gets to use pie rule
        gameManager.bot.setPlayerColour(PlayerColour.WHITE);
        gameManager.human.setPlayerColour(PlayerColour.BLACK);

        // call pie rule
        gameManager.activatePieRule(gameManager.bot);

        // check colours updated
        assertEquals(PlayerColour.BLACK, gameManager.bot.getPlayerColour());
        assertEquals(PlayerColour.WHITE, gameManager.human.getPlayerColour());

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
        Polygon tile = createTile("OctA01", PlayerColour.EMPTY);

        Move validMove = new Move(gameManager.human, tile, gameManager.board.getNode("OctA01"));

        assertTrue(gameManager.validateMove(validMove));
    }

    // test if invalid move returns false
    @Test
    void validateMoveInvalid()
    {
        Polygon tile = createTile("OctA01", PlayerColour.WHITE);

        Move invalidMove = new Move(gameManager.bot, tile, gameManager.board.getNode("OctA01"));

        assertFalse(gameManager.validateMove(invalidMove));
    }

    // test if no tile returns false
    @Test
    void validateMoveNullTile()
    {
        Move nullTileMove = new Move(gameManager.human, null, gameManager.board.getNode("OctA01"));

        assertFalse(gameManager.validateMove(nullTileMove));
    }

    // test if no player returns false
    @Test
    void validateMoveNullPlayer()
    {
        Polygon tile = createTile("OctA01", PlayerColour.EMPTY);

        Move nullPlayerMove = new Move(null, tile, gameManager.board.getNode("OctA01"));

        assertFalse(gameManager.validateMove(nullPlayerMove));
    }



    // ----------------------- isGameOver test ----------------------

    // test if game is over on black winning move
    @Test
    void isGameOverOnBlackWinningMove()
    {
        // Human plays Black for this test
        gameManager.currentPlayer = gameManager.human;
        gameManager.otherPlayer = gameManager.bot;
        gameManager.human.setPlayerColour(PlayerColour.BLACK);
        gameManager.bot.setPlayerColour(PlayerColour.WHITE);

        // Build an almost-complete Black path from top to bottom in column A
        // Leave OctA11 empty so the final move can trigger win detection.
        for (int row = 1; row <= 10; row++)
        {
            String id = String.format("OctA%02d", row);
            gameManager.board.getNode(id).setOwner(PlayerColour.BLACK);
        }

        // Winning move: complete the connection to bottomRow
        Polygon winningTile = createTile("OctA11", PlayerColour.EMPTY);
        Move move = gameManager.humanMove(winningTile);

        assertNotNull(move);
        assertTrue(gameManager.isGameOver());
    }

    // test if game is over on white winning move
    @Test
    void isGameOverOnWhiteWinningMove()
    {
        // Human plays White for this test
        gameManager.currentPlayer = gameManager.human;
        gameManager.otherPlayer = gameManager.bot;
        gameManager.human.setPlayerColour(PlayerColour.WHITE);
        gameManager.bot.setPlayerColour(PlayerColour.BLACK);

        // Build an almost-complete White path from left to right on row 6
        // Leave OctK06 empty so the final move can trigger win detection.
        for (char col = 'A'; col <= 'J'; col++)
        {
            String id = String.format("Oct%c06", col);
            gameManager.board.getNode(id).setOwner(PlayerColour.WHITE);
        }

        // Winning move: complete the connection to rightRow
        Polygon winningTile = createTile("OctK06", PlayerColour.EMPTY);
        Move move = gameManager.humanMove(winningTile);

        assertNotNull(move);
        assertTrue(gameManager.isGameOver());
    }
    @Test
    
    void isGameOverOnHumanWhitePlayingCentreTile()
    {
        // Test if game is over if line completed by playing a now edge tile
        gameManager.currentPlayer = gameManager.human;
        gameManager.otherPlayer = gameManager.bot;
        gameManager.human.setPlayerColour(PlayerColour.WHITE);
        gameManager.bot.setPlayerColour(PlayerColour.BLACK);

        // Build an almost-complete White path from left to right on row 6
        // Leave OctE06 empty so the non-edge final move can trigger win detection.
        for (char col = 'A'; col <= 'D'; col++)
        {
            String id = String.format("Oct%c06", col);
            gameManager.board.getNode(id).setOwner(PlayerColour.WHITE);
        }
        for (char col = 'E'; col <= 'K'; col++)
        {
            String id = String.format("Oct%c06", col);
            gameManager.board.getNode(id).setOwner(PlayerColour.WHITE);
        }

        // Winning move: complete the connection to playing centre tile
        Polygon winningTile = createTile("OctE06", PlayerColour.EMPTY);
        Move move = gameManager.humanMove(winningTile);

        assertNotNull(move);
        assertTrue(gameManager.isGameOver());
    }

    void isGameOverOnHumanBlackPlayingCentreTile()
    {
        // Test if game is over if line completed by playing a now edge tile
        gameManager.currentPlayer = gameManager.human;
        gameManager.otherPlayer = gameManager.bot;
        gameManager.human.setPlayerColour(PlayerColour.BLACK);
        gameManager.bot.setPlayerColour(PlayerColour.WHITE);

        // Build an almost-complete Black path from top to bottom on row 6
        // Leave OctD06 empty so the non-edge final move can trigger win detection.
        for (int row = 1; row <= 5; row++)
        {
            String id = String.format("OctD%02d", row);
            gameManager.board.getNode(id).setOwner(PlayerColour.BLACK);
        }
        for (int row = '7'; row <= 11; row++)
        {
            String id = String.format("OctD%02d", row);
            gameManager.board.getNode(id).setOwner(PlayerColour.BLACK);
        }

        // Winning move: complete the connection to playing centre tile
        Polygon winningTile = createTile("OctD06", PlayerColour.EMPTY);
        Move move = gameManager.humanMove(winningTile);

        assertNotNull(move);
        assertTrue(gameManager.isGameOver());
    }

    void isGameOverOnBotBlackPlayingCentreTile()
    {
        // Test if game is over if line completed by playing a now edge tile
        gameManager.currentPlayer = gameManager.human;
        gameManager.otherPlayer = gameManager.bot;
        gameManager.human.setPlayerColour(PlayerColour.WHITE);
        gameManager.bot.setPlayerColour(PlayerColour.BLACK);

        // Build an almost-complete Black path from top to bottom on row 6
        // Leave OctD06 empty so the non-edge final move can trigger win detection.
        for (int row = 1; row <= 5; row++)
        {
            String id = String.format("OctD%02d", row);
            gameManager.board.getNode(id).setOwner(PlayerColour.BLACK);
        }
        for (int row = '7'; row <= 11; row++)
        {
            String id = String.format("OctD%02d", row);
            gameManager.board.getNode(id).setOwner(PlayerColour.BLACK);
        }

        // Force bot black move
        Polygon winningTile = createTile("OctD06", PlayerColour.BLACK);
    

        
        assertTrue(gameManager.isGameOver());
    }

     @Test
    
    void isGameOverWithMultiColourConnection()
    {
        // Test that game isn't over if a line connects two sides with both colours
        gameManager.currentPlayer = gameManager.human;
        gameManager.otherPlayer = gameManager.bot;
        gameManager.human.setPlayerColour(PlayerColour.WHITE);
        gameManager.bot.setPlayerColour(PlayerColour.BLACK);

        // Build an almost-complete White path from left to right on row 6
        // Leave OctE06 empty so the non-edge final move can trigger win detection.
        for (char col = 'A'; col <= 'J'; col++)
        {
            String id = String.format("Oct%c06", col);

            if (col%2 == 0){

                gameManager.board.getNode(id).setOwner(PlayerColour.WHITE);
            }
            else {

                gameManager.board.getNode(id).setOwner(PlayerColour.BLACK);

            }
        }
        

        // Winning move: complete the connection to playing centre tile
        Polygon winningTile = createTile("OctK06", PlayerColour.EMPTY);
        Move move = gameManager.humanMove(winningTile);

        assertNotNull(move);
        assertFalse(gameManager.isGameOver());
    }

    

    // test if game is not over on a non-winning move
    @Test
    void isGameOverNonWinningMove()
    {
        // Human plays White for this test
        gameManager.currentPlayer = gameManager.human;
        gameManager.otherPlayer = gameManager.bot;
        gameManager.human.setPlayerColour(PlayerColour.WHITE);
        gameManager.bot.setPlayerColour(PlayerColour.BLACK);

        // Build an almost-complete White path from left to right on row 6
        // Leave OctK06 empty so the final move can trigger win detection.
        for (char col = 'A'; col <= 'F'; col++)
        {
            String id = String.format("Oct%c06", col);
            gameManager.board.getNode(id).setOwner(PlayerColour.WHITE);
        }

        // Winning move: complete the connection to rightRow
        Polygon winningTile = createTile("OctK06", PlayerColour.EMPTY);
        Move move = gameManager.humanMove(winningTile);

        assertNotNull(move);
        assertFalse(gameManager.isGameOver());
    }

    // test if game is not over on empty board
    @Test
    void isGameOverEmptyBoard()
    {
        // set all nodes to empty owner
        for(Node node : gameManager.board.nodeMap.values())
        {
            node.setOwner(PlayerColour.EMPTY);
        }

        assertFalse(gameManager.isGameOver());
    }

    // test if game is over on full board
    @Test
    void isGameOverFullBoard()
    {
        // set all nodes owner to black
        for(Node node : gameManager.board.nodeMap.values())
        {
            node.setOwner(PlayerColour.BLACK);
        }

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

        Polygon tile = createTile("OctA01", PlayerColour.EMPTY);

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

        Polygon tile = createTile("OctA01", PlayerColour.EMPTY);

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
        Polygon tile = createTile("OctA01", PlayerColour.EMPTY);

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

    // test if bot is enabled at start of every game
    @Test
    void resetGameBotEnabled()
    {
        gameManager.botEnabled = false;
        gameManager.gameOver = true;
        gameManager.resetGame();
        assertTrue(gameManager.botEnabled);
    }


    // ------------------------ isBotEnabled / setBotEnabled tests
    // set botEnabled false
    @Test
    void setBotEnabledFalse()
    {
        gameManager.botEnabled = true;
        gameManager.setBotEnabled(false);
        assertFalse(gameManager.botEnabled);
    }

    // set botEnabled true
    @Test
    void setBotEnabledTrue()
    {
        gameManager.botEnabled = false;
        gameManager.setBotEnabled(true);
        assertTrue(gameManager.botEnabled);
    }

    @Test
    void isBotEnabledFalse()
    {
        gameManager.botEnabled = true;
        gameManager.setBotEnabled(false);
        assertFalse(gameManager.isBotEnabled());
    }

    @Test
    void isBotEnabledTrue()
    {
        gameManager.botEnabled = false;
        gameManager.setBotEnabled(true);
        assertTrue(gameManager.isBotEnabled());
    }

}