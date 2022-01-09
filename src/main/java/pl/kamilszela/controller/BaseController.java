package pl.kamilszela.controller;

import pl.kamilszela.AppManager;
import pl.kamilszela.view.ViewFactory;

public abstract class BaseController {
    protected AppManager appManager;
    protected ViewFactory viewFactory;

    public BaseController(AppManager appManager, ViewFactory viewFactory) {
        this.appManager = appManager;
        this.viewFactory = viewFactory;
    }
}
