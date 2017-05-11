package com.concentrate.search.hotswap.impl;

import java.util.Map;

import com.concentrate.search.hotswap.Algorithm;
import com.concentrate.search.hotswap.param.SearchParam;

public class AlgorithmClickImpl implements Algorithm{

    @Override
    public float evalFloat(SearchParam param, Map<String, Float> coeffMap) {
        return param.clickScore;
    }

}
