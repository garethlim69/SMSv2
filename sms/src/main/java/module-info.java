module sms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens sms to javafx.fxml;
    exports sms;
}
