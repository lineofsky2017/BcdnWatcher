package com.ittianyu.bcdnwatcher;

import org.junit.Test;

import java.util.Locale;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void testFormatDouble() throws Exception {
//        assertEquals(4, 2 + 2);
        String money = String.format(Locale.CHINESE, "%.3f", 12.1659);
        String result = money.substring(0, money.length() - 1);
        System.out.println(result);
    }
}