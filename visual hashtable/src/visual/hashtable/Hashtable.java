/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.hashtable;

/**
 *
 * @author tue41582
 */
public class Hashtable {

    static double[] hashtable;
    static int[] collisiontable;
    static int currentIndex;
    static int size;

    public Hashtable(int size) {
        hashtable = new double[size];
        collisiontable = new int[size];
        this.size = size;
    }

    public static void insert(double v) {
        int collision = 0;
        double hashfunction = Math.round(v);
        int index = (int) (hashfunction % size);
        boolean insert = false;
        while (!insert) {
            if (hashtable[index] == 0) {
                hashtable[index] = v;
                insert = true;
            } else if (index == size - 1 && hashtable[index] != 0) {
                index = 0;
                collision++;
            } else {
                index++;
                collision++;
            }
        }
        collisiontable[index] = collision;
        currentIndex = index;
    }
}
