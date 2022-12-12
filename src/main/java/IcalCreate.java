import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IcalCreate {
	public static void main(String[] args) {
		createFile();
		changeFile("テスト", "20221110", "20221110", "100000", "110000", "説明", "東北電子");
		    try {
		      Runtime rt = Runtime.getRuntime();
		      rt.exec("Z:/JK3B11鈴木之也/GraduationWorks/SotsugyoKenkyu/src/main/webapp/WEB-INF/ical.ics");
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		//Icalファイル単体実行のメソッド
		deleteFile();
	}
	
	public static void createFile() {
		File file = new File("Z:/JK3B11鈴木之也/GraduationWorks/SotsugyoKenkyu/src/main/webapp/WEB-INF/ical.ics");
		/*
		try {
			if(file.createNewFile()){
				System.out.println("ファイル作成成功");
			}else{
				System.out.println("ファイル作成失敗");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		*/
	}
	
	public static void changeFile(String event, String startdate, String enddate, String starttime, String endtime, String description, String location) {
		try {
            FileWriter file = new FileWriter("Z:/JK3B11鈴木之也/GraduationWorks/SotsugyoKenkyu/src/main/webapp/WEB-INF/ical.ics");
            PrintWriter pw = new PrintWriter(new BufferedWriter(file));
            
            pw.println("BEGIN:VCALENDAR");
            pw.println("VERSION:2.0");
            pw.println("PRODID:-//200311//iCal 2.0//JA");
            pw.println("BEGIN:VEVENT");
            pw.println("UID:200311@jc-21.jp");
            pw.println("DTSTART;TZID=Asia/Tokyo:" + startdate + "T" + starttime + "Z");
            pw.println("DTEND;TZID=Asia/Tokyo:" + enddate + "T" + endtime + "Z");
            pw.println("SUMMARY:" + event);
            pw.println("DESCRIPTION:" + description);
            pw.println("LOCATION:" + location);
            pw.println("END:VEVENT");
            pw.println("END:VCALENDAR");
            
            pw.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteFile() {
		Path path = Paths.get("Z:/JK3B11鈴木之也/GraduationWorks/SotsugyoKenkyu/src/main/webapp/WEB-INF/ical.ics");
		try {
			Files.delete(path);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
