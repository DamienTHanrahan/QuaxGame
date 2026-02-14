module COMP20050.Group4QuaxGame {
    requires javafx.controls;
    requires javafx.fxml;


    opens COMP20050.Group4QuaxGame to javafx.fxml;
    exports COMP20050.Group4QuaxGame;
}