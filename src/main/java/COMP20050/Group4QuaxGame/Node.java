package COMP20050.Group4QuaxGame;

import java.util.ArrayList;

public class Node {
    private final String id;
    private PlayerColor owner;
    private final ArrayList<Node> neighbours;

    public Node(String id) {
        this.id = id;
        this.owner = PlayerColor.EMPTY;
        this.neighbours = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public PlayerColor getOwner() {
        return owner;
    }

    public void setOwner(PlayerColor owner) {
        this.owner = owner;
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }

    public void addNeighbour(Node node)
    {
        neighbours.add(node);
    }
}
