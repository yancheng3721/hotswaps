package com.concentrate.search.hotswap;

import java.util.Map;

import com.concentrate.search.hotswap.param.SearchParam;

public interface Algorithm {

    float evalFloat(SearchParam param, Map<String, Float> coeffMap);
}
