<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<%@page import="com.concentrate.search.hotswap.*" %>
<%
	String type = request.getParameter("type");
	String scopeType = request.getParameter("scopeType");
	
	boolean success = false;
	if ("1".equals(scopeType)) { //刷新搜索结果页算法
		if("0".equals(type)) {
			success = AlgorithmFactory.refreshAlgorithm();
		} else if("1".equals(type)){
			success = AlgorithmFactory.initKeywordAlgorithmaMap();
		} else if("2".equals(type)){
			success = AlgorithmFactory.initAlgorithmsMap();
		}
	} else if ("2".equals(scopeType)) { //刷新列表页算法
		if("0".equals(type)) {
			success = ListPageAlgorithmFactory.refreshAlgorithm();
		} else if("1".equals(type)){
			success = ListPageAlgorithmFactory.initKeywordAlgorithmaMap();
		} else if("2".equals(type)){
			success = ListPageAlgorithmFactory.initAlgorithmsMap();
		}
	}
	
    response.getWriter().println(success?"1":"0");  
%>