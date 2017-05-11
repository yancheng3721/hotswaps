package com.concentrate.search.hotswap;

import java.util.Map;

import com.concentrate.search.hotswap.param.SortParam;

public interface SortFunction {

    float evalFloat(SortParam param, Map<String, Float> coeffMap);
    
}
