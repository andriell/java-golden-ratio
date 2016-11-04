package com.andriell;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Created by Андрей on 03.11.2016.
 */
public class Main3 {
    public static void main(String[] args) {
        new Main3().go();
    }

    public void go() {
        JFrame frame = new JFrame("Gui");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 350);
        frame.setContentPane(new Image(1600, 1600));

        frame.setVisible(true);
    }

    class Image extends JPanel {
        private GoldenRatioImg image;


        public Image(int width, int height) {

            super();
            image = new GoldenRatioImg(width, height);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
        }
    }

}
