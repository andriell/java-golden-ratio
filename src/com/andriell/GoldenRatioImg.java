package com.andriell;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Created by Андрей on 04.11.2016.
 */
public class GoldenRatioImg extends BufferedImage {
    private final double PI2 = Math.PI / 2;
    private final double Y = 1 / (Math.sqrt(1.25) + 0.5);
    private Graphics2D g;

    private Color backgroundColor = Color.white;
    private Color color = Color.blue;
    private int[] colorArray = new int[4];

    public GoldenRatioImg(int width, int height) {

        super(width, height, BufferedImage.TYPE_4BYTE_ABGR);

        g = (Graphics2D) getGraphics();
        g.setColor(backgroundColor);
        g.fillRect(0, 0, width, height);

        printSpiral(1000, 1000, 0, 0, 0);
        color = Color.red;
        printSpiral(1000, 1000, 1, 525, -260);
        color = Color.green;
        printSpiral(1000, 1000, 3, 525, -260);
    }

    private void printSpiral(int width, int height, int position, int x, int y) {
        double r = Math.min(width, height) - 50;
        switch (position % 4) {
            case 0:
                x += r;
                y += r * Y;
                break;
            case 1:
                y += r;
                break;
            case 2:
                break;
            case 3:
                x += r * Y;
                break;
        }

        colorArray[0] = (color.getRGB() >> 24) & 0xff;
        colorArray[1] = (color.getRGB() >> 16) & 0xff;
        colorArray[2] = (color.getRGB() >> 8) & 0xff;
        colorArray[3] = (color.getRGB()) & 0xff;

        while (true) {
            r = r * Y;
            if (r <= 1) {
                break;
            }
            switch (position % 4) {
                case 0:
                    x -= r * Y;
                    break;
                case 1:
                    y -= r * Y;
                    break;
                case 2:
                    x += r * Y;
                    break;
                case 3:
                    y += r * Y;
                    break;
            }
            printCircle(x, y, r, position++);
        }
    }

    double l = 0;

    private void printCircle(double x, double y, double r, int i) {
        double step = 1 / r, da = 0;
        switch (i % 4) {
            case 0:
                da = Math.PI;
                break;
            case 1:
                da = -PI2;
                break;
            case 2:
                da = 0;
                break;
            case 3:
                da = PI2;
                break;
        }
        double or = r;
        for (double a = 0; a <= PI2; a += step) {
            r = or;
            paint(x + r * Math.cos(a + da), y + r * Math.sin(a + da));
            l += or * Math.sin(step);
        }
    }

    private void paint(int x, int y, double a) {
        if (x >= getWidth() || y >= getHeight() || x < 0 || y < 0) {
            return;
        }
        int[] pixelArray = new int[4];
        int pixelColor = getRGB(x, y);
        if (a > 0) {
            pixelArray[1] = (pixelColor >> 16) & 0xff;
            pixelArray[2] = (pixelColor >> 8) & 0xff;
            pixelArray[3] = (pixelColor) & 0xff;

            pixelArray[1] = (int) (pixelArray[1] * (1 - a) + colorArray[1] * a);
            pixelArray[2] = (int) (pixelArray[2] * (1 - a) + colorArray[2] * a);
            pixelArray[3] = (int) (pixelArray[3] * (1 - a) + colorArray[3] * a);

            pixelColor = ((255 & 0xFF) << 24) |
                    ((pixelArray[1] & 0xFF) << 16) |
                    ((pixelArray[2] & 0xFF) << 8)  |
                    ((pixelArray[3] & 0xFF));

            setRGB(x, y, pixelColor);
        }
    }

    private void paint(double x, double y) {
        int xi, yi;
        double a, xd, yd;
        xi = (int) x;
        yi = (int) y;
        xd = x - xi;
        yd = y - yi;

        a = (1 - xd) * (1 - yd);
        paint(xi, yi, a);
        a = xd * (1 - yd);
        paint(xi + 1, yi, a);
        a = (1 - xd) * yd;
        paint(xi, yi + 1, a);
        a = xd * yd;
        paint(xi + 1, yi + 1, a);
    }
}
