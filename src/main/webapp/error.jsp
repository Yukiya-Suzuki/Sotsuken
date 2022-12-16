<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="error.css">
		<title>error</title>
	</head>
	<body>
		<%
		List<String> errMessage = (List<String>)request.getAttribute("errMessage");
		%>
		<h2>エラーが発生しました</h2>
		<b>エラー原因</b>
		<%
		for(String error : errMessage) {
		%>
			<p><%= error %></p>
		<%
		}
		%>
		<a href="historyback()" class="btn">リトライ</a>

</body>
</html>