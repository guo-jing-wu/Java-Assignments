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
public class QNode implements QuadTreeNode {

    double v;
    QNode[] n;

    public QNode(double v) {
        this.v = v;
        n = new QNode[4];
    }

    @Override
    public QuadTreeNode[] getChildren() {
        return n;
    }
}
