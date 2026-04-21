package COMP20050.Group4QuaxGame;

import java.util.ArrayList;

public class Node {
    private final String id;
    private PlayerColour owner;
    private final ArrayList<Node> neighbours;

    public Node(String id) {
        this.id = id;
        this.owner = PlayerColour.EMPTY;
        this.neighbours = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public PlayerColour getOwner() {
        return owner;
    }

    public void setOwner(PlayerColour owner) {
        this.owner = owner;
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
    }

    public void addNeighbour(Node node)
    {
        // add it to list if it isn't already added
        if(!neighbours.contains(node))
        {
            neighbours.add(node);
        }
    }
}
