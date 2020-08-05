package com.github.fisherhe12.gof.metric;

import org.apache.commons.lang3.StringUtils;

/**
 * com.github.fisherhe12.gof.metric
 *
 * @author fisher
 * @date 2020-03-29
 */
public class MetricsCollector {

	private MetricsStorage metricsStorage;

	public MetricsCollector(MetricsStorage metricsStorage) {
		this.metricsStorage = metricsStorage;
	}

	public void recordRequest(RequestInfo requestInfo) {
		if (requestInfo == null || StringUtils.isBlank(requestInfo.getApiName())) {
			return;
		}
		metricsStorage.saveRequestInfo(requestInfo);
	}
}
