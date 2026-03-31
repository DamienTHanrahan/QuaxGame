package COMP20050.Group4QuaxGame;

import javafx.scene.shape.Polygon;

import java.util.*;

import static COMP20050.Group4QuaxGame.BoardController.tiles;

public class GameManager
{
    Board board = new Board();
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
    private Player winner;

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
        winner = null;

        for(Node node : board.nodeMap.values())
        {
            node.setOwner(PlayerColor.EMPTY);
        }

        if(startingPlayer == human)
        {
            currentPlayer = human;
            otherPlayer = bot;
        }
        else
        {
            currentPlayer = bot;
            otherPlayer = human;
        }
        currentPlayer.setPlayerColor(PlayerColor.BLACK);
        otherPlayer.setPlayerColor(PlayerColor.WHITE);
    }

    // logic for the human players turn
    protected Move humanMove(Polygon tile)
    {
        if(currentPlayer != human || isGameOver())
        {
            return null;
        }

        Node node = board.getNode(tile.getId());
        Move move = new Move(currentPlayer, tile, node);
        if(!validateMove(move))
        {
            return null;
        }
        node.setOwner(currentPlayer.getPlayerColor());
        if(checkWinner(currentPlayer.getPlayerColor()))
        {
            gameOver = true;
            setWinner(currentPlayer);
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
        Node node;
        do
        {
            String moveID = bot.chooseMove();
            tile = tiles.get(moveID);
            node = board.getNode(moveID);

        }while(!validateMove(new Move(bot, tile, node)));

        Move move = new Move(currentPlayer, tile, node);
        node.setOwner(currentPlayer.getPlayerColor());

        if(checkWinner(currentPlayer.getPlayerColor()))
        {
            gameOver = true;
            setWinner(currentPlayer);
        }
        switchTurns();
        return move;
    }

    // method to check if board is full to prevent bot infinite loop
    protected boolean isBoardFull()
    {
        for(Node node : board.nodeMap.values())
        {
            if (node.getOwner() == PlayerColor.EMPTY)
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
        if(move.getTile() == null || move.getPlayer() == null || move.getNode() == null)
        {
            return false;
        }

        return move.getNode().getOwner() == PlayerColor.EMPTY;
    }

    public boolean isGameOver()
    {
        return (gameOver || isBoardFull());
    }

    protected boolean resetGame()
    {
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

    // check for winner given the player color
    protected boolean checkWinner(PlayerColor playerColor)
    {
        ArrayList<Node> startingNodes;
        ArrayList<Node> endingNodes;

        // match starting and ending node based on player colors win conditions
        if(playerColor == PlayerColor.BLACK)
        {
            startingNodes = board.topRow;
            endingNodes = board.bottomRow;
        }
        else if(playerColor == PlayerColor.WHITE)
        {
            startingNodes = board.leftRow;
            endingNodes = board.rightRow;
        }
        else
        {
            return false;
        }

        // for each of the nodes in the starting list if it matches the player color try traverse board to an ending node
        for(Node startNode : startingNodes)
        {
            if(startNode.getOwner() == playerColor)
            {
                ArrayList<Node> visitedNodes = new ArrayList<>();
                if(canTraverseBoard(startNode, endingNodes, visitedNodes, playerColor))
                {
                    return true;
                }
            }
        }
        // no winning path found
        return false;
    }

    // helper method for checkWinner to check if you can traverse board to reach a node in the ending node list
    private boolean canTraverseBoard(Node currentNode, ArrayList<Node> endingNodes, ArrayList<Node> visitedNodes, PlayerColor playerColor)
    {
        // check if the current node is in the ending node list
        if(endingNodes.contains(currentNode))
        {
            return true;
        }
        // mark current node as visited
        visitedNodes.add(currentNode);
        // check for each of its neighbours if it is a new node not visited before and matches the player color
        for(Node neighbour : currentNode.getNeighbours())
        {
            if(neighbour.getOwner() == playerColor && !visitedNodes.contains(neighbour))
            {
                if(canTraverseBoard(neighbour, endingNodes, visitedNodes, playerColor))
                {
                    return true;
                }
            }
        }
        // if node is not an end node and no neighbours left to explore that match player color
        return false;
    }

    protected Player getWinner()
    {
        return winner;
    }

    private void setWinner(Player winner)
    {
        this.winner = winner;
    }
}
