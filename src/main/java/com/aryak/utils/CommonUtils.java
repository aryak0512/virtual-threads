package com.aryak.utils;

import java.util.function.Consumer;

public class CommonUtils {
    
    public static Consumer<String> log = message -> System.out.println("[" + Thread.currentThread() + "] : " + message);
    
}
