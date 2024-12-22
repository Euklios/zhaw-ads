/**
 * @(#)TreeTest.java
 *
 *
 * @author K Rege
 * @version 1.00 2018/3/17
 * @version 1.01 2021/8/1
 */

package ch.zhaw.ads;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class ADS6_5_test {
    Tree<String> tree;

    String fileToTest = "AVLSearchTree.java";

    @Test
    @Ignore
    public void checkFileUpload() throws Exception {
        File f = new File(fileToTest);
        assertTrue("File uploaded "+fileToTest,f.exists());
    }

    @Test
    public void testMixed() {
        tree = new AVLSearchTree<String>();
        List<String> list = new LinkedList<>();
        for (int i = 0; i < 1000; i++) {
            Character c = (char) ('A' + (Math.random() * 26));
            boolean twice = false;
            int op = (int) (Math.random() * 2);
            System.out.println(op + " " + c);

            switch (op) {
                case 0:
                    list.add(c.toString());
                    tree.add(c.toString());
                    break;
                case 1:
                    list.remove(c.toString());
                    tree.remove(c.toString());
                    break;
            }

            if (list.size() != tree.size()) {
                switch (op) {
                    case 0:
                        tree.add(c.toString());
                        break;
                    case 1:
                        tree.remove(c.toString());
                        break;
                }
            }
        }
        assertEquals(list.size(),tree.size());
        Collections.sort(list);
        StringBuilder b = new StringBuilder();
        for (String s : list) {b.append(s);};
        Visitor<String> v = new MyVisitor<String>();
        tree.traversal().inorder(v);
        assertEquals("mixed",b.toString(), v.toString());

        assertTrue("balanced",tree.balanced());
    }

    @Test
    public void testSimple() {
        tree = new AVLSearchTree<>();
        tree.add("J");
        tree.add("G");
        tree.remove("G");
        tree.add("T");
        tree.add("V");

        assertEquals(3, tree.size());
    }

}
