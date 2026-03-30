package COMP20050.Group4QuaxGame;

import java.util.ArrayList;

public class Node {
        private int x;
        private int y;
        private PlayerColor color;
        private ArrayList<Node> neighbours;
        private String id;

        public Node (int x, int y, PlayerColor color) {
            this.x = x;
            this.y = y;
            this.color = color;
            neighbours = new ArrayList<Node>();
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setID (String id) {
            this.id = id;
        }

        public String getID () {
            return id;
        }

        public PlayerColor getColor() {
            return color;
        }

        public void setColor (PlayerColor color) {
            this.color = color;
        }

        public ArrayList<Node> getNeighbours() {
            return neighbours;
        }

        public void setNeighbours (ArrayList<Node> neighbours) {
            this.neighbours = neighbours;
        }

        public int getCost(PlayerColor query) {
            if (color == PlayerColor.EMPTY) {
                return 1;
            } else if (color == query) {
                return 0;
            } else {
                return 999999;
            }
        }
}
