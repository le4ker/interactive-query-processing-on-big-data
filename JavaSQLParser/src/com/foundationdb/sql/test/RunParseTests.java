package com.foundationdb.sql.test;

import java.lang.reflect.Method;

/**
 * Created by panossakkos on 2/15/14.
 */

public class RunParseTests {

    public static void main(String[] args) throws Exception {
        for (Method test : ParseTests.class.getMethods()) {
            System.out.println(test.getName() + ": " + test.invoke(null, null));
        }
    }
}
