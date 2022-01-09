package pl.kamilszela.controller;

public class APICallResult {

    JsonDownloadResult result;
    String downloadedJSON;

    public APICallResult(JsonDownloadResult result, String downloadedJSON) {
        this.result = result;
        this.downloadedJSON = downloadedJSON;
    }

    public JsonDownloadResult getResult() {
        return result;
    }

    public String getDownloadedJSON() {
        return downloadedJSON;
    }
}
