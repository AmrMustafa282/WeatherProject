package com.example.wetherproject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

class WeatherThread extends Thread {
    private static final String API_KEY = "5a3e87cbfemsha27df0b0e3de563p1e691djsn8212427c92ad";
    private final String name;
    private final List<String> responses;

    public WeatherThread(String name ,List<String> responses) {
        this.name = name;
        this.responses = responses;
    }

    @Override
    public void run() {
        String response = makeAPICallByName(name);
        responses.add(response);
    }

private String makeAPICallByName(String location){
    location=location.replaceAll(" ","+");
    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://weatherapi-com.p.rapidapi.com/forecast.json?q="+location+"&days=3"))
            .header("X-RapidAPI-Key", API_KEY)
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
//    System.out.println(response.body());
}
}


