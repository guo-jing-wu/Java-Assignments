/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.hashtable;

import java.awt.Color;
import simplegui.*;

/**
 *
 * @author tue41582
 */
public class VisualHashtable implements GUIListener {

    static SimpleGUI sg;
    static int size = 150;

    public VisualHashtable() {
        sg = new SimpleGUI(1110, 40);
        sg.setBackgroundColor(Color.BLUE);
        Hashtable ht = new Hashtable(size);
        for (int i = 0; i < size; i++) {
            sg.drawFilledBox(i * 11 + 4, 12, 10, 10, Color.black, 1.0, null);
        }
        sg.labelButton1("NEXT10");
        sg.labelButton2("RESET");
        sg.registerToGUI(this);
    }

    public static Color computeCellColor(int col) {
        int r = (int) (255.0 / 7.0 * col);
        r = (int) Math.min(r, 255);
        int g = 255 - r;
        int b = 0;
        return (new Color(r, g, b));
    }

    public static void collisionColor(int index) {
        sg.drawFilledBox(index * 11 + 4, 12, 10, 10, computeCellColor(Hashtable.collisiontable[index]), 1.0, null);
    }

    @Override
    public void reactToButton1() {
        int counter = 0;
        int add = 0;
        int average = 0;
        if (counter < size) {
            for (int j = 0 + add; j < 10 + add; j++) {
                double random = Math.random() * 10*size;
                Hashtable.insert(random);
                collisionColor(Hashtable.currentIndex);
                counter++;
                for (int t = 0; t < size; t++) {
                    average += Hashtable.collisiontable[t];
                }
                sg.print("Collision average: " + Math.round(average / size));
            }
        }
        add += 10;
    }

    @Override
    public void reactToButton2() {
        for (int t = 0; t < size; t++) {
            sg.eraseAllDrawables();
            for (int i = 0; i < size; i++) {
                sg.drawFilledBox(i * 11 + 4, 12, 10, 10, Color.black, 1.0, null);
                Hashtable.hashtable[i] = 0;
                Hashtable.collisiontable[i] = 0;
            }
        }
    }

    public static void main(String[] args) {
        new VisualHashtable();
    }

    @Override
    public void reactToSwitch(boolean bln) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void reactToSlider(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
