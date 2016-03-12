package ru.javawebinar.topjava.util;

import java.util.concurrent.atomic.AtomicLong;

public abstract class IdGenerator {
    private static final AtomicLong userMealId = new AtomicLong(1);

    public static long getUserMealId(){
        return userMealId.getAndIncrement();}
}
