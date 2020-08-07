package com.github.fisherhe12.gof.metric;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * com.github.fisherhe12.gof.metric
 *
 * @author fisher
 * @date 2020-03-29
 */
public class ConsoleReporter extends ScheduleReporter {

    private static final Long DAY_HOURS_IN_SECONDS = 86400L;

    public ConsoleReporter(MetricsStorage metricsStorage,
                           Aggregator aggregator,
                           StatViewer viewer) {
        super(metricsStorage, aggregator, viewer);
    }


    public void startDailyReport() {
        Date firstTime = trimTimeFieldsToZeroOfNextDay(new Date());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long durationMillis = DAY_HOURS_IN_SECONDS * 1000;
                long endTimeMillis = System.currentTimeMillis();
                long startTimeMillis = endTimeMillis - durationMillis;
                doStatAndReport(startTimeMillis, endTimeMillis);
            }
        }, firstTime, DAY_HOURS_IN_SECONDS);
    }

    private Date trimTimeFieldsToZeroOfNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static void main(String[] args) {
    }

}

