module PogodaNaLato {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires com.google.gson;

    opens pl.kamilszela;
    opens pl.kamilszela.controller;
    opens pl.kamilszela.view;
    opens pl.kamilszela.model;
}