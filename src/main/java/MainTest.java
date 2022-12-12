import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MainTest {

	public static void main(String[] args) {
		try {
			
			//keyを取得
			String key = "48948351-e6e1-47bc-933e-d061ab54a2fe";
			
			//Luisを使用して分類
			/*
			FormRecognizer aResult = AnalyzeAPI.getResult(key);
			
			int wordCount = aResult.analyzeResult.paragraphs.length;
			String[] wordBox = new String[wordCount];
			
			for(int a = 0 ; a < aResult.analyzeResult.paragraphs.length ; a++) {
				wordBox[a] = aResult.analyzeResult.paragraphs[a].content;
				//確認用sysout
				System.out.println(wordBox[a]);
			}
			System.out.println("-----------------------------------------------------------");

			*/
			//テスト用Box
			String[] testBox = new String[2];
			testBox[0] = "仙台花火祭り";
			testBox[1] = "2022年10月13日20:15~21:10";

			
			List<String> titleBox = new ArrayList<>();
			List<String> timeBox = new ArrayList<>();
			List<String> placeBox = new ArrayList<>();
			List<String> dateBox = new ArrayList<>();
			List<String> noneBox = new ArrayList<>();
			
			for(int l = 0 ; l < testBox.length ; l++) {
				Luis lResult = LuisAPI.getLuis(testBox[l]);
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
			//中身確認用テスト
			/*
			for(String out : timeBox) {
				System.out.println(out);
			}
			*/
			
			String title = null;
			String startTime = null;
			String endTime = null;
			String startDate = null;
			String endDate = null;
			
			//タイトル
			
			title = titleBox.get(0);
			
			//日付
			if(timeBox.size() != 0) {
				startTime = timeBox.get(0);
			}
			if(dateBox.size() == 0) {
				if(startTime.contains("月")) {
					int gatsuIndex = startTime.indexOf("月");
					startDate = startTime.substring(gatsuIndex - 2, gatsuIndex + 3);
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
			System.out.println("title : " + title);
			System.out.println("startTime : " + startTime);
			System.out.println("endTime : " + endTime);
			System.out.println("startDate : " + startDate);
			System.out.println("endDate : " + endDate);
			
		} catch(Exception e) {
			e.printStackTrace();
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
