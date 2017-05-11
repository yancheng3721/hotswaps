<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.concentrate.search.hotswap.builder.AlgorithmBuilder"%>
<%@page import="com.concentrate.search.hotswap.bean.ChannelSortFunction"%>
<%@page import="com.concentrate.search.hotswap.bean.SortFunctionCoeffBean"%>
<%@page import="com.concentrate.search.hotswap.*"%>
<%@page import="com.concentrate.search.hotswap.param.SortParam"%>
<%@page import="com.concentrate.search.hotswap.config.Constant"%>
<%@page import="com.concentrate.search.hotswap.param.SearchParam"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<%@page import="java.util.*,java.lang.reflect.*;" %>
<%
	String channel = request.getParameter("channel");
	String scopeType = request.getParameter("scopeType");
	SortParam param = new SortParam();

	Field[] fields = SortParam.class.getDeclaredFields();
	Map<String, Float> fieldsMap = new HashMap<String, Float>();
	for (Field f : fields) {
		if(f.getType().equals(float.class)) {
			String name = f.getName();
			String value = request.getParameter(name);
			fieldsMap.put(name, (value != null && !value.equals("")) ? Float.valueOf(value) : 0f);
			f.setFloat(param, fieldsMap.get(name));
		}
	}
	
	String keyword = request.getParameter("keyword");
	keyword = keyword != null ? keyword.trim() : "";
	scopeType = scopeType != null ? scopeType.trim() : "";
	ChannelSortFunction rsf = SortFunctionFactory.getChannelSortFunc(channel, scopeType);
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
			display:inline-block;
			padding: 2px;
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
		<p>在线测试</p>
		<form action="querySortFunction.jsp">
			<div class="searchItem">
				<span>频道：<input type="text" class="param" name="channel" value="<%out.println(channel);%>"/></span>
				<span>页面类型：
				<select class="param" name="scopeType" style="width: 80px;">
					<option <%if(scopeType.equals(AlgorithmBuilder.DEFAULT_SCOPE_TYPE))out.println("selected= \"selected\"");%> value="<%out.println(AlgorithmBuilder.DEFAULT_SCOPE_TYPE);%>"><%out.println(AlgorithmBuilder.DEFAULT_SCOPE_TYPE);%></option>
					<option <%if(scopeType.equals(AlgorithmBuilder.SEARCH_SCOPE_TYPE))out.println("selected= \"selected\"");%> value="<%out.println(AlgorithmBuilder.SEARCH_SCOPE_TYPE);%>"><%out.println(AlgorithmBuilder.SEARCH_SCOPE_TYPE);%></option>
					<option <%if(scopeType.equals(AlgorithmBuilder.LIST_SCOPE_TYPE))out.println("selected= \"selected\"");%> value="<%out.println(AlgorithmBuilder.LIST_SCOPE_TYPE);%>"><%out.println(AlgorithmBuilder.LIST_SCOPE_TYPE);%></option>
					<option <%if(scopeType.equals(AlgorithmBuilder.VIRTUAL_LIST_SCOPE_TYPE))out.println("selected= \"selected\"");%> value="<%out.println(AlgorithmBuilder.VIRTUAL_LIST_SCOPE_TYPE);%>"><%out.println(AlgorithmBuilder.VIRTUAL_LIST_SCOPE_TYPE);%></option>
				</select>
				</span>
				<span>关键词：<input type="text" class="param" name="keyword" value="<%out.println(keyword);%>"/></span></br>
				<%
		        List<String> list=new ArrayList<String>();
		        for(String fieldName : fieldsMap.keySet()) {
		        	list.add(fieldName);
				}
		        SortParam.sortField(list);
				for(String fieldName : list) {
					out.println(
							"<span>"+fieldName+"：<input type=\"text\"  class=\"param\" name=\""+fieldName+"\"  value=\""+fieldsMap.get(fieldName)+"\"/></span>");
				}
				%>
			</div>
			<div><label>结果：</label>
			<%
				out.println(SortFunctionFactory.evalFloat(channel, scopeType, keyword, param));
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
					<td>频道</td>
					<td>页面类型</td>
					<td>关键词</td>
					<td>公式名</td>
					<td>公式class</td>
					<td>公式系数</td>
				</tr>
			</thead>
			<tbody>
				<%
				if(channel != null && keyword != null) {
					SortFunctionCoeffBean algoCoeffBean = SortFunctionFactory.getFunc(channel, scopeType, keyword);
					if(algoCoeffBean != null) {
						out.println("<tr><td>"+algoCoeffBean.getChannelName()+"</td><td>"+algoCoeffBean.getScopeType()+"</td><td>"+keyword+"</td><td>"+algoCoeffBean.getFuncName()+"</td><td>"+algoCoeffBean.getFunc().getClass().getName()+"</td><td>"+algoCoeffBean.getCoeffientMap()+"</td></tr>");
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
					<td>频道</td>
					<td>页面类型</td>
					<td>公式名</td>
					<td>公式class</td>
					<td>公式系数</td>
				</tr>
			</thead>
			<tbody>
				<%
					if(rsf != null) {
						SortFunctionCoeffBean globalAlgoCoeff = rsf.getGlobalAlgorithm();
						if (globalAlgoCoeff != null) {
							out.println("<tr><td>"+globalAlgoCoeff.getChannelName()+"</td><td>"+globalAlgoCoeff.getScopeType()+"</td><td>"+globalAlgoCoeff.getFuncName()+"</td><td>"+globalAlgoCoeff.getFunc().getClass().getName()+"</td><td>"+globalAlgoCoeff.getCoeffientMap()+"</td></tr>");
						}
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
					<td>频道</td>
					<td>页面类型</td>
					<td>公式名</td>
					<td>公式class</td>
					<td>公式系数</td>
				</tr>
			</thead>
			<tbody>
				<%
				if (rsf != null) {
					for (String algoName : rsf.getAlgorithms().keySet()) {
						SortFunctionCoeffBean algoCoeff = rsf.getAlgorithms().get(algoName);
						out.println("<tr><td>"+algoCoeff.getChannelName()+"</td><td>"+algoCoeff.getScopeType()+"</td><td>"+algoCoeff.getFuncName()+"</td><td>"+algoCoeff.getFunc().getClass().getName()+"</td><td>"+algoCoeff.getCoeffientMap()+"</td></tr>");
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
					<td>频道</td>
					<td>页面类型</td>
					<td>关键词</td>
					<td>公式名</td>
					<td>公式class</td>
					<td>公式系数</td>
				</tr>
			</thead>
			<tbody>
				<%
				if (rsf != null) {
					for (String kw : rsf.getKeywordAlgorithms().keySet()) {
						SortFunctionCoeffBean algoCoeff = rsf.getKeywordAlgorithms().get(kw);
						out.println("<tr><td>"+algoCoeff.getChannelName()+"</td><td>"+algoCoeff.getScopeType()+"</td><td>"+kw+"</td><td>"+algoCoeff.getFuncName()+"</td><td>"+algoCoeff.getFunc().getClass().getName()+"</td><td>"+algoCoeff.getCoeffientMap()+"</td></tr>");
					}
					
				}
				%>
			</tbody>
		</table>
	</div>
	</div>
	</body>
</html>