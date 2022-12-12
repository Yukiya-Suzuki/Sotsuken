import java.time.LocalDate;

public class FormatTest {
	public static void main(String[] args) {
		String a = "2022-02-27";
		System.out.println(DateFormatChange(a));
		
		String b = DateFormatChange("2022年3月10日");
		System.out.println(DateFormatChange(b));
		
		String c = DateFormatChange("02月27日");
		System.out.println(DateFormatChange(c));
		
		String d = DateFormatChange("7月16日");
		System.out.println(DateFormatChange(d));
		
		String e = DateFormatChange("02/27");
		System.out.println(DateFormatChange(e));
	}
	public static String DateFormatChange(String date) {
		String FormatDate = date;
		if(FormatDate.contains("年")) {
			FormatDate = FormatDate.replace("年", "‐");
		}
		if(FormatDate.contains("月")) {
			FormatDate = FormatDate.replace("月", "-");
		}
		if(FormatDate.contains("日")) {
			FormatDate = FormatDate.replace("日", "");
		}
		if(FormatDate.contains("/")) {
			FormatDate = FormatDate.replace("/", "-");
		}

		if(FormatDate.length() < 6) {		//----年がない場合今年を入れる
			LocalDate currentDate = LocalDate.now();
			 int currentYear = currentDate.getYear();
			 FormatDate = currentYear + "-" + FormatDate;
		}
		return FormatDate;
	}
}
