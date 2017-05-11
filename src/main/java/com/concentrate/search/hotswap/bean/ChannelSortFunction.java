package com.concentrate.search.hotswap.bean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChannelSortFunction {
	
	/**
	 * 频道
	 */
	private String channelName;
	
	/**
	 * 页面类型，搜索结果页，列表页，频道默认页
	 */
	private String scopeType;
	
	private Map<String,SortFunctionCoeffBean> algorithms = new ConcurrentHashMap<String,SortFunctionCoeffBean>();
    
	private Map<String, SortFunctionCoeffBean> keywordAlgorithms = new ConcurrentHashMap<String, SortFunctionCoeffBean>();

	private SortFunctionCoeffBean globalAlgorithm;

	public ChannelSortFunction(String channelName, String scopeType) {
		super();
		this.channelName = channelName;
		this.scopeType = scopeType;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getScopeType() {
		return scopeType;
	}

	public void setScopeType(String scopeType) {
		this.scopeType = scopeType;
	}

	public Map<String, SortFunctionCoeffBean> getAlgorithms() {
		return algorithms;
	}

	public void setAlgorithms(Map<String, SortFunctionCoeffBean> algorithms) {
		this.algorithms = algorithms;
	}

	public Map<String, SortFunctionCoeffBean> getKeywordAlgorithms() {
		return keywordAlgorithms;
	}

	public void setKeywordAlgorithms(
			Map<String, SortFunctionCoeffBean> keywordAlgorithms) {
		this.keywordAlgorithms = keywordAlgorithms;
	}

	public SortFunctionCoeffBean getGlobalAlgorithm() {
		return globalAlgorithm;
	}

	public void setGlobalAlgorithm(SortFunctionCoeffBean globalAlgorithm) {
		this.globalAlgorithm = globalAlgorithm;
	}

	@Override
	public String toString() {
		return "ChannelSortFunction [channelName=" + channelName
				+ ", scopeType=" + scopeType + ", algorithms=" + algorithms
				+ ", keywordAlgorithms=" + keywordAlgorithms
				+ ", globalAlgorithm=" + globalAlgorithm + "]";
	}
	
	
}
