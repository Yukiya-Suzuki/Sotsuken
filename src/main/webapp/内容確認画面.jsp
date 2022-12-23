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
	%> 
	<img src="<%= saveFile %>">
<H1>こちらの画像でよろしいですか？</H1>
	<div class="container">
	<form method="post" action="OperationAPI">
	<input type="hidden" value="<%= saveFile %>">
	<button type="submit" class="btn">確定</button>
	<button onclick="historyback()" class="btn">戻る</button>
	</form>
	</div>
</body>
</html>