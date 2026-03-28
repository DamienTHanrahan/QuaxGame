package COMP20050.Group4QuaxGame;

import java.util.HashMap;
import java.util.Map;

public class Board
{
    protected final Map<String, Node> nodeMap = new HashMap<>();

    public Board()
    {
        constructNodeMap();
    }

    private void constructNodeMap()
    {
        // construct octagon nodes
        for (char col = 'A'; col <= 'K'; col++)
        {
            for (int row = 1; row <= 11; row++)
            {
                String id = "Oct" + col;
                if(row < 10)
                {
                    id += "0" + row;
                }
                else
                {
                    id += row;
                }
                Node newNode = new Node(id);
                nodeMap.put(id, newNode);
            }
        }

        // construct rhombus nodes
        for (char col = 'A'; col <= 'J'; col++)
        {
            for (int row = 1; row <= 10; row++)
            {
                String id = "Rho" + col;
                if(row < 10)
                {
                    id += "0" + row;
                }
                else
                {
                    id += row;
                }
                Node newNode = new Node(id);
                nodeMap.put(id, newNode);
            }
        }
    }

    protected Node getNode(String id)
    {
        return nodeMap.get(id);
    }

}
