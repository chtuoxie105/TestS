package com.example.testwifi;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        int[] arrays = {1, 2, 5, 4, 9, 6, 7, 8};
        int[] l = bubbleSort(arrays);
        for (int i = 0; i < l.length; i++) {
            System.out.println("ll.." + l[i]);
        }
    }

    public int[] bubbleSort(int[] arrays) {
        if (arrays.length == 0)
            return arrays;
        if (arrays.length == 1)
            return arrays;
        int position = 1;
        int length = arrays.length - 1;
        boolean isfirst = true; //新增代码
        for (int i = 0; i < length; i++) {
            boolean flag = true;
            for (int j = 1; j <= length - i; j++) {
                if (arrays[j - 1] > arrays[j]) {
                    swap(arrays, j - 1, j);
                    flag = false;
                    position = position > j ? position : j;
                }
            }
            /*新增代码*/
            if (isfirst) {
                isfirst = false;
                length = position;
            }
            if (flag) return arrays;
        }
        return arrays;
    }

    private void swap(int[] arrays, int arg1, int arg2) {
        int temp = arrays[arg1];
        arrays[arg1] = arrays[arg2];
        arrays[arg2] = temp;
    }
}