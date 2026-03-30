module com.example.demo {
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires javafx.web;
    requires jdk.jsobject;
    requires com.gluonhq.maps;
    requires org.json;
    requires java.mail;
    requires jdk.httpserver;
    requires com.google.api.client.auth;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;

    opens tn.chouflifilm.entities to javafx.base;
    exports tn.chouflifilm.controller.user;
    exports tn.chouflifilm.controller.Association;

    exports tn.chouflifilm.services to javafx.graphics;

    requires jbcrypt;
    requires lingua;
    requires org.controlsfx.controls;
    requires java.net.http;
    requires java.desktop;
    requires org.checkerframework.checker.qual;
    requires de.jensd.fx.glyphs.fontawesome;


    // Modules Google OAuth



    // Permet d'ouvrir ce package à javafx.fxml pour permettre l'accès au contrôleur
    opens tn.chouflifilm.controller.user to javafx.fxml;
    opens tn.chouflifilm.controller.Association to javafx.fxml;
    opens tn.chouflifilm.controller.Ressource to javafx.fxml;
    opens tn.chouflifilm.controller.reclamation to javafx.fxml;
    opens tn.chouflifilm.controller.Reponse to javafx.fxml;
    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}