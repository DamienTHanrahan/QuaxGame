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

    protected boolean botEnabled = true;
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
        botEnabled = true;

        for(Node node : board.nodeMap.values())
        {
            node.setOwner(PlayerColour.EMPTY);
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
        currentPlayer.setPlayerColour(PlayerColour.BLACK);
        otherPlayer.setPlayerColour(PlayerColour.WHITE);
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
        node.setOwner(currentPlayer.getPlayerColour());
        if(checkWinner(currentPlayer.getPlayerColour()))
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

        if(!isBotEnabled())
        {
            switchTurns();
            return null;
        }

        if(turnCount == 1 && bot.usePieRule(board))
        {
            activatePieRule(bot);
            return null;
        }

        
        Polygon tile;
        Node node;
        
            String moveID = bot.chooseMove(board, currentPlayer.getPlayerColour());
            tile = tiles.get(moveID);
            node = board.getNode(moveID);

        

        Move move = new Move(currentPlayer, tile, node);
        node.setOwner(currentPlayer.getPlayerColour());

        if(checkWinner(currentPlayer.getPlayerColour()))
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
            if (node.getOwner() == PlayerColour.EMPTY)
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

    // swap player colours for pie rule
    protected void activatePieRule(Player playerWhoUsedPieRule)
    {
        if(pieRuleUsed || turnCount != 1 || playerWhoUsedPieRule != currentPlayer)
        {
            return;
        }
        pieRuleUsed = true;
        PlayerColour temp = currentPlayer.getPlayerColour();
        currentPlayer.setPlayerColour(otherPlayer.getPlayerColour());
        otherPlayer.setPlayerColour(temp);
        switchTurns();
    }

    // check if tile is valid
    protected boolean validateMove(Move move)
    {
        if(move.getTile() == null || move.getPlayer() == null || move.getNode() == null)
        {
            return false;
        }

        return move.getNode().getOwner() == PlayerColour.EMPTY;
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

    // check for winner given the player colour
    protected boolean checkWinner(PlayerColour playerColour)
    {
        Node startNode;
        Node endNode;

        // match starting and ending node based on player colours win conditions
        if(playerColour == PlayerColour.BLACK)
        {
            startNode = board.topRow;
            endNode = board.bottomRow;
        }
        else if(playerColour == PlayerColour.WHITE)
        {
            startNode = board.leftRow;
            endNode = board.rightRow;
        }
        else
        {
            return false;
        }

        // try traverse board from start to end to check for win
        ArrayList<Node> visitedNodes = new ArrayList<>();
        if(canTraverseBoard(startNode, endNode, visitedNodes, playerColour))
        {
            return true;
        }

        // no winning path found
        return false;
    }

    // helper method for checkWinner to check if you can traverse board to reach a node in the ending node list
    private boolean canTraverseBoard(Node currentNode, Node endingNode, ArrayList<Node> visitedNodes, PlayerColour playerColour)
    {
        // check if the current node is the end node
        if(currentNode == endingNode)
        {
            return true;
        }
        // mark current node as visited
        visitedNodes.add(currentNode);
        // check for each of its neighbours if it is a new node not visited before and matches the player colour
        for(Node neighbour : currentNode.getNeighbours())
        {
            if(neighbour.getOwner() == playerColour && !visitedNodes.contains(neighbour))
            {
                if(canTraverseBoard(neighbour, endingNode, visitedNodes, playerColour))
                {
                    return true;
                }
            }
        }
        // if node is not an end node and no neighbours left to explore that match player colour
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

    protected boolean isBotEnabled()
    {
        return botEnabled;
    }

    protected void setBotEnabled(boolean botEnabled)
    {
        this.botEnabled = botEnabled;
    }

    protected Stack<Node> getPath(PlayerColour colour) {

        return board.getPath(colour);

        
    
    }

    public Board getBoard() {

        return board;
    }
}
