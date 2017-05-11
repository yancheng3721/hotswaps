<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<%@page import="com.concentrate.search.hotswap.*" %>
<%
	String type = request.getParameter("type");
	String channel = request.getParameter("channel");
	String scopeType = request.getParameter("scopeType");
	
	boolean success = false;
	if("0".equals(type)) {
		success = SortFunctionFactory.refreshAlgorithm(channel, scopeType);
	} else if("1".equals(type)){
		success = SortFunctionFactory.refreshKeywordAlgorithmaMap(channel, scopeType);
	} else if("2".equals(type)){
		success = SortFunctionFactory.initAlgorithmsMap();
	} else if("3".equals(type)){
		success = SortFunctionFactory.initKeywordAlgorithmaMap();
	}
	
    response.getWriter().println(success?"1":"0");  
%>