/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heapsort;

import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author tue41582
 */
public class HeapSort {

    public static int findAppropriateNumber(int n) {
        double time = 0;
        boolean repeat = false;
        Random r = new Random();
        while (!repeat) {
            if (time >= 1.0 && time <= 1.5) {
                repeat = true;
            } else if (time < 1.0) {
                int[] dum = new int[n];
                long start = System.nanoTime();
                for (int i = 0; i < dum.length; i++) {
                    dum[i] = r.nextInt(100);
                }
                Heap.sort(dum);
                long end = System.nanoTime();
                time = (end - start) / 1e9;
                n = n * 2;
            } else if (time > 1.5) {
                int[] dum = new int[n];
                long start = System.nanoTime();
                for (int i = 0; i < dum.length; i++) {
                    dum[i] = r.nextInt(100);
                }
                Heap.sort(dum);
                long end = System.nanoTime();
                time = (end - start) / 1e9;
                n = n / 3;
            }
        }
        return n;
    }

    public static void main(String[] args) {
        int size = findAppropriateNumber(2);
        System.out.println(size + " elements");
        LinkedList<Double> bst = new LinkedList<Double>();
        for (int i = 1; i < 21; i++) {
            int[] a = new int[size * i];
            Random r = new Random();
            long start1 = System.nanoTime();
            for (int j = 0; j < a.length; j++) {
                a[j] = r.nextInt(100);
            }
            Heap.sort(a);
            long end1 = System.nanoTime();
            double time1 = (end1 - start1) / 1e9;
            System.out.println("heap " + i + " : " + time1);

            for (int j = 0; j < size * size; j++) {
                bst.add(Math.random());
            }
            long start2 = System.nanoTime();
            BST m = new BST();
            for (double in : bst) {
                m.insert(in);
            }
            long end2 = System.nanoTime();
            double time2 = (end2 - start2) / 1e9;
            System.out.println("bst " + i + " : " + time2);
        }
    }
}
