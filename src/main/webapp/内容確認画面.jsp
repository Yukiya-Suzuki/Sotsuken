<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="内容確認画面.css">
<title>内容確認画面</title>
</head>
<body>
	<%
	String saveFile = (String)request.getAttribute("saveFile");
	System.out.println(saveFile);
	%> 
	<img src="<%= saveFile %>">
<H1>こちらの画像でよろしいですか？</H1>
	<div class="container">
	<a href="ImageGetServlet" class="btn">確定</a>
	<a href="画像入力画面.jsp" class="btn">戻る</a>
	</div>
</body>
</html>