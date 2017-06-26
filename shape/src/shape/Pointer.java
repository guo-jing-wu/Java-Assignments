/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package shape;

/**
 *
 * @author tue41582
 */
public class Pointer {

    int x, y;
    Pointer next, prev;
    double weight;

    public Pointer(int x, int y, double weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;
    }
    
    public double getWeight() {
        return weight;
    }
}