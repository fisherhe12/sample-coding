package com.github.fisherhe12.gof.metric;

import lombok.Getter;
import lombok.Setter;

/**
 * com.github.fisherhe12.gof.metric
 *
 * @author fisher
 * @date 2020-03-29
 */
@Getter
@Setter
public class RequestStat {
	private double maxResponseTime;
	private double minResponseTime;
	private double avgResponseTime;
	private double p999ResponseTime;
	private double p99ResponseTime;
	private long count;
	private long tps;
}
