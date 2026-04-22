package COMP20050.Group4QuaxGame;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void resetSharedBoardState() {
        BoardController.tiles.clear();
        Board.blackPath.clear();
        Board.whitePath.clear();
        controller.showStrategyToggled = false;
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
            } else if(field.getType() == CheckBox.class){
                setField(controller, field, new CheckBox());
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
    // test if black player moves does fill colour be set to black
    @Test
    void paintTileBlackPlayer()
    {
        // simulate black player and tile
        Human blackPlayer = new Human(PlayerColour.BLACK);
        Polygon tile = new Polygon();
        tile.setFill(Color.ORANGE);

        // simulate move for black
        Move blackMove = new Move(blackPlayer, tile, new Node("OctA01"));
        controller.paintTile(blackMove);

        // check if tile colour is updated
        assertEquals(Color.BLACK, blackMove.getTile().getFill());
    }

    // test if black player moves does fill colour be set to black
    @Test
    void paintTileWhitePlayer()
    {
        // simulate black player and tile
        Human whitePlayer = new Human(PlayerColour.WHITE);
        Polygon tile = new Polygon();
        tile.setFill(Color.ORANGE);

        // simulate move for black
        Move whiteMove = new Move(whitePlayer, tile, new Node("OctA01"));
        controller.paintTile(whiteMove);

        // check if tile colour is updated
        assertEquals(Color.WHITE, whiteMove.getTile().getFill());
    }

    // ------------------------------- updateTurnLabel tests -----------------------
    // check if label displays BLACK to play on start
    @Test
    void updateTurnLabelStartGame() throws Exception
    {
        controller.gameManager.currentPlayer = new Human(PlayerColour.BLACK);

        runOnFxThread(() -> {
            try {
                // get label
                Label label = new Label();
                Field field = BoardController.class.getDeclaredField("playLabel");
                field.setAccessible(true);
                field.set(controller, label);

                // get other labels and stuff needed for method as calls update label
                CheckBox checkBox = new CheckBox();
                Field field3 = BoardController.class.getDeclaredField("botEnabledBox");
                field3.setAccessible(true);
                field3.set(controller, checkBox);

                Button button2 = new Button();
                Field field4 = BoardController.class.getDeclaredField("strategyButton");
                field4.setAccessible(true);
                field4.set(controller, button2);

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
        controller.gameManager.currentPlayer = new Human(PlayerColour.BLACK);
        controller.gameManager.otherPlayer = new Bot(PlayerColour.WHITE);

        runOnFxThread(() -> {
            try {
                // get label
                Label label = new Label();
                Field field = BoardController.class.getDeclaredField("playLabel");
                field.setAccessible(true);
                field.set(controller, label);

                // get other labels and stuff needed for method as calls update label
                CheckBox checkBox = new CheckBox();
                Field field3 = BoardController.class.getDeclaredField("botEnabledBox");
                field3.setAccessible(true);
                field3.set(controller, checkBox);

                Button button2 = new Button();
                Field field4 = BoardController.class.getDeclaredField("strategyButton");
                field4.setAccessible(true);
                field4.set(controller, button2);

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
        controller.gameManager.currentPlayer = new Human(PlayerColour.WHITE);
        controller.gameManager.otherPlayer = new Bot(PlayerColour.BLACK);

        runOnFxThread(() -> {
            try {
                // get label
                Label label = new Label();
                Field field = BoardController.class.getDeclaredField("playLabel");
                field.setAccessible(true);
                field.set(controller, label);

                // get other labels and stuff needed for method as calls update label
                CheckBox checkBox = new CheckBox();
                Field field3 = BoardController.class.getDeclaredField("botEnabledBox");
                field3.setAccessible(true);
                field3.set(controller, checkBox);

                Button button2 = new Button();
                Field field4 = BoardController.class.getDeclaredField("strategyButton");
                field4.setAccessible(true);
                field4.set(controller, button2);

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

                // get other labels and stuff needed for method as calls update label
                Label label = new Label();
                Field field2 = BoardController.class.getDeclaredField("playLabel");
                field2.setAccessible(true);
                field2.set(controller, label);

                CheckBox checkBox = new CheckBox();
                Field field3 = BoardController.class.getDeclaredField("botEnabledBox");
                field3.setAccessible(true);
                field3.set(controller, checkBox);

                Button button2 = new Button();
                Field field4 = BoardController.class.getDeclaredField("strategyButton");
                field4.setAccessible(true);
                field4.set(controller, button2);

                controller.gameManager.currentPlayer = new Human(PlayerColour.BLACK);
                controller.gameManager.otherPlayer = new Bot(PlayerColour.WHITE);
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

                CheckBox checkBox = new CheckBox();
                Field field3 = BoardController.class.getDeclaredField("botEnabledBox");
                field3.setAccessible(true);
                field3.set(controller, checkBox);

                Button button2 = new Button();
                Field field4 = BoardController.class.getDeclaredField("strategyButton");
                field4.setAccessible(true);
                field4.set(controller, button2);


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


    // --------------------------- botEnabledBoxClicked  ----------------------------------------------
    // check when click box it enables bot
    @Test
    void botEnabledBoxEnableBot() throws Exception
    {
        runOnFxThread(() -> {
            try {
                // set players
                controller.gameManager.currentPlayer = new Human(PlayerColour.BLACK);
                controller.gameManager.otherPlayer = new Bot(PlayerColour.WHITE);

                // enable dev mode
                BoardController.DEV_MODE = true;
                // get the checkBox
                CheckBox botEnabledBox = new CheckBox();
                Field field = BoardController.class.getDeclaredField("botEnabledBox");
                field.setAccessible(true);
                field.set(controller, botEnabledBox);

                // get other UI elements needed as updateLabel needs them
                Label label = new Label();
                Field field2 = BoardController.class.getDeclaredField("playLabel");
                field2.setAccessible(true);
                field2.set(controller, label);

                Button button2 = new Button();
                Field field4 = BoardController.class.getDeclaredField("strategyButton");
                field4.setAccessible(true);
                field4.set(controller, button2);

                // simulate button clicked when bot disabled
                controller.gameManager.botEnabled = false;
                botEnabledBox.setSelected(true);
                controller.botEnabledBoxClicked();

                // bot should now be enabled
                assertTrue(controller.gameManager.isBotEnabled());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    // check when click box it disables bot
    @Test
    void botEnabledBoxDisableBot() throws Exception {
        runOnFxThread(() -> {
            try {
                // set players
                controller.gameManager.currentPlayer = new Human(PlayerColour.BLACK);
                controller.gameManager.otherPlayer = new Bot(PlayerColour.WHITE);

                // enable dev mode
                BoardController.DEV_MODE = true;
                // get the checkBox
                CheckBox botEnabledBox = new CheckBox();
                Field field = BoardController.class.getDeclaredField("botEnabledBox");
                field.setAccessible(true);
                field.set(controller, botEnabledBox);

                // get other UI elements needed as updateLabel needs them
                Label label = new Label();
                Field field2 = BoardController.class.getDeclaredField("playLabel");
                field2.setAccessible(true);
                field2.set(controller, label);

                Button button2 = new Button();
                Field field4 = BoardController.class.getDeclaredField("strategyButton");
                field4.setAccessible(true);
                field4.set(controller, button2);

                // simulate button clicked when bot enabled
                controller.gameManager.botEnabled = true;
                botEnabledBox.setSelected(false);
                controller.botEnabledBoxClicked();

                // bot should now be disabled
                assertFalse(controller.gameManager.isBotEnabled());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    // check the box is no update happens when dev mode is false
    @Test
    void botEnabledBoxDevModeFalse() throws Exception {
        runOnFxThread(() -> {
            try {
                // set players
                controller.gameManager.currentPlayer = new Human(PlayerColour.BLACK);
                controller.gameManager.otherPlayer = new Bot(PlayerColour.WHITE);

                // disable dev mode
                BoardController.DEV_MODE = false;
                // get the checkBox
                CheckBox botEnabledBox = new CheckBox();
                Field field = BoardController.class.getDeclaredField("botEnabledBox");
                field.setAccessible(true);
                field.set(controller, botEnabledBox);

                // get other UI elements needed as updateLabel needs them
                Label label = new Label();
                Field field2 = BoardController.class.getDeclaredField("playLabel");
                field2.setAccessible(true);
                field2.set(controller, label);

                Button button2 = new Button();
                Field field4 = BoardController.class.getDeclaredField("strategyButton");
                field4.setAccessible(true);
                field4.set(controller, button2);

                // simulate button clicked when bot enabled
                controller.gameManager.botEnabled = true;
                botEnabledBox.setSelected(false);
                controller.botEnabledBoxClicked();

                // Dev mode false so bot should still be enabled
                assertTrue(controller.gameManager.isBotEnabled());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    // --------------------------- showStrategyButtonClicked  ----------------------------------------------
    @Test
    void showStrategyButtonClicked_highlightsEmptyPathNodesAndSkipsOccupied() throws Exception {
        runOnFxThread(() -> {
            BoardController.DEV_MODE = true;

            Button strategyButton = new Button();
            Field strategyField = null;
            try {
                strategyField = BoardController.class.getDeclaredField("strategyButton");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            strategyField.setAccessible(true);
            try {
                strategyField.set(controller, strategyButton);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            Polygon blackPathEmptyTile = tile("OctA01", Color.web("#ffd675"));
            Polygon whitePathEmptyTile = tile("RhoA01", Color.web("#bcbcbc"));
            Polygon occupiedTile = tile("OctA02", Color.web("#123456"));

            BoardController.tiles.put("OctA01", blackPathEmptyTile);
            BoardController.tiles.put("RhoA01", whitePathEmptyTile);
            BoardController.tiles.put("OctA02", occupiedTile);

            Node blackPathEmptyNode = new Node("OctA01");
            Node occupiedNode = new Node("OctA02");
            occupiedNode.setOwner(PlayerColour.BLACK);
            Node whitePathEmptyNode = new Node("RhoA01");

            Board.blackPath = new Stack<>();
            Board.blackPath.push(blackPathEmptyNode);
            Board.blackPath.push(occupiedNode);

            Board.whitePath = new Stack<>();
            Board.whitePath.push(whitePathEmptyNode);

            controller.showStrategyButtonClicked();

            assertEquals(Color.web("#575757"), blackPathEmptyTile.getFill());
            assertEquals(Color.web("#d4d4d4"), whitePathEmptyTile.getFill());
            assertEquals(Color.web("#123456"), occupiedTile.getFill());
            assertTrue(controller.showStrategyToggled);
        });
    }

    @Test
    void showStrategyButtonClicked_secondClickResetsToDefaultTileColours() throws Exception {
        runOnFxThread(() -> {
            BoardController.DEV_MODE = true;

            Button strategyButton = new Button();
            Field strategyField = null;
            try {
                strategyField = BoardController.class.getDeclaredField("strategyButton");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            strategyField.setAccessible(true);
            try {
                strategyField.set(controller, strategyButton);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            Polygon octTile = tile("OctB01", Color.web("#ffd675"));
            Polygon rhoTile = tile("RhoB01", Color.web("#bcbcbc"));

            BoardController.tiles.put("OctB01", octTile);
            BoardController.tiles.put("RhoB01", rhoTile);

            Node blackPathNode = new Node("OctB01");
            Node whitePathNode = new Node("RhoB01");

            Board.blackPath = new Stack<>();
            Board.blackPath.push(blackPathNode);

            Board.whitePath = new Stack<>();
            Board.whitePath.push(whitePathNode);

            controller.showStrategyButtonClicked();
            controller.showStrategyButtonClicked();

            assertEquals(Color.web("#ffd675"), octTile.getFill());
            assertEquals(Color.web("#bcbcbc"), rhoTile.getFill());
            assertFalse(controller.showStrategyToggled);
        });
    }

    @Test
    void showStrategyButtonClicked_doesNotChangeTilesOutsideSuggestedPaths() throws Exception {
        runOnFxThread(() -> {
            BoardController.DEV_MODE = true;

            Button strategyButton = new Button();
            Field strategyField = null;
            try {
                strategyField = BoardController.class.getDeclaredField("strategyButton");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            strategyField.setAccessible(true);
            try {
                strategyField.set(controller, strategyButton);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            Polygon untouchedTile = tile("OctC01", Color.web("#aa33aa"));
            BoardController.tiles.put("OctC01", untouchedTile);

            Board.blackPath = new Stack<>();
            Board.whitePath = new Stack<>();

            controller.showStrategyButtonClicked();
            controller.showStrategyButtonClicked();

            assertEquals(Color.web("#aa33aa"), untouchedTile.getFill());
        });
    }

    // check that the text for it displays show strategy before clicked
    @Test
    void showStrategyButtonClickedShowText() throws Exception {
        runOnFxThread(() -> {

            BoardController.DEV_MODE = true;

            // get button
            Button strategyButton = new Button();
            strategyButton.setText("SHOW STRATEGY");
            Field field = null;
            try {
                field = BoardController.class.getDeclaredField("strategyButton");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(true);
            try {
                field.set(controller, strategyButton);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            assertEquals("SHOW STRATEGY", strategyButton.getText() );
        });
    }

    // check that text displays hide strategy when clicked
    @Test
    void showStrategyButtonClickedHideText() throws Exception {
        runOnFxThread(() -> {

            BoardController.DEV_MODE = true;

            // get button
            Button strategyButton = new Button();
            strategyButton.setText("SHOW STRATEGY");
            Field field = null;
            try {
                field = BoardController.class.getDeclaredField("strategyButton");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(true);
            try {
                field.set(controller, strategyButton);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            controller.showStrategyButtonClicked();
            assertEquals("HIDE STRATEGY", strategyButton.getText() );
        });
    }

    // test that dev mode is needed for any changes to work
    @Test
    void showStrategyButtonClickedDevModeFalse() throws Exception {
        runOnFxThread(() -> {

            BoardController.DEV_MODE = false;

            // get button
            Button strategyButton = new Button();
            strategyButton.setText("SHOW STRATEGY");
            Field field = null;
            try {
                field = BoardController.class.getDeclaredField("strategyButton");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
            field.setAccessible(true);
            try {
                field.set(controller, strategyButton);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            // if method works text should change however as dev mode is false it should stay the same
            controller.showStrategyButtonClicked();
            assertEquals("SHOW STRATEGY", strategyButton.getText() );
        });
    }

    private static Polygon tile(String id, Color initialFill) {
        Polygon polygon = new Polygon();
        polygon.setId(id);
        polygon.setFill(initialFill);
        return polygon;
    }

}
