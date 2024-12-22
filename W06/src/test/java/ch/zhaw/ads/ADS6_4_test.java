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

public class ADS6_4_test {
    MyTree<String> tree;

    String fileToTest = "AVLSearchTree.java";

    @Test
    @Ignore
    public void checkFileUpload() throws Exception {
        File f = new File(fileToTest);
        assertTrue("File uploaded "+fileToTest,f.exists());
    }

    class MyTree<T extends Comparable<T>> extends AVLSearchTree<T> {
        TreeNode getRoot () {
            return root;
        }

    }

    @Before
    public void setUp() throws Exception {
        tree = new MyTree<String>();
        tree.add("B");
        tree.add("A");
        tree.add("C");
        tree.add("D");
    }

    @Test
    public void testBalanced() {
        assertTrue("should be balanced",tree.balanced());
        TreeNode n = tree.getRoot();
        n.right.right.right = new TreeNode("Z");
        assertFalse("should not be balanced",tree.balanced());
    }
}
