package com.github.fisherhe12.gof.metric;

import java.util.Map;

/**
 * com.github.fisherhe12.gof.metric
 *
 * @author Fisher.He
 */
public interface StatViewer {

    void output(Map<String, RequestStat> requestStats,
                long startTimeInMillis,
                long endTimeInMillis);

}
