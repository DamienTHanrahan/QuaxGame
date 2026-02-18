package COMP20050.Group4QuaxGame;

public class Bot {
    // Rudimentary bot that makes random moves. To be implemented in the future.
    // Move validation logic to gameManager, and use a loop in gameManager to keep picking tile
    String chooseMove() {
        String move;

        int rand = (int) (Math.random() * 2);
        switch (rand) {
            case 0 -> move = getRandomOctagon();
            case 1 -> move = getRandomRhombus();
            default -> throw new IllegalStateException("Unexpected value: " + rand);
        }
        return move;
    }

    String getRandomOctagon() {
        int y = 1 + (int) (Math.random() * 11);
        char x = (char) ('A' + (int) (Math.random() * 11));
        StringBuilder id = new StringBuilder();
        id.append("Oct");
        id.append(x);
        if (y < 10) {
            id.append("0");
        }
        id.append(y);
        return id.toString();
    }

    String getRandomRhombus() {
        int y = 1 + (int) (Math.random() * 10);
        char x = (char) ('A' + (int) (Math.random() * 10));
        StringBuilder id = new StringBuilder();
        id.append("Rho");
        id.append(x);
        if (y < 10) {
            id.append("0");
        }
        id.append(y);
        return id.toString();
    }
}