package ch.zhaw.ads;

import java.util.LinkedList;
import java.util.List;

public class TreeNode<T extends Comparable<T>> {
    List<T> values;  // implemented as list because of doublette handling in AVL
    TreeNode<T> left, right;
    int height; // for AVL

    TreeNode(T value){
        this.values = new LinkedList<>();
        this.values.add(value);
        this.height = 1;
    }

    T getValue(){return values.get(0);}
}