package COMP20050.Group4QuaxGame;
import java.util.ArrayList;

public class Bot extends Player
{
    public Bot(PlayerColour playerColour)
    {
        super(playerColour);
    }

    protected enum Strategy {

        AGGRESSIVE,
        ATTACKING,
        NEUTRAL,
        DEFENSIVE,
        STONEWALL
    }
    
    

    private Strategy strategy = Strategy.NEUTRAL;

    protected void setStrategy(Strategy strategy){
        this.strategy = strategy;
        System.out.println("Strategy set to: " + strategy);
    }

    protected Strategy getStrategy(){

        return strategy;
    }

    // Rudimentary bot that makes random moves. To be implemented in the future.
    
    public String chooseMove(Board board, PlayerColour colour) {
        
        
        String move;
        PlayerColour oppColour = null;
        if (colour == PlayerColour.WHITE) {oppColour = PlayerColour.BLACK;}
        else{oppColour = PlayerColour.WHITE; }
        int botPath = board.getCheapestPath(colour);
        int oppPath = board.getCheapestPath(oppColour);
        int scoreDifference = botPath - oppPath;
        double bestScore = 0.0;
        ArrayList<Node> bestNodes = new ArrayList<>();

        // select strategy depending on who is currently winning (shortest path to win)
        if(scoreDifference >= 3)
        {
            setStrategy(Strategy.STONEWALL);
        }
        else if(scoreDifference >= 1)
        {
            setStrategy(Strategy.DEFENSIVE);
        }
        else if(scoreDifference <= -3)
        {
            setStrategy(Strategy.AGGRESSIVE);
        }
        else if(scoreDifference == -1)
        {
            setStrategy(Strategy.ATTACKING);
        }
        else
        {
            setStrategy(Strategy.NEUTRAL);
        }

        for (Node node: board.getEmptyNodes()){

            node.setOwner(colour);

            // calculate improvements to bot in terms of improved path and decrease in opponents path 
            // order swaped for oppScore to ensure positive value.
            int botScore = botPath - board.getCheapestPath(colour);
            int oppScore = board.getCheapestPath(oppColour) - oppPath; 

            double moveScore = getScore(botScore, oppScore, strategy);

            if (moveScore == bestScore) {
                
                bestNodes.add(node);
            }
            else if(moveScore > bestScore){
                bestScore = moveScore;
                bestNodes.clear();
                bestNodes.add(node);
            }


        node.setOwner(PlayerColour.EMPTY);

        }

       // bestNodes contains all the nodes with best possible score. Pick one at random
            move = bestNodes.get((int)(Math.random() * bestNodes.size())).getId();
        // return the coordinates of the move
        return move;
        
    }
    
    
    public String chooseRandomMove() {
        String move;

        int rand = (int) (Math.random() * 2);
        switch (rand) {
            case 0 -> move = getRandomOctagon();
            case 1 -> move = getRandomRhombus();
            default -> throw new IllegalStateException("Unexpected value: " + rand);
        }
        // return the coordinates of the move
        return move;
    }

    // get string for coordinates of a random octagon tile
    String getRandomOctagon() {
        int y = 1 + (int) (Math.random() * 11);
        char x = (char) ('A' + (int) (Math.random() * 11));
        StringBuilder id = new StringBuilder();
        id.append("Oct");
        id.append(x);
        // if it's less than 10 add a 0 before the number
        if (y < 10) {
            id.append("0");
        }
        id.append(y);
        return id.toString();
    }

    // get string for coordinates of a random rhombus tile
    String getRandomRhombus() {
        int y = 1 + (int) (Math.random() * 10);
        char x = (char) ('A' + (int) (Math.random() * 10));
        StringBuilder id = new StringBuilder();
        id.append("Rho");
        id.append(x);
        // if it's less than 10 add a 0 before the number
        if (y < 10) {
            id.append("0");
        }
        id.append(y);
        return id.toString();
    }

    // random 1 - 3 chance to use pie rule
    // TEMP until further bot logic
    protected boolean usePieRule()
    {
        int rand = (int) (Math.random() * 2);
        return (rand == 1);
    }

    

    protected double getScore(int myScore, int oppScore, Strategy strategy) {

        double score = 0;
        
        switch(strategy) {

            case AGGRESSIVE:
                score = (0.9 * myScore) + (0.1 * oppScore);
                break;
            case ATTACKING:
                score = (0.7 * myScore) + (0.3 * oppScore);
                break;
            case NEUTRAL:
                score = (0.5 * myScore) + (0.5 * oppScore);
                break;
            case DEFENSIVE:
                score = (0.3 * myScore) + (0.7 * oppScore);
                break;
            case STONEWALL:
                score = (0.1 * myScore) + (0.9 * oppScore);
                break;

        }
        return score;
    }
}