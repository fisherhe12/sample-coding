package com.github.fisherhe12.gof.metric;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestInfo {
	private String apiName; 
	private double responseTime;
	private long timestamp;
}
