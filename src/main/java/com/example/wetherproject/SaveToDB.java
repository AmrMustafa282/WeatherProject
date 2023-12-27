package com.example.wetherproject;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import org.json.JSONObject;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;



public class SaveToDB extends Application {

//    private String json;
    private JSONObject jsonData;
    public SaveToDB(JSONObject jsonData){

        this.jsonData = jsonData;
    }

    @Override
    public void start(Stage primaryStage) {
        // Sample JSON data
//        String jsonDataString = "{\"feelslike_c\":20,\"uv\":5,\"last_updated\":\"2023-12-27 12:00\",\"feelslike_f\":68,\"wind_degree\":310,\"last_updated_epoch\":1703671200,\"is_day\":1,\"precip_in\":0,\"wind_dir\":\"NW\",\"gust_mph\":11.1,\"temp_c\":20,\"pressure_in\":30.09,\"gust_kph\":17.8,\"temp_f\":68,\"precip_mm\":0.03,\"cloud\":50,\"wind_kph\":6.8,\"condition\":{\"code\":1003,\"icon\":\"//cdn.weatherapi.com/weather/64x64/day/116.png\",\"text\":\"Partly cloudy\"},\"wind_mph\":4.3,\"vis_km\":6,\"humidity\":73,\"pressure_mb\":1019,\"vis_miles\":3}";
//        jsonData = new JSONObject(json);

         saveToDB();

//        StackPane root = new StackPane();
//        root.getChildren().add(saveButton);

//        Scene scene = new Scene(root, 300, 250);

//        primaryStage.setTitle("Save Data to MongoDB");
//        primaryStage.setScene(scene);
//        primaryStage.show();
    }

    public void saveToDB() {
        try (MongoClient mongoClient = MongoClients.create("mongodb+srv://amrdarwish115:w2Bu9QRvZ9NLcrMD@cluster0.ekqgwti.mongodb.net/?retryWrites=true&w=majority")) {
            MongoDatabase database = mongoClient.getDatabase("java"); // Replace with your database name
            MongoCollection<Document> collection = database.getCollection("Weather_Records"); // Replace with your collection name

            Document document = Document.parse(jsonData.toString());
            collection.insertOne(document);

            showAlert("Data saved into MongoDB");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Process failed");
        }
    }

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("MongoDB Status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
