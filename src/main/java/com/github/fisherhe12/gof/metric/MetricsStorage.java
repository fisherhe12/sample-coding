package com.github.fisherhe12.gof.metric;

import java.util.List;
import java.util.Map;

/**
 * com.github.fisherhe12.gof.metric
 *
 * @author fisher
 * @date 2020-03-29
 */
public interface MetricsStorage {

	void saveRequestInfo(RequestInfo requestInfo);

	List<RequestInfo> getRequestInfos(String apiName, long startTimeInMillis, long endTimeInMills);

	Map<String, List<RequestInfo>> getRequestInfos(long startTimeInMillis, long endTimeInMills);

}
