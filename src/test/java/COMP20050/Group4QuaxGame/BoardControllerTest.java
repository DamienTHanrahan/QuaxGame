package COMP20050.Group4QuaxGame;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BoardControllerTest {

    BoardController controller = new BoardController();

    @BeforeAll
    static void initJavaFx() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException alreadyStarted) {
            latch.countDown();
        }
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new IllegalStateException("JavaFX toolkit did not initialize in time.");
        }
    }

    @Test
    void initialize_populatesTilesMap() throws Exception {
        BoardController.tiles.clear();


        Map<String, Polygon> injected = new HashMap<>();
        runOnFxThread(() -> {
            injectFields(controller, injected);
            controller.initialize();
        });

        long polygonFieldCount = countPolygonFields();
        assertEquals(polygonFieldCount, BoardController.tiles.size(), "tiles map size");

        for (Map.Entry<String, Polygon> entry : injected.entrySet()) {
            assertSame(entry.getValue(), BoardController.tiles.get(entry.getKey()), "tile for " + entry.getKey());
        }
    }

    private static void injectFields(BoardController controller, Map<String, Polygon> injected) {
        for (Field field : BoardController.class.getDeclaredFields()) {
            if (field.getType() == Polygon.class) {
                Polygon polygon = new Polygon();
                polygon.setId(field.getName());
                setField(controller, field, polygon);
                injected.put(field.getName(), polygon);
            } else if (field.getType() == Label.class) {
                setField(controller, field, new Label());
            } else if(field.getType() == Button.class){
                setField(controller, field, new Button());
            }
        }
    }

    private static void setField(BoardController controller, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(controller, value);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Failed to inject field: " + field.getName(), e);
        }
    }

    private static long countPolygonFields() {
        long count = 0;
        for (Field field : BoardController.class.getDeclaredFields()) {
            if (field.getType() == Polygon.class) {
                count++;
            }
        }
        return count;
    }

    private static void runOnFxThread(Runnable action) throws Exception {
        if (Platform.isFxApplicationThread()) {
            action.run();
            return;
        }
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                latch.countDown();
            }
        });
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new IllegalStateException("JavaFX task did not complete in time.");
        }
    }

    // ------------------------------------- paintTile tests ----------------------------------------
    // test if black player moves does fill color be set to black
    @Test
    void paintTileBlackPlayer()
    {
        // simulate black player and tile
        Human blackPlayer = new Human(PlayerColor.BLACK);
        Polygon tile = new Polygon();
        tile.setFill(Color.ORANGE);

        // simulate move for black
        Move blackMove = new Move(blackPlayer, tile, new Node("OctA01"));
        controller.paintTile(blackMove);

        // check if tile color is updated
        assertEquals(Color.BLACK, blackMove.getTile().getFill());
    }

    // test if black player moves does fill color be set to black
    @Test
    void paintTileWhitePlayer()
    {
        // simulate black player and tile
        Human whitePlayer = new Human(PlayerColor.WHITE);
        Polygon tile = new Polygon();
        tile.setFill(Color.ORANGE);

        // simulate move for black
        Move whiteMove = new Move(whitePlayer, tile, new Node("OctA01"));
        controller.paintTile(whiteMove);

        // check if tile color is updated
        assertEquals(Color.WHITE, whiteMove.getTile().getFill());
    }

    // ------------------------------- updateTurnLabel tests -----------------------
    // check if label displays BLACK to play on start
    @Test
    void updateTurnLabelStartGame() throws Exception
    {
        controller.gameManager.currentPlayer = new Human(PlayerColor.BLACK);

        runOnFxThread(() -> {
            try {
                // get label
                Label label = new Label();
                Field field = BoardController.class.getDeclaredField("playLabel");
                field.setAccessible(true);
                field.set(controller, label);

                controller.updateTurnLabel();

                assertEquals("BLACK to play", label.getText());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    // check if label updates to WHITE to play after black moves
    @Test
    void updateTurnLabelAfterBlackMove() throws Exception
    {
        controller.gameManager.currentPlayer = new Human(PlayerColor.BLACK);
        controller.gameManager.otherPlayer = new Bot(PlayerColor.WHITE);

        runOnFxThread(() -> {
            try {
                // get label
                Label label = new Label();
                Field field = BoardController.class.getDeclaredField("playLabel");
                field.setAccessible(true);
                field.set(controller, label);

                // simulate turn
                controller.gameManager.switchTurns();
                controller.updateTurnLabel();

                // check label updates
                assertEquals("WHITE to play", label.getText());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    // check if label updates to BLACK to play after white moves
    @Test
    void updateTurnLabelAfterWhiteMove() throws Exception
    {
        controller.gameManager.currentPlayer = new Human(PlayerColor.WHITE);
        controller.gameManager.otherPlayer = new Bot(PlayerColor.BLACK);

        runOnFxThread(() -> {
            try {
                // get label
                Label label = new Label();
                Field field = BoardController.class.getDeclaredField("playLabel");
                field.setAccessible(true);
                field.set(controller, label);

                // simulate turn
                controller.gameManager.switchTurns();
                controller.updateTurnLabel();

                // check label updates
                assertEquals("BLACK to play", label.getText());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    // ------------------------------ pieButtonClicked ---------------------------------

    // test if the button disappears after clicked
    @Test
    void hidePieRuleButtonAfterClicked() throws Exception {
        runOnFxThread(() -> {
            try {
                // get button
                Button button = new Button();
                Field field = BoardController.class.getDeclaredField("pieButton");
                field.setAccessible(true);
                field.set(controller, button);

                // get label required for method as calls update label
                Label label = new Label();
                Field field2 = BoardController.class.getDeclaredField("playLabel");
                field2.setAccessible(true);
                field2.set(controller, label);

                controller.gameManager.currentPlayer = new Human(PlayerColor.BLACK);
                controller.gameManager.otherPlayer = new Bot(PlayerColor.WHITE);
                button.setVisible(true);
                controller.pieButtonClicked(null);

                assertFalse(button.isVisible());


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


    // ------------------------------ playAgainButtonClicked ---------------------------------

    // test if the button disappears after clicked
    @Test
    void hidePlayAgainButtonAfterClicked() throws Exception {
        runOnFxThread(() -> {
            try {
                // get button
                Button button = new Button();
                Field field = BoardController.class.getDeclaredField("playAgainButton");
                field.setAccessible(true);
                field.set(controller, button);

                // get label required as method calls update label
                Label label = new Label();
                Field field2 = BoardController.class.getDeclaredField("playLabel");
                field2.setAccessible(true);
                field2.set(controller, label);


                // simulate end game to make button visible
                controller.gameManager.gameOver = true;
                button.setVisible(true);
                controller.playAgainButtonClicked(null);

                assertFalse(button.isVisible());


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}
