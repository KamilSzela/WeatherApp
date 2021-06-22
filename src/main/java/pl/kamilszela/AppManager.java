package pl.kamilszela;

public class AppManager {

    private String currentTownForcastJson;
    private String destinationTownForcastJson;

    public String getCurrentTownForcastJson() {
        return currentTownForcastJson;
    }

    public void setCurrentTownForcastJson(String currentTownForcastJson) {
        this.currentTownForcastJson = currentTownForcastJson;
    }

    public String getDestinationTownForcastJson() {
        return destinationTownForcastJson;
    }

    public void setDestinationTownForcastJson(String destinationTownForcastJson) {
        this.destinationTownForcastJson = destinationTownForcastJson;
    }
}
