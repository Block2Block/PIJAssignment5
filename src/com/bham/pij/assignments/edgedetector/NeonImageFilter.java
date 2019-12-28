package com.bham.pij.assignments.edgedetector;

import javafx.scene.image.Image;

import java.io.File;

public class NeonImageFilter extends ImageFilter {

    public Image filterImage(Image image) {
        saveImage(applyFilter(getPixelDataExtended(image), createFilter()), "filteredNeon.png");
        return new Image("file:" + new File("filteredNeon.png"));
    }

    public float[][] createFilter() {
        return new float[][]{{-1,-1,-1},{-1,8,-1},{-1,-1,-1}};
    }
}
