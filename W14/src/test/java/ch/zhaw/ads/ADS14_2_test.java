/**
 * @author K Rege
 * @version 1.00 2018/3/17
 * @version 1.01 2021/8/1
 */

package ch.zhaw.ads;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.*;
import java.io.*;

public class ADS14_2_test {
    String fileToTest = "ParaglidingState.java";
    static final double COOLING = 0.9995;
    static final double DISTURBANCE = 0.5;

    @Test
    @Ignore
    public void checkFileUpload() throws Exception {
        File f = new File(fileToTest);
        assertTrue("File uploaded " + fileToTest, f.exists());
    }

    @Test
    public void testParagligingCost() throws Exception {
        AnnealingState state = new ParaglidingState(DISTURBANCE);
        int cost = (int)state.getCost();
        assertTrue("cost was:"+ cost+" should be ~1414",(int)state.getCost()==1414);
    }



}
