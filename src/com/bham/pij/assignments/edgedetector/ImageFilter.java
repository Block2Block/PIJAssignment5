package com.bham.pij.assignments.edgedetector;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class ImageFilter {

    public abstract Image filterImage(Image image);

    public abstract float[][] createFilter();

    public Color[][] applyFilter(Color[][] pixels, float[][] filter) {
        Color[][] filtered = new Color[pixels.length-2][pixels.length-2];
        for (int i = 0;i < filtered.length;i++) {
            for (int j = 0;j < filtered.length;j++) {
                double red = 0;
                double blue = 0;
                double green = 0;
                for (int k = 0; k < 3;k++) {
                    for (int l = 0; l < 3; l++) {
                        red += (pixels[i + k][j + l].getRed() * filter[k][l]);
                        green += (pixels[i + k][j + l].getGreen() * filter[k][l]);
                        blue += (pixels[i + k][j + l].getBlue() * filter[k][l]);
                    }
                }
                filtered[i][j] = new Color(((red < 0 || red > 1)?((red < 0)?0:1):red), ((green < 0 || green > 1)?((green < 0)?0:1):green), ((blue < 0 || blue > 1)?((blue < 0)?0:1):blue), 1);
            }
        }
        return filtered;
    }

    void saveImage(Color[][] pixels, String filename) {
        WritableImage wimg = new WritableImage(pixels.length, pixels.length);
        PixelWriter pw = wimg.getPixelWriter();
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels.length; j++) {
                pw.setColor(i, j, pixels[i][j]);
            }
        }
        BufferedImage bImage = SwingFXUtils.fromFXImage(wimg, null);
        try {
            ImageIO.write(bImage, "png", new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Color[][] getPixelData(Image image) {
        Color[][] pixelData = new Color[(int) image.getHeight()][(int) image.getWidth()];
        PixelReader pixelReader = image.getPixelReader();
        for (int i = 0;i < (int) image.getHeight(); i++) {
            for (int j = 0;j < (int) image.getWidth(); j++) {
                pixelData[i][j] = pixelReader.getColor(i, j);
            }
        }
        return pixelData;
    }

    public Color[][] getPixelDataExtended(Image image) {
        Color[][] extendedPixelData = new Color[(int) image.getHeight() + 2][(int) image.getWidth() + 2];
        Color[][] pixelData = getPixelData(image);
        for (int i = 0;i < (int) image.getHeight() + 2; i++) {
            for (int j = 0;j < (int) image.getWidth() + 2; j++) {
                if (j == 0 || j == (int) image.getWidth() + 1) {
                    extendedPixelData[i][j] = new Color(0, 0, 0, 1);
                } else if (i == 0 || i == (int) image.getHeight() + 1) {
                    extendedPixelData[i][j] = new Color(0, 0, 0, 1);
                } else {
                    extendedPixelData[i][j] = pixelData[i-1][j-1];
                }
            }
        }
        return extendedPixelData;
    }



}
