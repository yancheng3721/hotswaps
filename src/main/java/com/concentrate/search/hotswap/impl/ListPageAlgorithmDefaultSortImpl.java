package com.concentrate.search.hotswap.impl;

import java.util.Map;

import com.concentrate.search.hotswap.Algorithm;
import com.concentrate.search.hotswap.param.SearchParam;

/**
 * 列表页默认公式
 * @author 14060329
 *
 */
public class ListPageAlgorithmDefaultSortImpl implements Algorithm {

    @Override
    public float evalFloat(SearchParam param, Map<String, Float> coeffMap) {
        return param.listClickScore*coeffMap.get("k1") + param.listSaleScore*coeffMap.get("k2") + param.listSalesAmountScore*coeffMap.get("k3") + param.listPraiseScore*coeffMap.get("k4");
    }

}
