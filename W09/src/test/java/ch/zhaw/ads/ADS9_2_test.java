/**
 * @author K Rege
 * @version 1.00 2018/3/17
 * @version 1.01 2021/8/1
 */

package ch.zhaw.ads;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class ADS9_2_test {
    MyCompetitor c1 = new MyCompetitor(1,"Kiptum Daniel","02:11:31");
    MyCompetitor c2 = new MyCompetitor(1,"Kiptum Daniel","02:11:31");
    MyCompetitor c3 = new MyCompetitor(2,"Ancay Tarcis","02:20:02");

    String fileToTest = "MyCompetitor.java";

    @Test
    @Ignore
    public void checkFileUpload() throws Exception {
        File f = new File(fileToTest);
        assertTrue("File uploaded " + fileToTest, f.exists());
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(c1+" == "+c2,c1.equals(c2));
        assertFalse(c1+" != "+c3,c1.equals(c3));
    }

    @Test
    public void testCompareTo() throws Exception {
        assertTrue(c1+" == "+c2,c1.compareTo(c2) == 0);
        assertFalse(c1+" != "+c3,c1.compareTo(c3) == 0);
    }


    @Test
    public void testHashcode() throws Exception {
        assertTrue(c1+" == "+c2,c1.hashCode() == c2.hashCode());
        assertFalse(c1+" != "+c3,c1.hashCode() == c3.hashCode());
    }

}
