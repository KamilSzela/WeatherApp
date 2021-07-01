package pl.kamilszela.controller;

import pl.kamilszela.AppManager;
import pl.kamilszela.view.ViewFactory;

public abstract class BaseController {
    public AppManager appManager;
    public ViewFactory viewFactory;

    public BaseController(AppManager appManager, ViewFactory viewFactory) {
        this.appManager = appManager;
        this.viewFactory = viewFactory;
    }
}
