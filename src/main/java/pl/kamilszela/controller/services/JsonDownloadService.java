package pl.kamilszela.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.kamilszela.Config;
import pl.kamilszela.controller.JsonDownloadResult;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class JsonDownloadService extends Service {

    private String cityName;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                try{
                    URL url = new URL("http://api.openweathermap.org/data/2" +
                            ".5/forecast?q=" + cityName + "&units=metric&appid=" + Config.key);
                    InputStream inputStream = url.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder resultBuilder = new StringBuilder();
                    String line;
                    while( (line = reader.readLine()) != null){
                        resultBuilder.append(line);
                        resultBuilder.append(System.lineSeparator());
                        if(line == "") {
                            break;
                        }
                    }
                    reader.close();
                    System.out.println(resultBuilder.toString());
                    return JsonDownloadResult.SUCCESS;
                } catch (MalformedURLException e){
                    e.printStackTrace();
                    return JsonDownloadResult.FAILED_BY_MALFORMED_URL;
                }
                catch (Exception e){
                    e.printStackTrace();
                    return JsonDownloadResult.FAILED_BY_UNEXPECTED_ERROR;
                }
            }
        };
    }
}
