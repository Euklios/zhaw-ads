package ch.zhaw.ads;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ADS1_4_test {
    WellformedXmlServer xml;

    @Before
    public void setUp() throws Exception {
        xml = new WellformedXmlServer();
    }

    private void test(String s, boolean b) {
        boolean ok = xml.checkWellformed(s);
        assertEquals(s, b, ok);
    }

    @Test
    public void testXmlAttributes() {
        test("<a href=\"sugus\"></a>", true);
        test("<a href=\"sugus\">", false);
    }

    @Test
    public void testXml() {
        test("<a></a>", true);
        test("<a>", false);
        test("</a>", false);
        test("<a/>", true);
        test("<a><b></b></a>", true);
        test("<a><b></a></b>", false);
    }
}
