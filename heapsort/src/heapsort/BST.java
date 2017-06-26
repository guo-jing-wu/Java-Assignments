/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heapsort;

import java.util.LinkedList;

/**
 *
 * @author tue41582 pre: ValueLeftRight in: LeftValueRight post: LeftRightValue
 * iterative
 */
public class BST {

    BSTNode root;
    LinkedList<Double> l = new LinkedList<Double>();

    public BST() {
        root = null;
    }

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
        } else {
            if (mode.equals("preorder")) {
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
                System.exit(0);
            }
        }
        return l;
    }
}
