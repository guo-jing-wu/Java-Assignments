/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package quadtree;

import qtvisualizer.*;

/**
 *
 * @author tue41582
 */
public class QuadTree {

    QNode root;

    public QuadTree() {
        root = null;
    }

    public void insertAtRandom(double v) {
        QNode n = new QNode(v);
        if (root == null) {
            root = n;
            return;
        }
        boolean insert = false;
        QNode current = root;
        while (!insert) {
            int index = (int) (Math.random() * 4);
            if (current.n[index] == null) {
                current.n[index] = n;
                insert = true;
            } else {
                current = current.n[index];
            }
        }
    }

    public double sumR(QNode localroot) {
        if (localroot == null) {
            return (0.0);
        } else {
            double sum = localroot.v;
            for (int i = 0; i < 4; i++) {
                sum += sumR(localroot.n[i]);
            }
            return (sum);
        }
    }

    public static void main(String[] args) {
        QuadTree qt = new QuadTree();
        for (int i = 1; i <= 100; i++) {
            qt.insertAtRandom(i);
        }
        double sum = qt.sumR(qt.root);
        System.out.println(sum);
        QuadTreeViz viz = new QuadTreeViz(qt.root);
    }
}
