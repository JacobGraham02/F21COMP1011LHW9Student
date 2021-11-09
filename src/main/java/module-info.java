module com.example.f21comp1011w9prep {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    
    opens com.example.f21comp1011w9prep to javafx.fxml;
    exports com.example.f21comp1011w9prep;
    exports com.example.f21comp1011w9prep.Controllers;
    opens com.example.f21comp1011w9prep.Controllers to javafx.fxml;
    exports com.example.f21comp1011w9prep.Models;
    opens com.example.f21comp1011w9prep.Models to javafx.fxml;
}