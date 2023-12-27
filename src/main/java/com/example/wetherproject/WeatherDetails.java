package com.example.wetherproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;


public class WeatherDetails extends Application {

    private JSONObject weatherData;

    public WeatherDetails(JSONObject weatherData) {
        this.weatherData = weatherData;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Weather Details");

        VBox root = new VBox();
        root.setPadding(new Insets(20));
        root.setSpacing(20);

        GridPane container = new GridPane(); // the whole box
        container.setHgap(30);
        container.setVgap(30);

        //  Current
        JSONObject currentWeatherToday = weatherData.getJSONObject("current");
        double tempC = currentWeatherToday.getDouble("temp_c");
        String conditionText = currentWeatherToday.getJSONObject("condition").getString("text");
        int humidity = currentWeatherToday.getInt("humidity");
        double windKph = currentWeatherToday.getDouble("wind_kph");
        String cityName = weatherData.getJSONObject("location").getString("name");
        String region = weatherData.getJSONObject("location").getString("region");
        String iconUrl = currentWeatherToday.getJSONObject("condition").getString("icon");
        String cdayDate = weatherData.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0).getString("date");

        GridPane currentPane = new GridPane();    // first container
        currentPane.setPadding(new Insets(10));
        currentPane.setStyle("-fx-background-radius: 12; -fx-background-color: #1D1B1F;-fx-padding: 20");
        currentPane.setVgap(10);


        Label nowLabel = new Label("Now");
        nowLabel.setStyle("-fx-font-size: 20;-fx-font-weight: bold; -fx-text-fill: white");
        currentPane.add(nowLabel, 0, 0);

        Label tempLabel = new Label(tempC+"°C");
        tempLabel.setStyle("-fx-font-size: 40;-fx-padding: 20 100 0 0;-fx-font-weight: bold;-fx-text-fill: white");

        ImageView iconView = new ImageView(new Image("https:"+iconUrl));
        iconView.setFitWidth(100);
        iconView.setFitHeight(100);
        HBox tempIconBox = new HBox(10, tempLabel, iconView);
        currentPane.add(tempIconBox, 0, 1);

        Label conditionLabel = new Label(conditionText);
        conditionLabel.setStyle("-fx-text-fill: white");
        currentPane.add(conditionLabel, 0, 2);

        Line line = new Line(0, 0, 340, 0);
        line.setStroke(Color.web("#9e9e9e"));
        currentPane.add(line, 0, 3);

        ImageView dateImage = new ImageView(new Image("caln.png"));
        dateImage.setFitWidth(20);
        dateImage.setFitHeight(20);
        Label dateLabel = new Label(cdayDate);
        dateLabel.setStyle("-fx-text-fill: #9e9e9e");
        HBox dateBox = new HBox(10, dateImage, dateLabel);
        currentPane.add(dateBox, 0, 4);

        ImageView locImage = new ImageView(new Image("loc.png"));
        locImage.setFitWidth(20);
        locImage.setFitHeight(20);
        Label cityRegionLabel = new Label(cityName+", "+region);
        cityRegionLabel.setStyle("-fx-text-fill: #9e9e9e");
        HBox cityRegionBox = new HBox(10, locImage, cityRegionLabel);
        currentPane.add(cityRegionBox, 0, 5);
//      -----------------------------------------------------------



        //  Today's Highlights
        JSONObject todayHighlight = weatherData.getJSONObject("current");
        Label todaysHighlitstx = new Label("Todays Highlights");
        todaysHighlitstx.setStyle("-fx-font-size: 20;-fx-font-weight: bold;-fx-text-fill: white; -fx-padding: 0 0 5 0");
        GridPane todayHighlightBox = new GridPane();
        todayHighlightBox.setPadding(new Insets(10));
        todayHighlightBox.setStyle("-fx-background-radius: 12;-fx-background-color: #1D1B1F ;-fx-padding: 20");



        double pressureMb = todayHighlight.getDouble("pressure_mb");
        int cloud = todayHighlight.getInt("cloud");
        double feelsLikeC = todayHighlight.getDouble("feelslike_c");
        double visibilityKm = todayHighlight.getDouble("vis_km");
        int uv = todayHighlight.getInt("uv");
        double gustKph = todayHighlight.getDouble("gust_kph");

        JSONObject forecastdayh = weatherData.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0);
        JSONObject astro = forecastdayh.getJSONObject("astro");
        String sunrise = astro.getString("sunrise");
        String sunset = astro.getString("sunset");

        // Air quality index
        GridPane air = new GridPane();
        air.setVgap(20);
        air.setStyle("-fx-background-color: #1A191D ;-fx-padding: 20; -fx-background-radius: 6;");
        Label airLb = new Label("Air Quality Index");
        airLb.setStyle("-fx-font-size: 15; -fx-padding:0 0 5 0;-fx-text-fill: #9e9e9e");
        HBox airHBox = new HBox(50);


        ImageView airImg = new ImageView(new Image("wind.png"));
        airImg.setFitWidth(30);
        airImg.setFitHeight(30);
        airImg.setStyle("-fx-padding: 20 0 0 0");

        VBox windBox = new VBox(10);
        Label windLb = new Label("Wind");
        Label windVal = new Label(Double.toString(windKph).concat("km/h"));
        windLb.setStyle("-fx-text-fill: #9e9e9e ;-fx-font-size: 12");
        windVal.setStyle("-fx-text-fill: white; -fx-font-size: 25 ");
        windBox.getChildren().addAll(windLb,windVal);

        VBox cloudBox = new VBox(10);
        Label cloudLb = new Label("Cloud");
        Label cloudVal = new Label(Integer.toString(cloud).concat("%"));
        cloudLb.setStyle("-fx-text-fill: #9e9e9e ;-fx-font-size: 12");
        cloudVal.setStyle("-fx-text-fill: white; -fx-font-size: 25 ");
        cloudBox.getChildren().addAll(cloudLb, cloudVal);

        VBox gustBox = new VBox(10);
        Label gustLb = new Label("Gust");
        Label gustVal = new Label(Double.toString(gustKph).concat("km/h"));
        gustLb.setStyle("-fx-text-fill: #9e9e9e ;-fx-font-size: 12");
        gustVal.setStyle("-fx-text-fill: white; -fx-font-size: 25 ");
        gustBox.getChildren().addAll(gustLb, gustVal);

        VBox uVBox = new VBox(10);
        Label uvLb = new Label("UV");
        Label uvVal = new Label(Integer.toString(uv).concat("%"));
        uvLb.setStyle("-fx-text-fill: #9e9e9e ;-fx-font-size: 12");
        uvVal.setStyle("-fx-text-fill: white; -fx-font-size: 25 ");
        uVBox.getChildren().addAll(uvLb, uvVal);


        airHBox.getChildren().addAll(airImg,windBox,cloudBox,gustBox,uVBox);
        air.add(airLb,0,0);
        air.add(airHBox,0,1);


        // Astronomy

        GridPane ast = new GridPane();
        ast.setVgap(20);
        ast.setStyle("-fx-background-color: #1A191D ;-fx-padding: 20; -fx-background-radius: 6;");
        Label astLb = new Label("Astronomy");
        astLb.setStyle("-fx-font-size: 15; -fx-padding:0 0 5 0;-fx-text-fill: #9e9e9e");
        HBox astHBox = new HBox(80);


        ImageView sunImg = new ImageView(new Image("sun.png"));
        sunImg.setFitWidth(30);
        sunImg.setFitHeight(30);
        sunImg.setStyle("-fx-padding: 100 0 0 0");

        VBox sunriseb = new VBox(10);
        Label sunriseLb = new Label("Sunrise");
        Label sunriseVal = new Label(sunrise.concat("\nAM"));
        sunriseLb.setStyle("-fx-text-fill: #9e9e9e ;-fx-font-size: 12");
        sunriseVal.setStyle("-fx-text-fill: white; -fx-font-size: 25 ");
        sunriseb.getChildren().addAll(sunriseLb,sunriseVal);

        ImageView moonImg = new ImageView(new Image("moon.png"));
        moonImg.setFitWidth(30);
        moonImg.setFitHeight(30);
        moonImg.setStyle("-fx-padding: 20 0 0 0");

        VBox sunsetb = new VBox(15);
        Label sunsetLb = new Label("Sunset");
        Label sunsetVal = new Label(sunset.concat("\nPM"));
        sunsetLb.setStyle("-fx-text-fill: #9e9e9e ;-fx-font-size: 12");
        sunsetVal.setStyle("-fx-text-fill: white; -fx-font-size: 25 ");
        sunsetb.getChildren().addAll(sunsetLb, sunsetVal);


        HBox sunbox = new HBox(20);
        sunbox.getChildren().addAll(sunImg,sunriseb);
        HBox moonbox = new HBox(20);
        moonbox.getChildren().addAll(moonImg,sunsetb);
        astHBox.getChildren().addAll(sunbox,moonbox);
        ast.add(astLb,0,0);
        ast.add(astHBox,0,1);


        // States
        String[][] states = {
                {"Humidity", "hum.png",String.valueOf(humidity),"%"},
                {"Pressure", "pre.png",String.valueOf(pressureMb),"hPa"},
                {"Visibility", "vis.png",String.valueOf(visibilityKm),"km"},
                {"Fells Like", "temp.png",String.valueOf(feelsLikeC),"°C"}
        };

        HBox statesBox = new HBox(20);

        for(String []state :states){
            GridPane statePane = new GridPane();
            statePane.setVgap(20);
            statePane.setStyle("-fx-padding: 20;-fx-background-radius: 6;-fx-background-color: #1A191D");
            Label stateLb = new Label(state[0]);
            stateLb.setStyle("-fx-text-fill: #9e9e9e ;-fx-font-size: 12");
            ImageView stateImg = new ImageView(new Image(state[1]));
            stateImg.setFitHeight(30);
            stateImg.setFitWidth(30);
            Label mark = new Label(state[3]);
            mark.setStyle("-fx-font-size: 6");
            Label stateVal = new Label(state[2]+mark.getText());
            stateVal.setStyle("-fx-text-fill: white ;-fx-font-size: 15");
            statePane.add(stateLb,0,0);
            statePane.add(stateImg,0,1);
            statePane.add(stateVal,1,1);
            statesBox.getChildren().add(statePane);
        }




        todayHighlightBox.add(todaysHighlitstx,0,0);
        todayHighlightBox.add(air , 0,1);
        todayHighlightBox.add(ast,0,2);
        todayHighlightBox.add(statesBox,0,3);
        todayHighlightBox.setVgap(30);
        // ----------------------------------------------------------------------------
        // FORECAST


        GridPane forecastGrid = new GridPane();
        forecastGrid.setPadding(new Insets(10));
        forecastGrid.setStyle("-fx-background-radius: 12;-fx-background-color: #1D1B1F ;-fx-padding: 20");

        Label forcastLabel = new Label("3-Days Forecast");
        forcastLabel.setStyle("-fx-font-size: 20;-fx-font-weight: bold;-fx-text-fill: white");
        forecastGrid.add(forcastLabel, 0,0);

        JSONArray threeDayForecast = weatherData.getJSONObject("forecast").getJSONArray("forecastday");

        for (int i = 0; i < threeDayForecast.length(); i++) {
            JSONObject fdayInfo = threeDayForecast.getJSONObject(i);
            double ftempC = fdayInfo.getJSONObject("day").getDouble("avgtemp_c");
            String dayDate = fdayInfo.getString("date");
            String ficonUrl = fdayInfo.getJSONObject("day").getJSONObject("condition").getString("icon");

            LocalDate date = LocalDate.parse(dayDate);
            int dayNumber = date.getDayOfMonth();
            String monthName = date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            String dayName = date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);

            GridPane innerGrid = new GridPane();
            innerGrid.setHgap(80);

            HBox hBox1 = new HBox();
            ImageView ficonView = new ImageView(new Image("https:"+ficonUrl));
            ficonView.setFitHeight(50);
            ficonView.setFitWidth(50);
            Label temp = new Label(Double.toString(ftempC).concat("°"));
            temp.setStyle("-fx-font-size: 20 ; -fx-font-weight: bold; -fx-padding: 6 0 0 0;-fx-text-fill: white");
            hBox1.getChildren().addAll(ficonView, temp);

            Label fdateLabel = new Label(dayNumber+" "+monthName);
            fdateLabel.setStyle("-fx-text-fill: #9e9e9e");
            Label fdateLabel2 = new Label(dayName);
            fdateLabel2.setStyle("-fx-text-fill: #9e9e9e");

            innerGrid.add(hBox1,0,0);
            innerGrid.add(fdateLabel,1,0);
            innerGrid.add(fdateLabel2,2,0);
            innerGrid.setPadding(new Insets(10));
            innerGrid.setHgap(40);
            innerGrid.setVgap(25);

            forecastGrid.add(innerGrid, 0, i+1);
        }


        container.add(currentPane,0,0);
        container.add(todayHighlightBox, 1, 0, 1, 2);
        container.add(forecastGrid,0,1);

        root.getChildren().addAll(container);
        root.setStyle("-fx-background-color: #131216");

        Scene scene = new Scene(root, 1090, 650);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}

