/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagePixel;

import java.awt.Color;
import simplegui.*;

/**
 *
 * @author tue41582
 */
public class ImagePixel {

    static DrwImage img;

    public static Color color(int width, int height, int interval) {
        int red = 0;
        int green = 0;
        int blue = 0;
        for (int pwidth = width; pwidth < width + interval; pwidth++) {
            for (int pheight = height; pheight < height + interval; pheight++) {
                red = red + img.getPixelRGB(width, height).r;
                green = green + img.getPixelRGB(width, height).g;
                blue = blue + img.getPixelRGB(width, height).b;
            }
        }
        red = red / (interval * interval);
        green = green / (interval * interval);
        blue = blue / (interval * interval);
        Color c = new Color(red, green, blue);
        return c;
    }

    public static void main(String[] args) {
        SimpleGUI sg = new SimpleGUI();
        sg.labelButton1("Pixelate");
        int interval;
        img = new DrwImage("pooh4.jpg");
        sg.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), "pic");
        while (true) {
            sg.setTitle("Image Pixelization");
            sg.print("Input a number: ");
            sg.waitForButton1();
//            interval = (int) sg.keyReadDouble(true); //text input
            interval = sg.getSliderValue(); //slider
            if (interval == 0) {
                interval = 1;
            }
            sg.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), "pic");
            for (int width = 0; width < img.getWidth(); width += interval) {
                for (int height = 0; height < img.getHeight(); height += interval) {
                    sg.drawFilledBox(width, height, interval, interval, color(width, height, interval), 1, "");
                }
            }
        }
    }
}
