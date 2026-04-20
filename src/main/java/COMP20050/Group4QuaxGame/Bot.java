package COMP20050.Group4QuaxGame;

public class Bot extends Player
{
    public Bot(PlayerColour playerColour)
    {
        super(playerColour);
    }

    // Rudimentary bot that makes random moves. To be implemented in the future.
    public String chooseMove() {
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
}