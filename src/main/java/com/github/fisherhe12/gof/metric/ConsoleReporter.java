package com.github.fisherhe12.gof.metric;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * com.github.fisherhe12.gof.metric
 *
 * @author fisher
 * @date 2020-03-29
 */
public class ConsoleReporter {
	private MetricsStorage metricsStorage;
	private ScheduledExecutorService executor;
	private Aggregator aggregator;

	public ConsoleReporter(MetricsStorage metricsStorage, Aggregator aggregator) {
		this.metricsStorage = metricsStorage;
		this.executor = Executors.newSingleThreadScheduledExecutor();
		this.aggregator = aggregator;
	}


	public void startRepeatedReport(long periodInSeconds, long durationInSeconds) {
		executor.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {

				long durationInMillis = durationInSeconds * 1000;
				long endTimeInMillis = System.currentTimeMillis();
				long startTimeInMillis = endTimeInMillis - durationInMillis;

				Map<String, List<RequestInfo>> requestInfos = metricsStorage
						.getRequestInfos(startTimeInMillis, endTimeInMillis);

				Map<String, RequestStat> requestStats = aggregator.aggregate(requestInfos, durationInMillis);

				System.out.println("Time Span: [" + startTimeInMillis + ", " + endTimeInMillis);
			}
		}, 0, periodInSeconds, TimeUnit.SECONDS);
	}
}

