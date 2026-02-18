package COMP20050.Group4QuaxGame;


import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import static COMP20050.Group4QuaxGame.BoardController.tiles;

public class Bot {
    // Rudimentary bot that makes random moves. To be implemented in the future.
    // Move validation logic to gameManager, and use a loop in gameManager to keep picking tile
    String chooseMove() {
        Polygon move;

        do {
            int rand = (int) (Math.random() * 2);
            switch (rand) {
                case 0 -> move = getRandomOctagon();
                case 1 -> move = getRandomRhombus();
                default -> throw new IllegalStateException("Unexpected value: " + rand);
            }
        } while (move == null || move.getFill() == Color.BLACK || move.getFill() == Color.WHITE);
        return move.getId();

    }

    Polygon getRandomOctagon() {
        int y = (int) (Math.random() * 12);
        char x = (char) ('A' + (int) (Math.random() * 11));
        StringBuilder id = new StringBuilder();
        id.append("Oct");
        id.append(x);
        if (y < 10) {
            id.append("0");
        }
        id.append(y);
        String idStr = id.toString();
        return tiles.get(idStr);
    }

    Polygon getRandomRhombus() {
        int y = (int) (Math.random() * 11);
        char x = (char) ('A' + (int) (Math.random() * 10));
        StringBuilder id = new StringBuilder();
        id.append("Rho");
        id.append(x);
        if (y < 10) {
            id.append("0");
        }
        id.append(y);
        String idStr = id.toString();
        return tiles.get(idStr);
    }
}