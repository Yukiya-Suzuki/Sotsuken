import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/OperationAPI")
public class OperationAPIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public OperationAPIServlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		List<String> errMessage = (List<String>)request.getAttribute("errMessage");
		
		String filename = null;
		File imageFile = null;
		byte[] fileContent = null;
		String base64Image = null;
		try {
			//filename = "C:/Users/200311/Desktop/Hanabi.png";
			filename = (String)request.getAttribute("saveFile");
			imageFile = new File(filename);
			fileContent = Files.readAllBytes(imageFile.toPath());
			
			base64Image = Base64.getEncoder().encodeToString(fileContent);
		} catch(Exception e) {
			e.printStackTrace();
		}
		try {
			FormRecognizer sendResult = AnalyzeAPI.sendImage(base64Image);
			if (sendResult != null) {
				System.out.println(sendResult.error.message);
				errMessage.add("画像解析中にエラーが発生しました");
				request.getRequestDispatcher("error.jsp").forward(request, response);
			} else {
			
				String key = "6b7d3b16-5355-4793-ac78-4a207d6868dd";
				//Luisを使用して分類
				FormRecognizer aResult = AnalyzeAPI.getResult(key);
				
				int wordCount = aResult.analyzeResult.paragraphs.length;
				String[] wordBox = new String[wordCount];
				
				for(int a = 0 ; a < aResult.analyzeResult.paragraphs.length ; a++) {
					wordBox[a] = aResult.analyzeResult.paragraphs[a].content;
				}
				/*
				URL obj = new URL("https://calendar.cognitiveservices.azure.com/"
						+ "/formrecognizer/documentModels/prebuilt-read"
						+ ":analyze?api-version=2022-08-31&stringIndexType=textElements");
				URLConnection conn = obj.openConnection();

				Map<String, List<String>> map = conn.getHeaderFields();
				for(Map.Entry<String, List<String>> entry : map.entrySet()) {
					String key = entry.getKey();
					List<String> values = entry.getValue();
					for(String val : values) {
						System.out.println(key + " : " + val);
					}
				}
				*/
				

				List<String> titleBox = new ArrayList<>();
				List<String> timeBox = new ArrayList<>();
				List<String> placeBox = new ArrayList<>();
				List<String> dateBox = new ArrayList<>();
				List<String> noneBox = new ArrayList<>();
				
				for(int l = 0 ; l < wordBox.length ; l++) {
					Luis lResult = LuisAPI.getLuis(wordBox[l]);
					switch(lResult.prediction.topIntent.toString()) {
						case "Title" :
							titleBox.add(lResult.query);
							break;
						case "Time" :
							timeBox.add(lResult.query);
							break;
						case "Place" :
							placeBox.add(lResult.query);
							break;
						case "Date" :
							dateBox.add(lResult.query);
							break;
						default :
							noneBox.add(lResult.query);
							break;
					}
				}
				
				String title = null;
				String startTime = null;
				String endTime = null;
				String startDate = null;
				String endDate = null;
				String place = null;
				
				if(titleBox.size() != 0) {
					title = titleBox.get(0);
				}
				
				if(timeBox.size() != 0) {
					startTime = timeBox.get(0);
				}
				if(dateBox.size() == 0) {
					if(startTime.contains("月")) {
						int gatsuIndex = startTime.indexOf("月");
						startDate = startTime.substring(gatsuIndex - 1, gatsuIndex + 2);
					} else if(startTime.contains("/") && startTime.contains("2022")) {
						int slashIndex = startTime.indexOf("/");
						startDate = startTime.substring(slashIndex + 2, slashIndex + 4);
					} else if(startTime.contains("/") && !startTime.contains("2022")) {
						int slashIndex = startTime.indexOf("/");
						startDate = startTime.substring(slashIndex - 2, slashIndex +2);
					}
				}
				if(dateBox.size() != 0) {
					startDate = dateBox.get(0);
					if(dateBox.size() == 2) {
						endDate = dateBox.get(1);
					}
				}
				if(dateBox.size() == 1 || dateBox.size() == 0) {
					endDate = startDate;
				}
				
				//時間
				if(startTime.contains("月") || startTime.contains("/")) {
					int colonIndex = startTime.indexOf(":");
					if(startTime.contains("~")) {
						endTime = startTime.substring(colonIndex + 4, colonIndex + 9);
					}
					startTime = startTime.substring(colonIndex - 2, colonIndex + 3);
				}
				if(startTime.contains("~")) {
					int karaIndex = startTime.indexOf("~");
					endTime = startTime.substring(karaIndex + 1,startTime.length());
					startTime = startTime.substring(0,karaIndex);
				}
				if(timeBox.size() == 2) {
					endTime = timeBox.get(1);
				}
				
				if(placeBox.size() != 0) {
					place = placeBox.get(0);
				}
				
				//----フォーマット変換処理
				startTime = TimeFormatChange(startTime);
				endTime = TimeFormatChange(endTime);
				
				startDate = DateFormatChange(startDate);
				endDate = DateFormatChange(endDate);
				
				//----日付の順番を合わせる処理
				String sDate = startDate;
				String eDate = endDate;
				sDate = sDate.replace("-", "");
				eDate = eDate.replace("-", "");
				int isDate = Integer.parseInt(sDate);
				int ieDate = Integer.parseInt(eDate);
				if(isDate > ieDate) {
					String s = startDate;
					startDate = endDate;
					endDate = s;
				}
				
				//----時間の順番を合わせる処理（日付が違う場合はそのまま）
				String sTime = startTime;
				String eTime = endTime;
				sTime = sTime.replace(":", "");
				eTime = eTime.replace(":", "");
				int isTime = Integer.parseInt(sTime);
				int ieTime = Integer.parseInt(eTime);
				if(isTime > ieTime) {
					String s = startTime;
					startTime = endTime;
					endTime = s;
				}
				
				
				if(title == null && startTime == null && endTime == null && startDate == null && endDate == null && place == null) {
					errMessage.add("内容が読み込めませんでした");
					request.setAttribute("errMessage", errMessage);
					request.getRequestDispatcher("/error.jsp").forward(request, response);
				}
				
				request.setAttribute("title", title);
				request.setAttribute("startTime", startTime);
				request.setAttribute("endTime", endTime);
				request.setAttribute("startDate", startDate);
				request.setAttribute("endDate", endDate);
				request.setAttribute("place", place);
				request.getRequestDispatcher("/change.jsp").forward(request, response);
		}
		} catch(Exception e) {
			errMessage.add("例外エラーが発生しました");
			request.setAttribute("errMessage", errMessage);
			request.getRequestDispatcher("/error.jsp").forward(request, response);
		}
	}
	public static String DateFormatChange(String date) {
		String formatDate = date;
		if(formatDate.contains("年")) {
			formatDate = formatDate.replace("年", "-");
		}
		if(formatDate.contains("月")) {
			formatDate = formatDate.replace("月", "-");
		}
		if(formatDate.contains("日")) {
			formatDate = formatDate.replace("日", "");
		}
		if(formatDate.contains("/")) {
			formatDate = formatDate.replace("/", "-");
		}

		if(formatDate.length() < 6) {		//----年がない場合今年を入れる
			LocalDate currentDate = LocalDate.now();
			int currentYear = currentDate.getYear();
				formatDate = currentYear + "-" + formatDate;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(formatDate);
		if(formatDate.charAt(6) == '-') {
			sb.insert(5, "0");
		}
		if(formatDate.length() == 8) {
			sb.insert(8, "0");
		}
		formatDate =sb.toString();
		return formatDate;
	}
		
	public static String TimeFormatChange(String time) {
		String formatTime = time;
		if(formatTime.contains("時")) {
			formatTime = formatTime.replace("時", ":");
		}
		if(formatTime.contains("分")) {
			formatTime = formatTime.replace("分", "");
		}
		return formatTime;
	}
}
