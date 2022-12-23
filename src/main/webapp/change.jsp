<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="change.css">
<title>内容の変更</title>
</head>
<body>
	<%
	String title = (String)request.getAttribute("title");
	String startDate = (String)request.getAttribute("startDate");
	String endDate = (String)request.getAttribute("endDate");
	String startTime = (String)request.getAttribute("startTime"); 
	String endTime = (String)request.getAttribute("endTime"); 
	String place = (String)request.getAttribute("place"); 
	
	%>
	<H1>内容の確認</H1>
	<form action="IcalSet" method="post">
	
	
	<table border="1">
		<tr>
			<td>タイトル</td>
			<td><input type="text" name="changed_title" value="<%=title%>"></td>
		</tr>
		<tr>
			<td>日付</td>
			<td><input type="date" name="changed_startDate" value="<%=startDate%>">
			～<input type="date" name="changed_endDate" value="<%=endDate%>"></td>
		</tr>
		<tr>
			<td>時間</td>
			<td><input type="time" name="changed_startTime" value="<%=startTime%>">
			～
			<input type="time" name="changed_endTime" value="<%=endTime%>"></td>
		</tr>
		<tr>
			<td>場所</td>
			<td><input type="text" name="changed_place" value="<%=place%>"></td>
		</tr>
	</table>
	<button type="submit" class="btn">確定</button>
	<a href="/画像入力画面.jsp" class="btn">戻る</a>
	</form>
</body>
</html>