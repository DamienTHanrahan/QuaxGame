package COMP20050.Group4QuaxGame;

import java.util.HashMap;
import java.util.Map;

public class BoardState {
    private static Map<String, PlayerColor> colorMap = new HashMap<>();

    public static Map<String, PlayerColor> getColorMap() {
        return colorMap;
    }

    public static void setColorMap(Map<String, PlayerColor> colorMap) {
        BoardState.colorMap = colorMap;
    }

    public static void reset() {
        colorMap.clear();
    }

    public static void declareMove(Move move) {
        String name = move.getTile().getId();
        PlayerColor color = move.getPlayer().getPlayerColor();
        colorMap.put(name, color);
    }
}
