package ru.sgd.devtools;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.Temporal;

public class Main {

    public static void main(String[] args) {
        Temporal firstArg = LocalDateTime.now();
        Temporal secondArg = LocalDateTime.of(2021, Month.JULY, 14, 0, 0);

        long result = DateUtils.fullDaysBetween(firstArg, secondArg);
        System.out.println(result);
    }
}
