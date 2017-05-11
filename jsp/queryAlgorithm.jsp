<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.concentrate.search.hotswap.config.Constant"%>
<%@page import="com.concentrate.search.hotswap.param.SearchParam"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<%@page import="com.concentrate.search.hotswap.*,com.concentrate.search.hotswap.bean.*,java.util.*" %>
<%
	String scopeTypeStr = request.getParameter("scopeType");
	int scopeType = 0;
	if (scopeTypeStr != null) {
		scopeType = Integer.parseInt(scopeTypeStr.trim());
	}

	String keyword = request.getParameter("keyword");
	String newScore = request.getParameter("newScore");
	String hotSaleScore = request.getParameter("hotSaleScore");
	String semanticScore = request.getParameter("semanticScore");
	String popularityScore = request.getParameter("popularityScore");
	String saleScore = request.getParameter("saleScore");
	String clickScore = request.getParameter("clickScore");
	String storeScore = request.getParameter("storeScore");
	
	String listClickScore = request.getParameter("listClickScore");
	String listSaleScore = request.getParameter("listSaleScore");
	String listSalesAmountScore = request.getParameter("listSalesAmountScore");
	String listPraiseScore = request.getParameter("listPraiseScore");
	String listCommentScore = request.getParameter("listCommentScore");
	String listBrandScore = request.getParameter("listBrandScore");
	
	SearchParam param = new SearchParam();
	param.newScore = (newScore != null && !newScore.equals("")) ? Float.valueOf(newScore) : 0f;
	param.hotSaleScore = (hotSaleScore != null && !hotSaleScore.equals("")) ? Float.valueOf(hotSaleScore) : 0f;
	param.semanticScore = (semanticScore != null && !semanticScore.equals("")) ? Float.valueOf(semanticScore) : 0f;
	param.popularityScore = (popularityScore != null && !popularityScore.equals("")) ? Float.valueOf(popularityScore) : 0f;
	param.saleScore = (saleScore != null && !saleScore.equals("")) ? Float.valueOf(saleScore) : 0f;
	param.clickScore = (clickScore != null && !clickScore.equals("")) ? Float.valueOf(clickScore) : 0f;
	param.storeScore = (storeScore != null && !storeScore.equals("")) ? Float.valueOf(storeScore) : 0f;
	
	param.listClickScore = (listClickScore != null && !listClickScore.equals("")) ? Float.valueOf(listClickScore) : 0f;
	param.listSaleScore = (listSaleScore != null && !listSaleScore.equals("")) ? Float.valueOf(listSaleScore) : 0f;
	param.listSalesAmountScore = (listSalesAmountScore != null && !listSalesAmountScore.equals("")) ? Float.valueOf(listSalesAmountScore) : 0f;
	param.listPraiseScore = (listPraiseScore != null && !listPraiseScore.equals("")) ? Float.valueOf(listPraiseScore) : 0f;
	param.listCommentScore = (listCommentScore != null && !listCommentScore.equals("")) ? Float.valueOf(listCommentScore) : 0f;
	param.listBrandScore = (listBrandScore != null && !listBrandScore.equals("")) ? Float.valueOf(listBrandScore) : 0f;
	
	keyword = keyword != null ? keyword.trim() : null;
	
%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<title>查询公式</title>
	<style>
		.context {
			margin: 0 auto;
			width: 1000px;
		}
		.module {
			
		}
		
		.module>p {
			
		}
		
		#queryKeywordInfo{
			
		}
		
		table {
			font-family: verdana,arial,sans-serif;
			font-size:11px;
			color:#333333;
			border-width: 1px;
			border-color: #666666;
			border-collapse: collapse;
		}
		table th {
			border-width: 1px;
			padding: 8px;
			border-style: solid;
			border-color: #666666;
			background-color: #dedede;
		}
		table td {
			border-width: 1px;
			padding: 8px;
			border-style: solid;
			border-color: #666666;
			background-color: #ffffff;
		}
		
		.param {
			width: 60px;
		}
		
		form {
			font-size: 12px;
		}
		
		.dashline {
			margin: 10px 0;
			height: 1px;
  			border-bottom: 1px dashed #000;
		}
		
		.searchItem span{
			margin-right: 5px;
		}
		
		.pageSwitch form{
			display: inline-block;
		}
	</style>
	<script language="javascript" type="text/javascript">
	</script>
	</head>
	<body>
	<div class="context">
	<div class="module">
		<div class="pageSwitch">
			<form action="queryAlgorithm.jsp">
				<input type="hidden" class="param" name="scopeType" value="1"/>
				<input type="submit"  value="结果页"/ style="margin-top: 10px;">
			</form>
			<form action="queryAlgorithm.jsp">
				<input type="hidden" class="param" name="scopeType" value="2"/>
				<input type="submit"  value="列表页"/ style="margin-top: 10px;">
			</form>
		</div>
		<p>在线测试</p>
		<form action="queryAlgorithm.jsp">
			<input type="hidden" class="param" name="scopeType" value="<%out.println(scopeType);%>"/>
			<div class="searchItem">
				<span>关键词：<input type="text" class="param" name="keyword" value="<%out.println(keyword);%>"/></span>
				<%
				if(scopeType == Constant.SCOPE_TYPE_FOR_SEARCH_PAGE) {
					out.println(
					"<span>新品得分：<input type=\"text\"  class=\"param\" name=\"newScore\"  value=\""+param.newScore+"\"/></span>"+
					"<span>点击得分：<input type=\"text\"  class=\"param\" name=\"clickScore\"  value=\""+param.clickScore+"\"/></span>"+
					"<span>热销得分：<input type=\"text\"  class=\"param\" name=\"hotSaleScore\" value=\""+param.hotSaleScore+"\"/></span>"+
					"<span>语义分析得分：<input type=\"text\"  class=\"param\" name=\"semanticScore\" value=\""+param.semanticScore+"\"/></span>"+
					"<span>人气得分：<input type=\"text\"  class=\"param\" name=\"popularityScore\" value=\""+param.popularityScore+"\"/></span>"+
					"<span>销量得分：<input type=\"text\"  class=\"param\" name=\"saleScore\" value=\""+param.saleScore+"\"/></p>"+
					"<span>库存得分：<input type=\"text\"  class=\"param\" name=\"storeScore\" value=\""+param.storeScore+"\"/></p>");
				} else if(scopeType == Constant.SCOPE_TYPE_FOR_LIST_PAGE){
					out.println(
					"<span>点击得分：<input type=\"text\"  class=\"param\" name=\"listClickScore\"  value=\""+param.listClickScore+"\"/></span>"+
					"<span>销量得分：<input type=\"text\"  class=\"param\" name=\"listSaleScore\" value=\""+param.listSaleScore+"\"/></span>"+
					"<span>销售额得分：<input type=\"text\"  class=\"param\" name=\"listSalesAmountScore\" value=\""+param.listSalesAmountScore+"\"/></span>"+
					"<span>好评率得分：<input type=\"text\"  class=\"param\" name=\"listPraiseScore\" value=\""+param.listPraiseScore+"\"/></span>"+
					"<span>评价得分：<input type=\"text\"  class=\"param\" name=\"listCommentScore\" value=\""+param.listCommentScore+"\"/></span>"+
					"<span>品牌得分：<input type=\"text\"  class=\"param\" name=\"listBrandScore\" value=\""+param.listBrandScore+"\"/></span>");
				}
				
				%>
			</div>
			<div><label>结果：</label>
			<%
				if(scopeType == Constant.SCOPE_TYPE_FOR_SEARCH_PAGE) {
					out.println(AlgorithmFactory.evalFloat(keyword, param));
				} else if(scopeType == Constant.SCOPE_TYPE_FOR_LIST_PAGE){
					out.println(ListPageAlgorithmFactory.evalFloat(keyword, param));
				}
			%>
			</div>
			<div><input type="submit"  value="计算"/ style="margin-top: 10px;"></div>
		</form>
	</div>
	<div class="dashline"></div>
	<div class="module">
		<p>关键词查询结果</p>
		<table>
			<thead>
				<tr>
					<td>关键词</td>
					<td>公式名</td>
					<td>公式class</td>
					<td>公式系数</td>
				</tr>
			</thead>
			<tbody>
				<%
				if(keyword != null) {
					AlgorithmCoeffBean algoCoeffBean = null;
					if(scopeType == Constant.SCOPE_TYPE_FOR_SEARCH_PAGE) {
						algoCoeffBean = AlgorithmFactory.getAlgorithm(keyword);
					} else if(scopeType == Constant.SCOPE_TYPE_FOR_LIST_PAGE){
						algoCoeffBean = ListPageAlgorithmFactory.getAlgorithm(keyword);
					}
					if(algoCoeffBean != null) {
						out.println("<tr><td>"+keyword+"</td><td>"+algoCoeffBean.getAlgorithmName()+"</td><td>"+algoCoeffBean.getAlgorithm().getClass().getName()+"</td><td>"+algoCoeffBean.getCoeffientMap()+"</td></tr>");
					}
				}
				%>
			</tbody>
		</table>
	</div>
	<div class="dashline"></div>
	<div class="module">
		<p>全局公式</p>
		<table>
			<thead>
				<tr>
					<td>公式名</td>
					<td>公式class</td>
					<td>公式系数</td>
				</tr>
			</thead>
			<tbody>
				<%
					AlgorithmCoeffBean globalAlgoCoeff = null;
					if(scopeType == Constant.SCOPE_TYPE_FOR_SEARCH_PAGE) {
						globalAlgoCoeff = AlgorithmFactory.globalAlgorithm;
					} else if(scopeType == Constant.SCOPE_TYPE_FOR_LIST_PAGE){
						globalAlgoCoeff = ListPageAlgorithmFactory.globalAlgorithm;
					}
					if (globalAlgoCoeff != null) {
						out.println("<tr><td>"+globalAlgoCoeff.getAlgorithmName()+"</td><td>"+globalAlgoCoeff.getAlgorithm().getClass().getName()+"</td><td>"+globalAlgoCoeff.getCoeffientMap()+"</td></tr>");
					}
				%>
			</tbody>
		</table>
	</div>
	<div class="dashline"></div>
	<div class="module">
		<p>所有公式</p>
		<table>
			<thead>
				<tr>
					<td>公式名</td>
					<td>公式class</td>
					<td>公式系数</td>
				</tr>
			</thead>
			<tbody>
				<%
					if(scopeType == Constant.SCOPE_TYPE_FOR_SEARCH_PAGE) {
						for (String algoName : AlgorithmFactory.algorithms.keySet()) {
							AlgorithmCoeffBean algoCoeff = AlgorithmFactory.algorithms.get(algoName);
							out.println("<tr><td>"+algoCoeff.getAlgorithmName()+"</td><td>"+algoCoeff.getAlgorithm().getClass().getName()+"</td><td>"+algoCoeff.getCoeffientMap()+"</td></tr>");
						}
					} else if(scopeType == Constant.SCOPE_TYPE_FOR_LIST_PAGE){
						for (String algoName : ListPageAlgorithmFactory.algorithms.keySet()) {
							AlgorithmCoeffBean algoCoeff = ListPageAlgorithmFactory.algorithms.get(algoName);
							out.println("<tr><td>"+algoCoeff.getAlgorithmName()+"</td><td>"+algoCoeff.getAlgorithm().getClass().getName()+"</td><td>"+algoCoeff.getCoeffientMap()+"</td></tr>");
						}
					}
					
				%>
			</tbody>
		</table>
	</div>
	<div class="dashline"></div>
	<div class="module">
		<p>关键词公式规则</p>
		<table>
			<thead>
				<tr>
					<td>关键词</td>
					<td>公式名</td>
					<td>公式class</td>
					<td>公式系数</td>
				</tr>
			</thead>
			<tbody>
				<%
					if(scopeType == Constant.SCOPE_TYPE_FOR_SEARCH_PAGE) {
						for (String kw : AlgorithmFactory.keywordAlgorithms.keySet()) {
							AlgorithmCoeffBean algoCoeff = AlgorithmFactory.keywordAlgorithms.get(kw);
							out.println("<tr><td>"+kw+"</td><td>"+algoCoeff.getAlgorithmName()+"</td><td>"+algoCoeff.getAlgorithm().getClass().getName()+"</td><td>"+algoCoeff.getCoeffientMap()+"</td></tr>");
						}
					} else if(scopeType == Constant.SCOPE_TYPE_FOR_LIST_PAGE){
						for (String kw : ListPageAlgorithmFactory.keywordAlgorithms.keySet()) {
							AlgorithmCoeffBean algoCoeff = ListPageAlgorithmFactory.keywordAlgorithms.get(kw);
							out.println("<tr><td>"+kw+"</td><td>"+algoCoeff.getAlgorithmName()+"</td><td>"+algoCoeff.getAlgorithm().getClass().getName()+"</td><td>"+algoCoeff.getCoeffientMap()+"</td></tr>");
						}
					}
					
				%>
			</tbody>
		</table>
	</div>
	</div>
	</body>
</html>