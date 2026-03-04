package COMP20050.Group4QuaxGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import static COMP20050.Group4QuaxGame.BoardController.tiles;

public class GameManager
{
    // reference to the human and the bot
    protected final Human human = new Human(null);
    protected final Bot bot = new Bot(null);
    // reference to who's turn it is
    protected Player currentPlayer;
    protected Player otherPlayer;
    // boolean to prevent pie rule from being used twice
    protected boolean pieRuleUsed = false;
    int turnCount = 0;

    // getters for currentPlayer and turnCount
    protected Player getCurrentPlayer()
    {
        return currentPlayer;
    }

    protected int getTurnCount()
    {
        return turnCount;
    }

    // set up game by picking who starts first (50/50 currently in BoardController)
    protected void setUpGame(Player startingPlayer)
    {
        if(startingPlayer == human)
        {
            currentPlayer = human;
            otherPlayer = bot;
            currentPlayer.setPlayerColor(PlayerColor.BLACK);
            otherPlayer.setPlayerColor(PlayerColor.WHITE);
        }
        else
        {
            currentPlayer = bot;
            otherPlayer = human;
            currentPlayer.setPlayerColor(PlayerColor.BLACK);
            otherPlayer.setPlayerColor(PlayerColor.WHITE);
        }
    }

    // logic for the human players turn
    protected Move humanMove(Polygon node)
    {
        if(currentPlayer != human)
        {
            return null;
        }

        Move move = new Move(currentPlayer, node);
        if(!validateMove(move))
        {
            return null;
        }
        switchTurns();
        return move;
    }

    // logic for the bots turn
    protected Move botMove()
    {
        if(currentPlayer != bot || isBoardFull())
        {
            return null;
        }

        if(turnCount == 1 && bot.usePieRule())
        {
            activatePieRule();
            return null;
        }

        // keep picking random tile until its valid
        Polygon tile;
        do
        {
            String moveID = bot.chooseMove();
            tile = tiles.get(moveID);

        }while(!validateMove(new Move(bot, tile)));

        Move move = new Move(currentPlayer, tile);
        switchTurns();
        return move;
    }

    // method to check if board is full to prevent bot infinite loop
    protected boolean isBoardFull()
    {
        for(Polygon p : tiles.values())
        {
            if (p.getFill() != Color.WHITE && p.getFill() != Color.BLACK)
            {
                return false;
            }
        }
        return true;
    }

    // switches turns
    protected void switchTurns()
    {
        turnCount++;

        Player temp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = temp;
    }

    // swap player colors for pie rule
    protected void activatePieRule()
    {
        if(pieRuleUsed || turnCount != 1)
        {
            return;
        }
        pieRuleUsed = true;
        PlayerColor temp = currentPlayer.getPlayerColor();
        currentPlayer.setPlayerColor(otherPlayer.getPlayerColor());
        otherPlayer.setPlayerColor(temp);

        switchTurns();
    }

    // check if tile is valid
    protected boolean validateMove(Move move)
    {
        if(move.getTile() == null || move.getPlayer() == null)
        {
            return false;
        }

        return move.getTile().getFill() != Color.BLACK && move.getTile().getFill() != Color.WHITE;
    }
}