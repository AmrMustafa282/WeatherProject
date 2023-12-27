package com.example.wetherproject;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net. http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.json.JSONObject;


public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #0F1011");
        Scene scene = new Scene(root, 1000, 800);


        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color:#0F1011");
        gridPane.setPadding(new Insets(20));
        gridPane.setHgap(10);


        Image headerImage = new Image("logo2.png");
        ImageView imageView = new ImageView(headerImage);
        imageView.setFitWidth(300);
        imageView.setFitHeight(300);
        imageView.setStyle("-fx-padding: 0 0 20 0");
        imageView.setPreserveRatio(true);
        gridPane.add(imageView, 0,0 );

        TextField locationField = new TextField();
        locationField.setMinWidth(760);
        locationField.setStyle("-fx-background-color: #161819");
        locationField.setPromptText("Enter location");
        gridPane.add(locationField, 0, 1);
        gridPane.setMargin(imageView, new Insets(0, 0, 30, 0));

        Button searchButton = new Button("Search");
        gridPane.add(searchButton, 0, 2);
        gridPane.setMargin(searchButton, new Insets(10));
        searchButton.setOnAction(e-> {
            String location = locationField.getText();

            String res =  makeAPICallByName(location);
            JSONObject jsonObject = new JSONObject(res);
                WeatherDetails weatherDetailsPage = new WeatherDetails(jsonObject);
                Stage stage = new Stage();
                weatherDetailsPage.start(stage);
        });



        String[][] Governorates = {
                {"الإسكندرية", "alex.png","Al Iskandariyah"},
                {"الإسماعيلية", "isma.png", "Al Isma'iliyah"},
                {"أسوان", "aswn.png", "Aswan"},
                {"أسيوط", "asy.png","Asyut"},
                {"الأقصر", "lux.png", "Luxor"},
                {"البحر الأحمر", "reds.png", "Al Bahr al Ahmar"},
                {"البحيرة", "bhra.png", "Al Buhayrah"},
                {"بني سويف", "baniswif.png", "Bani Suwayf"},
                {"بورسعيد", "ports.png", "Bur Sa'id"},
                {"جنوب سيناء", "gnobsina.png","Janub Sina'"},
                {"الجيزة", "giza.png", "Giza"},
                {"الدقهلية", "duch.png","Ad Daqahliyah"},
                {"دمياط", "domit.png", "Dumyat"},
                {"سوهاج", "sohg.png","Sohag"},  // api regon قنا
                {"السويس", "swiz.png","As Suways"},
                {"الشرقية", "shrqya.png","Ash Sharqiyah"},
                {"شمال سيناء", "shsina.png","Shamal Sina'"},
                {"الغربية", "8rbya.png","Al Gharbiyah"},
                {"الفيوم", "fyom.png","Al Fayyum"},
                {"القاهرة", "cairo.png","Al Qahirah"},
                {"القليوبية", "qlayo.png","Al Qalyubiyah"},
                {"قنا", "qina.png","Qina"},
                {"كفر الشيخ", "kfr.png","Kafr ash Shaykh"},
                {"مرسى مطروح", "mtroh.png","Matruh"},
                {"المنوفية", "menofia.png","Al Minufiyah"},
                {"المنيا", "minya.png","Al Minya"},
                {"الوادي الجديد", "newwady.png", "Al Wadi al Jadid"}
        };

        //Threads
        List<String> responses = new ArrayList<>();
        List<WeatherThread> threads = new ArrayList<>();

        for (int i=0 ;i<Governorates.length;i++) {
            String name = Governorates[i][2];

            WeatherThread thread = new WeatherThread(name, responses);
            threads.add(thread);
            thread.start();
        }
        for (WeatherThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        System.out.println(responses.get(0));


        FlowPane buttonPane = new FlowPane();
        buttonPane.setStyle("-fx-background-color:#0F1011");
        buttonPane.setHgap(40);
        buttonPane.setVgap(20);

        buttonPane.setPrefWrapLength(300);



        for (String jsonData : responses) {
            JSONObject jsonObject = new JSONObject(jsonData);
            JSONObject location = jsonObject.getJSONObject("location");
            String cityName = location.getString("name");
            String region = !location.getString("region").equals("") ? location.getString("region") : cityName;
            if(cityName.equals("Sohag")) region="Sohag";
            if(cityName.equals("Luxor")) region="Luxor";

            JSONObject current = jsonObject.getJSONObject("current");
            String conditionIconUrl = current.getJSONObject("condition").getString("icon");
            double currentTempC = current.getDouble("temp_c");

//            System.out.println(conditionIconUrl);

            GridPane gridPaneBtn = new GridPane();
            gridPaneBtn.setStyle("-fx-background-color: #161819; -fx-background-radius: 6");
            gridPaneBtn.setPadding(new Insets(20));
            gridPaneBtn.setHgap(10);


            String img_name = "";
            String gov_name = "";
            for (String[] gov : Governorates) {
                if (gov[2].equals(region)) {
                    img_name = gov[1];
                    gov_name = gov[0];
//                    System.out.println(gov[1]);
                    break;
                }else{
                    img_name = "alex.png";
                }
            }
            Image headerImageBtn = new Image(img_name);
            ImageView imageViewBtn = new ImageView(headerImageBtn);
            imageViewBtn.setPreserveRatio(true);
            imageViewBtn.setFitWidth(100);
            imageViewBtn.setFitHeight(100);

            Label lb = new Label(Double.toString(currentTempC).concat("°C"));
//            Label lb = new Label(cityName);

            lb.setMinWidth(40);
            lb.setStyle("-fx-padding: 10;-fx-text-fill: white;-fx-font-size: 20;-fx-font-weight: bold");



            Image iconImage = new Image("https:"+conditionIconUrl);
            ImageView iconView = new ImageView(iconImage);
            iconView.setFitWidth(100); // Adjust the width of the image
            iconView.setPreserveRatio(true);
            iconView.setFitWidth(50);
            iconView.setFitHeight(50);




            Button button = new Button(gov_name);
            button.setPadding(new Insets(10));
            button.setMinWidth(110);
            button.setStyle("-fx-background-color: #9e9e9e ;-fx-padding: 5;-fx-text-fill: black;-fx-font-weight: bold");
            button.setOnAction(e->{
                WeatherDetails weatherDetailsPage = new WeatherDetails(jsonObject);
                Stage stage = new Stage();
                weatherDetailsPage.start(stage);
            });

            Button saveToDB = new Button("save to DB");
            saveToDB.setPadding(new Insets(10));
            saveToDB.setMinWidth(110);
            saveToDB.setStyle("-fx-background-color: #9e9e9e ;-fx-padding: 5;-fx-text-fill: black;-fx-font-weight: bold");
            saveToDB.setOnAction(e->{
                SaveToDB DB = new SaveToDB(jsonObject);
                DB.start(new Stage());
                System.out.println(jsonObject.getJSONObject("current"));

            });

            HBox buttons = new HBox(10);
            buttons.getChildren().addAll(button,saveToDB);
            gridPaneBtn.add(imageViewBtn, 0, 0, 2, 1);
            gridPaneBtn.add(iconView, 3,0);
            gridPaneBtn.add(lb, 2, 0);
            gridPaneBtn.add(buttons, 0, 1, 2, 1);
//            gridPaneBtn.add(button, 0, 1);
//            gridPaneBtn.add(saveToDB,2,1,4,1);

            gridPaneBtn.setMargin(button, new Insets(5, 0, 0, 130));


            buttonPane.getChildren().add(gridPaneBtn);
        }


        ScrollPane scrollPane = new ScrollPane(buttonPane);
        scrollPane.setStyle("-fx-background-color: #0F1011");
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(600);
        scrollPane.setPrefViewportWidth(950);
        scrollPane.setPadding(new Insets(20));


        gridPane.add(scrollPane, 0, 3);

        GridPane.setHalignment(searchButton, HPos.CENTER);
//        GridPane.setHalignment(imageView, HPos.CENTER);

        root.setCenter(gridPane);
        primaryStage.setTitle("Weather App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private  String makeAPICallByName(String location){
        location=location.replaceAll(" ","+");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://weatherapi-com.p.rapidapi.com/forecast.json?q="+location+"&days=3"))
                .header("X-RapidAPI-Key", "5a3e87cbfemsha27df0b0e3de563p1e691djsn8212427c92ad")
                .header("X-RapidAPI-Host", "weatherapi-com.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        launch();
    }
}