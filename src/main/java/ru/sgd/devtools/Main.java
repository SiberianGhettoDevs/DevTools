package ru.sgd.devtools;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.Temporal;

public class Main {

    public static void main(String[] args) {
        Temporal firstArg = LocalDateTime.of(2021, Month.NOVEMBER, 1, 0, 0);
        Temporal secondArg = LocalDateTime.of(2022, Month.MAY, 1, 0, 0);

        System.out.println("Full days between dates: " + DateUtils.fullDaysBetween(firstArg, secondArg));
        System.out.println("Full weeks between dates: " + DateUtils.fullWeeksBetween(firstArg, secondArg));
    }
}
