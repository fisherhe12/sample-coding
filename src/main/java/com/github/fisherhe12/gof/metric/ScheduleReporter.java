package com.github.fisherhe12.gof.metric;

import java.util.List;
import java.util.Map;

/**
 * com.github.fisherhe12.gof.metric
 *
 * @author Fisher.He
 */
public class ScheduleReporter {

    private static final Long DAY_HOURS_IN_SECONDS = 86400L;
    private final MetricsStorage metricsStorage;
    private final Aggregator aggregator;
    private final StatViewer viewer;

    public ScheduleReporter(MetricsStorage metricsStorage,
                            Aggregator aggregator,
                            StatViewer viewer) {
        this.metricsStorage = metricsStorage;
        this.aggregator = aggregator;
        this.viewer = viewer;
    }

    protected void doStatAndReport(long startTimeInMillis, long endTimeInMillis) {
        long durationInMillis = endTimeInMillis - startTimeInMillis;
        Map<String, List<RequestInfo>> requestInfos =
            metricsStorage.getRequestInfos(startTimeInMillis, endTimeInMillis);

        Map<String, RequestStat> requestStats =
            aggregator.aggregate(requestInfos, durationInMillis);

        viewer.output(requestStats, startTimeInMillis, endTimeInMillis);
    }

}
