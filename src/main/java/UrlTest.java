

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UrlTest
 */
@WebServlet("/UrlTest")
public class UrlTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UrlTest() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		
		/*
		URL obj = new URL("https://calendar.cognitiveservices.azure.com/"
					+ "/formrecognizer/documentModels/prebuilt-read/"
					+ ":analyze?api-version=2022-08-31&stringIndexType=textElements");
		*/
		URL obj = new URL("http://www.yahoo.co.jp");
		URLConnection conn = obj.openConnection();
		
		Map<String, List<String>> map = conn.getHeaderFields();
		for(Map.Entry<String, List<String>> entry : map.entrySet()) {
			String key = entry.getKey();
			List<String> values = entry.getValue();
			for(String val : values) {
				response.getWriter().println(key + " : " + val + "<br>");
			}
		}
		response.getWriter().println("<hr>\n");
	}
}

	
