module org.base_datos_viajes {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.base_datos_viajes to javafx.fxml;
    exports org.base_datos_viajes;
}