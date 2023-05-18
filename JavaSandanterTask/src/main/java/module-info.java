module com.tkachev.javasandantertask {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.tkachev.javasandantertask to javafx.fxml;
    exports com.tkachev.javasandantertask;
}