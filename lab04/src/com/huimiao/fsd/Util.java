package com.huimiao.fsd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.function.Predicate;

/**
 * @ClassName: Util
 * @Description: TODO
 * @author: huimiao
 * @date: 7/25/2019 9:53 PM
 * @version: 1.0
 */
public class Util {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static boolean isPositiveNum(String str) {
        return str.matches("[1-9][0-9]*(\\.*[0-9]{1,2})?");
    }

    public static boolean isValidFrequence(String str) {
        return str.matches("([1-9][0-9]*)");
    }

    public static String readValidData(String indicator, Predicate<String> p) throws Exception {
        String input = "";

        while (!p.test(input)) {
            System.out.print(indicator);
            input = br.readLine();
        }

        return input;
    }
}
