package com.bham.pij.assignments.edgedetector;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;

public class MenuManager {

    public enum FilterType {NEON,NORMAL}

    private Pane root;
    private ImageView image;
    private MenuBar menuBar;
    private ImageView prefilteredImage;


    public MenuManager(Pane pane, MenuBar menuBar) {
        this.root = pane;
        this.menuBar = menuBar;
        disableMenuItems();
    }

    public void loadImageFile(File file) {
        Image image = new Image("file:" + file.getPath());
        ImageView imageView = new ImageView(image);
        if (this.image != null) {
            root.getChildren().remove(this.image);
        }
        this.image = imageView;
        root.getChildren().add(imageView);
        enableMenuItems(false);
    }

    public void loadImageFile(Image image) {
        ImageView imageView = new ImageView(image);
        if (this.image != null) {
            root.getChildren().remove(this.image);
        }
        this.image = imageView;
        root.getChildren().add(imageView);
        enableMenuItems(true);
    }

    public void closeImage() {
        ObservableList<Node> nodes = FXCollections.observableArrayList(root.getChildren());

        disableMenuItems();

        root.getChildren().remove(image);
    }

    public void disableMenuItems() {
        for (Menu menu : menuBar.getMenus()) {
            for (MenuItem menuItem : menu.getItems()) {
                if (!menuItem.getText().equals("Open")) {
                    menuItem.setDisable(true);
                }
            }
        }
    }

    public void enableMenuItems(boolean filtered) {
        for (Menu menu : menuBar.getMenus()) {
            for (MenuItem menuItem : menu.getItems()) {
                if (menuItem.getText().equals("Revert") && !filtered) {
                    continue;
                }
                menuItem.setDisable(false);
            }
        }
    }

    public void filter(FilterType filterType) {
        ImageFilter filter;
        if (filterType == FilterType.NEON) {
            filter = new NeonImageFilter();
        } else {
            filter = new EdgeDetector();
        }
        prefilteredImage = image;
        closeImage();
        loadImageFile(filter.filterImage(image.getImage()));
    }

    public void revert()
    {
        if (prefilteredImage != null) {
            closeImage();
            image = prefilteredImage;
            prefilteredImage = null;
            root.getChildren().add(image);
            enableMenuItems(false);
        }
    }

}
