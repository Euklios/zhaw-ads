/**
 * @author K Rege
 * @version 1.00 2018/3/17
 * @version 1.01 2021/8/1
 */

package ch.zhaw.ads;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.*;
import java.util.function.*;

public class ADS12_1_test {
    QuickerSortServer sortServer = new QuickerSortServer();
    final int DATAELEMS = 1000000;

    String fileToTest = "QuickerSortServer.java";

    @Test
    @Ignore
    public void checkFileUpload() throws Exception {
        File f = new File(fileToTest);
        assertTrue("File uploaded " + fileToTest, f.exists());
    }



    @Test
    public void testQuickerSort() throws Exception {
        sortServer.dataElems = DATAELEMS;
        sortServer.insertion_threshold = 50;
        int[] data = sortServer.randomData();
        sortServer.quickerSort(data);
        for (int i = 0; i< sortServer.dataElems-1; i++) {
            assertTrue("Sorted",data[i] <= data[i+1]);
        }
    }

    private void testMeasureTime(String msg, Consumer<int[]> sorter) throws Exception {
        sortServer.dataElems = DATAELEMS;
        sortServer.insertion_threshold = 50;
        double time1 = sortServer.measureTime(sortServer::randomData,sorter);
    }


    @Test
    public void testMeasureTime() throws Exception {
        testMeasureTime("QUICKER",sortServer::quickerSort);
    }


}
