package com.concentrate.search.hotswap.impl;

import java.util.Map;

import com.concentrate.search.hotswap.Algorithm;
import com.concentrate.search.hotswap.param.SearchParam;

public class AlgorithmDefaultSortImpl implements Algorithm {

    @Override
    public float evalFloat(SearchParam param, Map<String, Float> coeffMap) {
        return param.semanticScore*coeffMap.get("k4") + coeffMap.get("k5")*(param.clickScore*coeffMap.get("k1") + param.saleScore*coeffMap.get("k2") + param.newScore*coeffMap.get("k3"));
    }

}
