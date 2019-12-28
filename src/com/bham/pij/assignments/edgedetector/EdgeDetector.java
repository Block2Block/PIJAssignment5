package com.bham.pij.assignments.edgedetector;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.File;

public class EdgeDetector extends ImageFilter {

    public Image filterImage(Image image) {
        saveImage(applyFilter(applyGreyscale(getPixelDataExtended(image)), createFilter()), "filtered.png");
        return new Image("file:" + new File("filtered.png"));
    }

    public float[][] createFilter() {
        return new float[][]{{-1,-1,-1},{-1,8,-1},{-1,-1,-1}};
    }

    public Color[][] applyGreyscale(Color[][] pixels) {
        Color[][] greyscale = new Color[pixels.length][pixels.length];
        for (int i = 0;i < pixels.length;i++) {
            for (int j = 0;j < pixels.length;j++) {
                Color pixel = pixels[i][j];
                double average = (pixel.getBlue() + pixel.getGreen() + pixel.getRed()) /3d;
                greyscale[i][j] = new Color(average, average, average, 1);
            }
        }
        return greyscale;
    }

}
