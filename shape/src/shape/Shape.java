/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

import java.util.*;
import simplegui.SimpleGUI;
import java.io.*;

/**
 *
 * @author tue41582
 */
public class Shape {

    Pointer start, end, current;
    int size;

    public Shape() {
        start = null;
        end = null;
        size = 0;
    }

    public void insertFirst(int x, int y) {
        Pointer newNode = new Pointer(x, y, Double.POSITIVE_INFINITY);
        if (start == null) {
            start = newNode;
            end = start;
        } else {
            start.prev = newNode;
            newNode.next = start;
            start = newNode;
        }
        size++;
    }
    
    public void removeAt(int index) {
        if (index == 0) {
            if (size == 1) {
                start = null;
                end = null;
                size = 0;
                return;
            }
            start = start.next;
            start.prev = null;
            size--;
            return;
        }
        if (index == size-1) {
            end = end.prev;
            end.next = null;
            size--;
            return;
        }
        Pointer track = start.next;
        for (int i = 1; i <= size; i++) {
            if (i == index) {
                Pointer tempa = track.prev;
                Pointer tempb = track.next;
                tempa.next = tempb;
                tempb.prev = tempa;
                size--;
            }
            track = track.next;
        }
    }

    public void convert() {
        SimpleGUI sg = new SimpleGUI();
        current = start;
        while (current.next != null) {
            sg.drawLine(current.x + 25, 0 - current.y + 725, current.next.x + 25, 0 - current.next.y + 725);
            current = current.next;
        }
        sg.waitForButton1();
        sg.eraseAllDrawables();
        while (size > 38) {
            current = start.next;
            while (current.next != null) {
                if (current == end) {
                    current.weight = Double.POSITIVE_INFINITY;
                } else {
                    current.weight = importance(current);
                }
                current = current.next;
            }
            current = start;
            Pointer min = start.next;
            int count = 0;
            while (current.next != null) {
                if (current.weight <= min.weight){
                    min = current;
                }
                current = current.next;
                count++;
            }
            removeAt(count);
        }
        current = start;
        while (current.next != null) {
            sg.drawLine(current.x + 25, 0 - current.y + 725, current.next.x + 25, 0 - current.next.y + 725);
            current = current.next;
        }
    }

    public double importance(Pointer node) {
        double lp = Math.sqrt((node.prev.x - node.x) * (node.prev.x - node.x)
                            + (node.prev.y - node.y) * (node.prev.y - node.y));
        double pr = Math.sqrt((node.x - node.next.x) * (node.x - node.next.x)
                            + (node.y - node.next.y) * (node.y - node.next.y));
        double rl = Math.sqrt((node.next.x - node.prev.x) * (node.next.x - node.prev.x)
                            + (node.next.y - node.prev.y) * (node.next.y - node.prev.y));
        double weight = lp + pr - rl;
        return weight;
    }

    public static void main(String[] args) throws FileNotFoundException {
        try (Scanner file = new Scanner(new FileReader("shapelist.txt"))) {
            Shape deer = new Shape();
            while (file.hasNext()) {
                int x = file.nextInt();
                int y = file.nextInt();
                deer.insertFirst(x, y);
            }
            deer.convert();

        }
    }
}
