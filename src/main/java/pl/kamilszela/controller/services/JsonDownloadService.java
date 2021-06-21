package pl.kamilszela.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.kamilszela.AppManager;
import pl.kamilszela.Config;
import pl.kamilszela.controller.JsonDownloadResult;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class JsonDownloadService extends Service {

    public String cityName;
    public AppManager appManager;
    public String downloadedJson;

    public JsonDownloadService(AppManager appManager){
        this.appManager = appManager;
    }

    public void setAppManager(AppManager appManager) {
        this.appManager = appManager;
    }

    @Override
    protected Task<JsonDownloadResult> createTask() {
        return new Task<JsonDownloadResult>() {
            @Override
            protected JsonDownloadResult call() throws Exception {
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
                    downloadedJson = resultBuilder.toString();
                } catch (MalformedURLException e){
                    e.printStackTrace();
                    return JsonDownloadResult.FAILED_BY_MALFORMED_URL;
                }
                catch (Exception e){
                    e.printStackTrace();
                    return JsonDownloadResult.FAILED_BY_UNEXPECTED_ERROR;
                }
                return JsonDownloadResult.SUCCESS;
            }
        };
    }

    public void setForecastInAppManager(){}

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
