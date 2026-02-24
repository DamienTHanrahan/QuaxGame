package COMP20050.Group4QuaxGame;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.shape.Polygon;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BoardControllerTest {

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
        BoardController controller = new BoardController();

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
                setField(controller, field, polygon);
                injected.put(field.getName(), polygon);
            } else if (field.getType() == Label.class) {
                setField(controller, field, new Label());
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
}