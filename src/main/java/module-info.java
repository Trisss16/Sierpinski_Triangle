module com.example.sierpinskitriangle {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.sierpinskitriangle to javafx.fxml;
    exports com.example.sierpinskitriangle;
}