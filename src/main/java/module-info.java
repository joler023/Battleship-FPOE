module com.battleship.battleshipfpoe {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.battleship.battleshipfpoe to javafx.fxml;
    opens com.battleship.battleshipfpoe.controller to javafx.fxml;
    exports com.battleship.battleshipfpoe;
}