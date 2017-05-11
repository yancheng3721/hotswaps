package com.concentrate.search.hotswap.impl.channel;

import java.util.Map;

import com.concentrate.search.hotswap.SortFunction;
import com.concentrate.search.hotswap.param.SortParam;

public class YpdsSearchDefaultSortImpl implements SortFunction{
	@Override
	public float evalFloat(SortParam param, Map<String, Float> coeffMap) {
		return param.f1*coeffMap.get("A") + param.f2*coeffMap.get("B") + param.f3*coeffMap.get("C") + param.f11*param.f12 + param.f13*param.f14 + param.f15*param.f16 + param.f17*param.f18+param.f19*param.f20 +param.f21*param.f22;
	}
}
