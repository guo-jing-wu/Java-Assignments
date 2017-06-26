/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package heapsort;

/**
 *
 * @author tue41582
 * {4,1,6,7,5,8,3,9,0,2,10}
 * root:             4
 * two :      1      -      6
 * four:   7  ,  5   -  8   ,  3
 * eight: 9,0 - 2,10 - none - none
 * if child is smaller than parent, swap it
 * root:             0
 * two:       1      -      3
 * four:   4  ,  2   -  8   ,  6
 * eight: 9,7 - 5,10 - none - none
 * {0,1,3,4,2,8,6,9,7,5,10}
 * print and remove root (0) and replace it with last (10)
 * {10,1,3,4,2,8,6,9,7,5}
 * swap again
 * root:           10
 * two:       1    -      3
 * four:   4  , 2  -  8   ,  6
 * eight: 9,7 - 5, - none - none
 * root:            1
 * two:        4    -      3
 * four:    9  , 2  -  8   ,  6
 * eight: 10,7 - 5, - none - none
 * {1,4,3,9,2,8,6,10,7,5}
 * repeat until completely sorted.
 */
public class Heap {

    int[] array;
    int size;

    public Heap(int size) {
        this.size = 0;
        array = new int[size];
    }

    public void insert(int data) {
        if (this.size == this.array.length) {
            int[] arr = new int[this.size * 2];
            for (int i = 0; i < this.array.length; i++) {
                arr[i] = this.array[i];
            }
            this.array = arr;
        }
        this.array[this.size] = data;
        this.up(size++);
    }

    private void up(int index) {
        int temp = this.array[index];
        int parent = (index - 1) / 2;
        while (index > 0 && array[parent] < temp) {
            array[index] = array[parent];
            index = parent;
            parent = (index - 1) / 2;
        }
        array[index] = temp;
    }

    public int remove() {
        if (this.size == 0) {
            return array[-1];
        }
        int result = array[0];
        this.array[0] = this.array[--size];
        this.down(0);
        return result;
    }

    private void down(int index) {
        if (size == 0) {
            return;
        }
        int temp = array[index];
        int child;
        while (index < size / 2) {
            int left = index * 2 + 1;
            int right = index * 2 + 2;
            if (right < size && array[right] > array[left]) {
                child = right;
            } else {
                child = left;
            }
            if (temp > array[child]) {
                break;
            }
            array[index] = array[child];
            index = child;
        }
        array[index] = temp;
    }

    public static void sort(int[] sort) {
        Heap heap = new Heap(sort.length);
        for (int i = 0; i < sort.length; i++) {
            heap.insert(sort[i]);
        }
        for (int i = sort.length - 1; i >= 0; i--) {
            sort[i] = heap.remove();
        }
    }

    public static void printArray(int[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }
}
