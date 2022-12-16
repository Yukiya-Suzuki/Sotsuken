<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
body {
				background-color: #FDF5E6;
				text-align:center;
				wigth:500px;
				font-size: 25px;
				height: 200px;
		}
		
		.btn {
 		 display: inline-block;
 		 max-width: 180px;
 		 text-align: center;
 		 border: 2px solid #9EC34B;
 		 font-size: 15px;
 		 color: #9EC34B;
 		 text-decoration: none;
 		 font-weight: bold; 
 		 padding: 10px ;
 		 border-radius: 4px;
 		 transition: .4s;
  		background-color: #9EC34B;
  		border-color: #CBE585;
 		 color:black;
 		 }
 		 table{
		padding:3px;
		margin:auto;
 		 
			}
		.size{
		margin: 0 auto;
		width: 80%;
		max-width: 500px;
		}
		
		
	 }
			
</style>
<title>予定を入力</title>
</head>
<body>

	<form method="post" action="in" >
		<main class="size">
			 		<table border="1">
		<tr>
			<td>タイトル</td>
			<td><input type="text"  name="changed_tytle"  value=""  ></td>
		</tr>
		<tr>
			<td>日付</td>
			<td><input type="date" name="changed_startDate" value="">
			～
			<input type="date" name="changed_endDate" value=""></td>
		</tr>
		<tr>
			<td>時間</td>
			<td><input type="time" name="changed_startTime" value="">
			～
			<input type="time" name="changed_endTime" value=""></td>
		</tr>
		<tr>
			<td>場所</td>
			<td><input type="text" name="changed_place" value=""></td>
		</tr>
	</table>
		</main>
					<button type="submit" class = "btn">予定を追加</button>
					<button type="reset" class = "btn">リセット</button>
					
	</form>
</body>

</html>