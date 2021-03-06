package pl.kamilszela.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import pl.kamilszela.Config;
import pl.kamilszela.controller.APICallResult;
import pl.kamilszela.controller.JsonDownloadResult;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class JsonDownloadService extends Service<APICallResult> {

    public String cityName;
    public String downloadedJson = new String();

    @Override
    protected Task<APICallResult> createTask() {
        return new Task<>() {
            @Override
            protected APICallResult call() throws Exception {
                try {
                    URL url = new URL("http://api.openweathermap.org/data/2" +
                            ".5/forecast?q=" + cityName + "&units=metric&appid=" + Config.key);
                    InputStream inputStream = url.openStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder resultBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        resultBuilder.append(line);
                        resultBuilder.append(System.lineSeparator());
                        if (line.equals("")) {
                            break;
                        }
                    }
                    reader.close();
                    downloadedJson = resultBuilder.toString();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    return new APICallResult(JsonDownloadResult.FAILED_BY_MALFORMED_URL, downloadedJson);
                } catch (Exception e) {
                    e.printStackTrace();
                    return new APICallResult(JsonDownloadResult.FAILED_BY_UNEXPECTED_ERROR, downloadedJson);
                }
                return new APICallResult(JsonDownloadResult.SUCCESS, downloadedJson);
            }
        };
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
