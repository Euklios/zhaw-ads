/**
 * @(#)TreeTest.java
 *
 *
 * @author K Rege
 * @version 1.00 2018/3/17
 * @version 1.01 2021/8/1
 */

package ch.zhaw.ads;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class ADS5_5_test {
    Tree<String> tree;

    String fileToTest = "TreeTraversal.java";

    @Test
    @Ignore
    public void checkFileUpload() throws Exception {
        File f = new File(fileToTest);
        assertTrue("File uploaded "+fileToTest,f.exists());
    }


    @Before
    public void setUp() throws Exception {
        tree = new SortedBinaryTree<String>();
        tree.add("B");
        tree.add("A");
        tree.add("C");
        tree.add("D");
    }



    @Test
    public void testInterval() {
        char left = 'K';
        char right = 'O';

        for (int i = 0; i < 200; i++) {
            Character c = (char) ('A' + (Math.random() * 26));
            tree.add(c.toString());
        }
        // get all elements with inorder
        Visitor<String> v = new MyVisitor<String>();
        tree.traversal().inorder(v);
        int count = 0;
        String s = v.toString();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= left && s.charAt(i) <= right) count++;
        }
        // now interval
        v = new MyVisitor<String>();
        tree.traversal().interval(((Character)left).toString(),((Character)right).toString(),v);
        s = v.toString();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            assertTrue(c + " in interval "+left+" "+right,c >= left && c <= right);
        }
        assertEquals("size",count,s.length());
    }
}
