module com.example.wetherproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.net.http;

    requires org.json;
    requires org.mongodb.bson;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.driver.core;

    opens com.example.wetherproject to javafx.fxml;
    exports com.example.wetherproject;
}