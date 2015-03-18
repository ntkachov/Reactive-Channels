package com.ntkachov.channel;

import java.util.regex.Pattern;

/**
 * Created by Nick on 3/16/15.
 */
class Utils {
    private static Pattern emptyStringPattern = Pattern.compile("\\s");

    static boolean isTextEmpty(String test){
        return test.isEmpty() || emptyStringPattern.matcher(test).find();
    }
}
