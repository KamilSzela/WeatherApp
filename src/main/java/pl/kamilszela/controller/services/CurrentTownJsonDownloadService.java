package pl.kamilszela.controller.services;

import pl.kamilszela.AppManager;

public class CurrentTownJsonDownloadService extends JsonDownloadService{

    public CurrentTownJsonDownloadService(AppManager appManager) {
        super(appManager);
    }

    public void setCurrentTownForcastInAppManager(){
        appManager.setCurrentTownForcastJson(downloadedJson);
    }
}
