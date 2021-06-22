package pl.kamilszela.controller.services;

import pl.kamilszela.AppManager;

public class DestinationTownJsonDownloadService extends JsonDownloadService{

    public DestinationTownJsonDownloadService(AppManager appManager) {
        super(appManager);
    }

    public void setForecastInAppManager(){
        appManager.setDestinationTownForcastJson(downloadedJson);
    }

}
