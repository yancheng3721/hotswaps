package com.concentrate.search.hotswap.bean;

import java.util.Map;

import com.concentrate.search.hotswap.SortFunction;

public class SortFunctionCoeffBean {

	private String channelName;
	
	private String scopeType;
	
	private String funcName;
	
	private SortFunction func;
	
	private Map<String, Float> coeffientMap;
	
	public SortFunctionCoeffBean(String channelName, String scopeType, String funcName, SortFunction func, Map<String, Float> coeffientMap) {
		this.channelName = channelName;
		this.scopeType = scopeType;
		this.funcName = funcName;
		this.func = func;
		this.coeffientMap = coeffientMap;
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

	public String getFuncName() {
		return funcName;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public SortFunction getFunc() {
		return func;
	}

	public void setFunc(SortFunction func) {
		this.func = func;
	}

	public Map<String, Float> getCoeffientMap() {
		return coeffientMap;
	}

	public void setCoeffientMap(Map<String, Float> coeffientMap) {
		this.coeffientMap = coeffientMap;
	}

	@Override
	public String toString() {
		return "SortFunctionCoeffBean [channelName=" + channelName
				+ ", scopeType=" + scopeType + ", funcName=" + funcName
				+ ", func=" + func + ", coeffientMap=" + coeffientMap + "]";
	}

}
