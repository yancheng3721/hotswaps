package com.concentrate.search.hotswap.param;

public class SearchParam {
    public float clickScore = 0.0f;
    public float saleScore = 0.0f;
    public float newScore = 0.0f;
    public float hotSaleScore = 0.0f;
    public float popularityScore = 0.0f;
    public float semanticScore = 0.0f;
    public float storeScore = 0.0f; //库存因子
    
    public float listClickScore = 0.0f;//列表页点击数
    public float listSaleScore = 0.0f;//列表页销量
    public float listSalesAmountScore = 0.0f;//列表页销售额
    public float listPraiseScore = 0.0f;//列表页好评率
    public float listCommentScore = 0.0f;//列表页评价得分
    public float listBrandScore = 0.0f;//列表页品牌得分
    
	@Override
	public String toString() {
		return "SearchParam [clickScore=" + clickScore + ", saleScore="
				+ saleScore + ", newScore=" + newScore + ", hotSaleScore="
				+ hotSaleScore + ", popularityScore=" + popularityScore
				+ ", semanticScore=" + semanticScore + ", storeScore="
				+ storeScore + ", listClickScore=" + listClickScore
				+ ", listSaleScore=" + listSaleScore
				+ ", listSalesAmountScore=" + listSalesAmountScore
				+ ", listPraiseScore=" + listPraiseScore
				+ ", listCommentScore=" + listCommentScore
				+ ", listBrandScore=" + listBrandScore + "]";
	}
}
