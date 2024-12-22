/**
 * @author K Rege
 * @version 1.00 2018/3/17
 * @version 1.01 2021/8/1
 */

package ch.zhaw.ads;

import org.junit.Ignore;
import org.junit.Test;

import java.io.File;
import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ADS11_2_test {
    SortServer sortServer = new SortServer();
    final int DATAELEMS = 10000;

    String fileToTest = "SortServer.java";

    @Test
    @Ignore
    public void checkFileUpload() throws Exception {
        File f = new File(fileToTest);
        assertTrue("File uploaded " + fileToTest, f.exists());
    }

    @Test
    public void testRandomData() throws Exception {
        sortServer.dataElems = DATAELEMS;
        int[] data = sortServer.randomData();
        assertEquals("Anzahl Daten",sortServer.dataElems,data.length);
        assertTrue("Distributen",data[0] != data[1]);
    }

    @Test
    public void testAscendingData() throws Exception {
        sortServer.dataElems = DATAELEMS;
        int[] data = sortServer.ascendingData();
        assertEquals("Anzahl Daten",sortServer.dataElems,data.length);
        for (int i = 0; i< sortServer.dataElems-1; i++) {
            assertTrue("Distributen",data[i] <= data[i+1]);
        }
    }

    @Test
    public void testDescendingData() throws Exception {
        sortServer.dataElems = DATAELEMS;
        int[] data = sortServer.descendingData();
        assertEquals("Anzahl Daten",sortServer.dataElems,data.length);
        for (int i = 0; i< sortServer.dataElems-1; i++) {
            assertTrue("Distributen",data[i] >= data[i+1]);
        }
    }

    @Test
    public void testBubbleSort() throws Exception {
        sortServer.dataElems = DATAELEMS;
        int[] data = sortServer.randomData();
        sortServer.bubbleSort(data);
        for (int i = 0; i< sortServer.dataElems-1; i++) {
            assertTrue("Sorted",data[i] <= data[i+1]);
        }
    }

    private void testMeasureTime(String msg, Consumer<int[]> sorter) throws Exception {
        sortServer.dataElems = DATAELEMS;
        double time1 = sortServer.measureTime(sortServer::randomData,sorter);
    }


    private double testQuadratic(String msg, Consumer<int[]> sorter) throws Exception {
        sortServer.dataElems = DATAELEMS;
        double time1 = sortServer.measureTime(sortServer::randomData,sorter);

        sortServer.dataElems = DATAELEMS/2;
        double time2 = sortServer.measureTime(sortServer::randomData,sorter);

        assertTrue(msg+" Time O(n^2)",time2 > time1/6 && time2 < time1/2);
        return time1;
    }


    @Test
    public void testMeasureTime() throws Exception {
        testMeasureTime("BUBBLESORT",sortServer::bubbleSort);
        // testQuadratic("BUBBLESORT",sortServer::bubbleSort);
    }


}
