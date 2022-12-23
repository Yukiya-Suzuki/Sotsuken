<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="画像入力画面2.css">
<title>画像入力画面</title>
</head>
<body>
<h1>読み込みたい画像を選択してください</h1>
<form action="imageGet" method="post" enctype="multipart/form-data">
    <input type="file" name="pict" id="pict" accept="image/*" onchange="previewFile(this);">
 		<button type="submit" class="btn">送信する</button>
</form>
<!--<p>カメラを起動する</p>
<form action ="imageGet" method="post" enctype="multipart/form-data">
	<input type="file" accept="image/*" capture="camera" name="pict" id="pict"  onchange="previewFile(this);">
	<button type="submit" class="btn">送信する</button>
</form>
-->
 <p>プレビュー</p>
  <img id="preview"width="350px" height="625px">
    <script>
    function previewFile(example){
    	var fileData = new FileReader();
    	fileData.onload = (function() {
     		//id属性が付与されているimgタグのsrc属性に、fileReaderで取得した値の結果を入力することで
      		//プレビュー表示している
      		document.getElementById('preview').src = fileData.result;
    	});
    	fileData.readAsDataURL(example.files[0]);
  	}
    
  </script>
</body>
</html>