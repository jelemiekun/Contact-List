module com.example.fxcontactnumber3rd {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.fxcontactnumber3rd to javafx.fxml;
    exports com.example.fxcontactnumber3rd;
}