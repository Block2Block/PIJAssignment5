package com.bham.pij.assignments.edgedetector;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainMenu extends Application {

    private MenuManager menuManager;


    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Edge Detector");
        VBox root = new VBox();


        //Settings up the File menu, with the 2 items and their click functions.
        Menu fileMenu = new Menu("File");
        MenuItem open = new MenuItem("Open");
        MenuItem close = new MenuItem("Close");
        fileMenu.getItems().addAll(open, close);
        open.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Please select an image.");
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                menuManager.loadImageFile(file);
            }
        });
        close.setOnAction(actionEvent -> {
            menuManager.closeImage();
        });

        Menu toolsMenu = new Menu("Tools");
        MenuItem edgeDetection = new MenuItem("Edge Detection");
        MenuItem neonEdgeDetection = new MenuItem("Neon Edge Detection");
        MenuItem revert = new MenuItem("Revert");
        toolsMenu.getItems().addAll(edgeDetection, neonEdgeDetection, revert);
        edgeDetection.setOnAction(actionEvent -> {
            menuManager.filter(MenuManager.FilterType.NORMAL);
        });
        neonEdgeDetection.setOnAction(actionEvent -> {
            menuManager.filter(MenuManager.FilterType.NEON);
        });
        revert.setOnAction(actionEvent -> {
            menuManager.revert();
        });

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu,toolsMenu);

        menuManager = new MenuManager(root, menuBar);

        root.getChildren().add(menuBar);

        stage.setScene(new Scene(root, 500, 500));
        stage.show();
    }



}
