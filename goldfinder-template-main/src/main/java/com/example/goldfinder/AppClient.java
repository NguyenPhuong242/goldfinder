package com.example.goldfinder;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.nio.Buffer;

import com.example.goldfinder.client.Client;

import java.io.*;

public class AppClient extends javafx.application.Application {
    private static final String VIEW_RESOURCE_PATH = "/com/example/goldfinder/gridView.fxml";
    private static final String APP_NAME = "Gold Finder";

    private Stage primaryStage;
    private Parent view;

    private void initializePrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(APP_NAME);
        this.primaryStage.setOnCloseRequest(event -> Platform.exit());
        this.primaryStage.setResizable(true);
        this.primaryStage.sizeToScene();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        initializePrimaryStage(primaryStage);
        initializeView();
        showScene();
    }

    private void initializeView() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL location = AppClient.class.getResource(VIEW_RESOURCE_PATH);
        loader.setLocation(location);
        view = loader.load();
        Controller controller = loader.getController();
        view.setOnKeyPressed(controller::handleMove);
        controller.initialize();

    }

    private void showScene() {
        Scene scene = new Scene(view);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(System.out, true);
            System.out.print("Enter your name: ");
            String name = in.readLine();
            Client client = new Client(name);
            launch(args);
        } catch (Exception e){
            e.printStackTrace();
        }
        // launch(args);
    }
}