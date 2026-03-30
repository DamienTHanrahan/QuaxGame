package COMP20050.Group4QuaxGame;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.*;

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

    protected boolean gameOver = false;

    BoardGraph boardGraph = new BoardGraph();

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
        turnCount = 0;
        pieRuleUsed = false;
        gameOver = false;

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
        if(currentPlayer != human || isGameOver())
        {
            return null;
        }

        Move move = new Move(currentPlayer, node);
        if(!validateMove(move))
        {
            return null;
        }
        BoardState.declareMove(move);
        boardGraph.syncColors();
        if(detectWin(boardGraph, currentPlayer.getPlayerColor())) {
            System.out.println("Player " + currentPlayer.getPlayerColor() + " wins!");
            gameOver = true;
        }
        switchTurns();
        return move;
    }

    // logic for the bots turn
    protected Move botMove()
    {
        if(currentPlayer != bot || isGameOver())
        {
            return null;
        }

        if(turnCount == 1 && bot.usePieRule())
        {
            activatePieRule(bot);
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
        BoardState.declareMove(move);
        boardGraph.syncColors();
        if(detectWin(boardGraph, currentPlayer.getPlayerColor())) {
            System.out.println("Player " + currentPlayer.getPlayerColor() + " wins!");
            gameOver  = true;
        }
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
    protected void activatePieRule(Player playerWhoUsedPieRule)
    {
        if(pieRuleUsed || turnCount != 1 || playerWhoUsedPieRule != currentPlayer)
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

    public boolean isGameOver()
    {
        return (gameOver || isBoardFull());
    }

    protected boolean resetGame()
    {
        BoardState.reset();
        if(!isGameOver())
        {
            return false;
        }

        int random = (int) (Math.random() * 2);
        if (random == 0)
        {
            setUpGame(human);
        }
        else
        {
            setUpGame(bot);
        }

        return true;
    }

    public boolean detectWin(BoardGraph graph, PlayerColor color) {
        Queue<Node> queue = new LinkedList<>();
        Set<Node> visited = new HashSet<>();

        Node startNode;
        Node endNode;

        if (color == PlayerColor.WHITE) {
            startNode = graph.whiteLeft;
            endNode = graph.whiteRight;
        } else {
            startNode = graph.blackUp;
            endNode = graph.blackDown;
        }

        queue.add(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            if (currentNode == endNode) {
                return true;
            }

            for (Node neighbour : currentNode.getNeighbours()) {
                if (neighbour.getColor() == color && !visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.add(neighbour);
                }
            }
        }
        return false;
    }

}