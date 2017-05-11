package com.concentrate.search.hotswap.bean;

import java.util.Map;

import com.concentrate.search.hotswap.Algorithm;

public class AlgorithmCoeffBean {

	private String algorithmName;
	
	private Algorithm algorithm;
	
	private Map<String, Float> coeffientMap;
	
	public AlgorithmCoeffBean(String algorithmName, Algorithm algorithm, Map<String, Float> coeffientMap) {
		this.algorithmName = algorithmName;
		this.algorithm = algorithm;
		this.coeffientMap = coeffientMap;
	}

	public String getAlgorithmName() {
		return algorithmName;
	}

	public void setAlgorithmName(String algorithmName) {
		this.algorithmName = algorithmName;
	}

	public Algorithm getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	public Map<String, Float> getCoeffientMap() {
		return coeffientMap;
	}

	public void setCoeffientMap(Map<String, Float> coeffientMap) {
		this.coeffientMap = coeffientMap;
	}
	
}
