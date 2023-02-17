<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="error.css">
<title>登録</title>
</head>
<body background="カレンダー.jpg">
<h1>予定が完成しました</h1>
<h1>ボタンを押して追加画面に移動してください</h1>
<% HttpSession imageSession = request.getSession();
		String saveFile = (String)imageSession.getAttribute("saveFile");
		System.out.println(saveFile);
		String url;
		if(saveFile.equals("upload/restraunt.jpg")) {
			url = "location.href='/ical2.ics'";
		} else {
			url ="location.href='/ical.ics'";
		}
		%>
<button class="btn" onclick=<%=url %>>カレンダーに登録する</button>
</body>
</html>