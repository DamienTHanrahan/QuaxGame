package COMP20050.Group4QuaxGame;

import javafx.scene.shape.Polygon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BoardGraph {
    Node[][] octagons = new Node[11][11];
    Node[][] rhombi = new Node[10][10];

    Node whiteLeft = new Node(-1, -1, PlayerColor.WHITE);
    Node whiteRight = new Node(-2, -2, PlayerColor.WHITE);
    Node blackUp = new Node(-3, -3, PlayerColor.BLACK);
    Node blackDown = new Node(-4, -4, PlayerColor.BLACK);

    protected static Map<String, Node> nodes = new HashMap<>();

    public BoardGraph() {
        initializeGraph();
    }

    private void initializeGraph() {
        for (int i = 0; i < octagons.length; i++) {
            for (int j = 0; j < octagons[i].length; j++) {
                octagons[i][j] = new Node(i, j, PlayerColor.EMPTY);
                StringBuilder sb = new StringBuilder();
                sb.append("Oct");
                sb.append((char) (i + 65));
                if(j+1 < 10) {
                    sb.append("0");
                }
                sb.append(j+1);
                octagons[i][j].setID(sb.toString());
                nodes.put(sb.toString(), octagons[i][j]);
            }
        }
        for (int i = 0; i < rhombi.length; i++) {
            for (int j = 0; j < rhombi[i].length; j++) {
                rhombi[i][j] = new Node(i, j, PlayerColor.EMPTY);
                StringBuilder sb = new StringBuilder();
                sb.append("Rho");
                sb.append((char) (i + 65));
                if(j+1 < 10) {
                    sb.append("0");
                }
                sb.append(j+1);
                rhombi[i][j].setID(sb.toString());
                nodes.put(sb.toString(), rhombi[i][j]);
            }
        }
        for (int i = 0; i < octagons.length; i++) {
            for (int j = 0; j < octagons[i].length; j++) {
                if (i > 0) {
                    connect(octagons[i][j], octagons[i - 1][j]);
                }
                if (j > 0) {
                    connect(octagons[i][j], octagons[i][j - 1]);
                }
                if (i < octagons.length - 1) {
                    connect(octagons[i][j], octagons[i + 1][j]);
                }
                if (j < octagons[i].length - 1) {
                    connect(octagons[i][j], octagons[i][j + 1]);
                }
            }
        }

        for (int i = 0; i < octagons.length; i++) {
            connect(whiteLeft, octagons[0][i]);
            connect(whiteRight, octagons[octagons[i].length - 1][i]);
        }
        for (int j = 0; j < octagons[0].length; j++) {
            connect(blackUp, octagons[j][0]);
            connect(blackDown, octagons[j][octagons.length - 1]);
        }

        for (int i = 0; i < rhombi.length; i++) {
            for (int j = 0; j < rhombi[i].length; j++) {
                connect(rhombi[i][j], octagons[i][j]);
                connect(rhombi[i][j], octagons[i + 1][j]);
                connect(rhombi[i][j], octagons[i][j + 1]);
                connect(rhombi[i][j], octagons[i + 1][j + 1]);
            }
        }
    }

    public void syncColors() {
        Map<String, PlayerColor> colorMap = BoardState.getColorMap();
        for (Map.Entry<String, PlayerColor> entry : colorMap.entrySet()) {
            String name = entry.getKey();
            PlayerColor color = entry.getValue();
            if (nodes.containsKey(name)) {
                nodes.get(name).setColor(color);
            }
        }
    }

    private void connect(Node node1, Node node2) {
        node1.getNeighbours().add(node2);
        node2.getNeighbours().add(node1);
    }
}
