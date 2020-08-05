package com.github.fisherhe12.gof.metric;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * com.github.fisherhe12.gof.metric
 *
 * @author fisher
 * @date 2020-03-29
 */
public class Aggregator {

	public Map<String, RequestStat> aggregate(Map<String, List<RequestInfo>> requestInfos, long durationInMillis) {
		Map<String, RequestStat> requestStats = new HashMap<>();
		for (Map.Entry<String, List<RequestInfo>> entry : requestInfos.entrySet()) {
			String apiName = entry.getKey();
			List<RequestInfo> requestInfosPerApi = entry.getValue();
			RequestStat requestStat = doAggregate(requestInfosPerApi, durationInMillis);
			requestStats.put(apiName, requestStat);
		}
		return requestStats;
	}


	private RequestStat doAggregate(List<RequestInfo> requestInfos, long durationInMillis) {
		List<Double> respTimes = requestInfos.stream().map(RequestInfo::getResponseTime).collect(Collectors.toList());
		RequestStat requestStat = new RequestStat();
		requestStat.setMaxResponseTime(max(respTimes));
		requestStat.setMinResponseTime(min(respTimes));
		requestStat.setAvgResponseTime(avg(respTimes));
		requestStat.setP999ResponseTime(percentile999(respTimes));
		requestStat.setP99ResponseTime(percentile99(respTimes));
		requestStat.setCount(respTimes.size());
		requestStat.setTps(respTimes.size() / durationInMillis * 1000);
		return requestStat;
	}

	private double min(List<Double> respTimes) {
		return respTimes.stream().mapToDouble(v -> v).min().orElse(0);
	}

	private double max(List<Double> respTimes) {
		return respTimes.stream().mapToDouble(v -> v).max().orElse(0);
	}

	private double avg(List<Double> respTimes) {
		return respTimes.stream().mapToDouble(v -> v).average().orElse(0);
	}

	private double percentile999(List<Double> respTimes) {
		return percentile(respTimes, 0.999);
	}

	private double percentile99(List<Double> respTimes) {
		return percentile(respTimes, 0.99);
	}

	private double percentile(List<Double> respTimes, double ratio) {
		respTimes = respTimes.stream().sorted().collect(Collectors.toList());
		int idx = (int) (respTimes.size() * ratio);
		return respTimes.get(idx);
	}
}
