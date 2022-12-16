import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/IcalSet")
public class IcalSetServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public IcalSetServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String title = null;
		String startDate = null;
		String endDate = null;
		String startTime = null;
		String endTime = null;
		String place = null;
		try {
			title = request.getParameter("changed_title");
			startDate = encodeDate(request.getParameter("changed_startDate"));
			endDate = encodeDate(request.getParameter("changed_endDate"));
			startTime = encodeTime(request.getParameter("changed_startTime")) +"00";
			endTime = encodeTime(request.getParameter("changed_endTime")) + "00";
			place = request.getParameter("changed_place");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		IcalCreate.createFile();
		
		IcalCreate.changeFile(title, startDate, endDate, startTime, endTime, place);
		  
		try {
			Runtime rt = Runtime.getRuntime();
			rt.exec("ical.ics");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		  
	
	public static String encodeDate(String date) {
		String x = "";
		for(int i = 0 ; i < date.length(); i++){
			char ch = date.charAt(i);
			if(ch >= '0' && ch <= '9') {
				x =  x + String.valueOf(ch);
			}
		}
		return x;
	}
			
	public static String encodeTime(String time) {
		String y = "";
		for(int i = 0 ; i < time.length(); i++){
			char ch = time.charAt(i);
			if(ch >= '0' && ch <= '9') {
				y =  y + String.valueOf(ch);
			}
		}
		return y;
	}
				
		
}
		