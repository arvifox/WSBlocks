package com.arvifox.SingleBlock;

/**
 * Created by Andrey on 02.12.2016.
 */
public class SingleBlockFactory {
    private static SingleBlockFactory ourInstance = new SingleBlockFactory();

    public static SingleBlockFactory getInstance() {
        return ourInstance;
    }

    public ISingleBlock createCalc() {
        return new SingleBlockCalc();
    }

    private SingleBlockFactory() {
    }
}
