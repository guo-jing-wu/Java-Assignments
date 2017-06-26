/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bst;

import java.awt.Color;
import java.util.LinkedList;
import simplegui.SimpleGUI;

/**
 *
 * @author tue41582 pre: ValueLeftRight in: LeftValueRight post: LeftRightValue
 * iterative
 */
public class BST {

    BSTNode root = null;
    LinkedList<Double> l = new LinkedList<Double>();
    static SimpleGUI sg;

    public boolean insert(double d) {
        BSTNode current;
        BSTNode n = new BSTNode(d);
        current = root;
        if (root == null) {
            root = n;
        } else {
            boolean insert = false;
            while (!insert) {
                if (n.data < current.data) {
                    if (current.left == null) {
                        current.left = n;
                        insert = true;
                    } else {
                        current = current.left;
                    }
                }
                if (n.data > current.data) {
                    if (current.right == null) {
                        current.right = n;
                        insert = true;
                    } else {
                        current = current.right;
                    }
                }
            }
        }
        return true;
    }

    public LinkedList<Double> traverse(String mode, BSTNode child) {
        if (child == null) {
            return l;
        } else if (mode.equals("preorder")) {
            l.add(child.data);
            if (child.left != null) {
                traverse(mode, child.left);
            }
            if (child.right != null) {
                traverse(mode, child.right);
            }
        } else if (mode.equals("inorder")) {
            if (child.left != null) {
                traverse(mode, child.left);
            }
            l.add(child.data);
            if (child.right != null) {
                traverse(mode, child.right);
            }
        } else if (mode.equals("postorder")) {
            if (child.left != null) {
                traverse(mode, child.left);
            }
            if (child.right != null) {
                traverse(mode, child.right);

            }
            l.add(child.data);
        } else {
            return l;
            //System.exit(0);
        }
        return l;
    }

    public void visualizeList(LinkedList<Double> bst, int size) {
        sg = new SimpleGUI(size, size);
        int index = 0;
        for (double value : bst) {
            int x = index % size;
            int y = index / size;
            Color c = new Color((int) (value * 256), 0, 0);
            sg.drawDot(x, y, 1.0, c, 1.0, "");
            index++;
        }
    }

    public static void main(String[] args) {
        BST bst = new BST();
        int size = 200;
        System.out.println(size + " elements");
        LinkedList<Double> list = new LinkedList<Double>();
        for (int i = 1; i < size * size; i++) {
            list.add(Math.random());
        }
        bst.visualizeList(list, size);
        for (int i = 0; i < (size * size) - 1; i++) {
            bst.insert(list.get(i));
        }
        bst.visualizeList(bst.traverse("inorder", bst.root), size);
        while (true) {
            sg.waitForButton1();
            bst.visualizeList(bst.traverse("preorder", bst.root), size);
            sg.waitForButton2();
            bst.visualizeList(bst.traverse("postorder", bst.root), size);
        }
    }
}
