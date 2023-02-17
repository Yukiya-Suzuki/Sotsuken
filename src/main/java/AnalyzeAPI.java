import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class AnalyzeAPI extends HttpServlet {
	public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
		File image = new File("C:\\Users\\200311\\Desktop\\restraunt.jpg"); 
		byte[] fileContent = Files.readAllBytes(image.toPath());
		String base64Image = Base64.getEncoder().encodeToString(fileContent);
		
		FormRecognizer sendResult = sendImage(base64Image);
		if (sendResult == null) {
			System.out.println("success");
		} else {
			System.out.println(sendResult.error.innererror.message);
		}
		
		/*
		URL url = new URL("https://calendar.cognitiveservices.azure.com/"
				+ "/formrecognizer/documentModels/prebuilt-read"
				+ ":analyze?api-version=2022-08-31");
        URLConnection conn = url.openConnection();
        
        Map headers = conn.getHeaderFields();
        Set<String> keys = headers.keySet();
        for( String key : keys ){
            String val = conn.getHeaderField(key);
            System.out.println(key+"    "+val);
        }
        System.out.println(conn.getLastModified());
        */
        
        
		String key = "6257009f-526c-410d-a6a9-ab2d04cbf47d";
		FormRecognizer analyzeResult = getResult(key);
		if(analyzeResult != null) {
			System.out.println(analyzeResult.status);
		}
	}
	
	protected static FormRecognizer sendImage(String s) throws IOException, URISyntaxException, InterruptedException {
		Gson gson = new Gson();
		
		String strurl ="https://calendar.cognitiveservices.azure.com/"
				+ "/formrecognizer/documentModels/"
				+ "prebuilt-read"
				+ ":analyze?api-version=2022-08-31"
				+ "&stringIndexType=textElements";
		
		Map<String,String> map = new HashMap<>();
		map.put("Ocp-Apim-Subscription-Key","9abfc957d16849f7bbf5b669a335f3c1");
		map.put("Content-Type","application/json");
		
		SendDocs doc=new SendDocs();
		doc.base64Source=s;

		String jsonData = new Gson().toJson(doc);
		InetSocketAddress proxy =new InetSocketAddress("172.17.0.2", 80);
		//JsonReader reader = WebApiConnector.postJsonReader(strurl,proxy,map,jsonData);
		JsonReader reader = WebApiConnector.postJsonReader(strurl,map,jsonData);
		FormRecognizer sendResult = null;
		if (reader != null) {
			sendResult = gson.fromJson(reader, FormRecognizer.class);
			reader.close();
		}
		return sendResult;
	}
	
	protected static FormRecognizer getResult(String key) throws IOException, URISyntaxException, InterruptedException {
		Gson gson = new Gson();
		
		String strurl ="https://calendar.cognitiveservices.azure.com/"
				+ "/formrecognizer/documentModels/"
				+ "prebuilt-read/"
				+ "analyzeResults/"
				+ key 
				+"?api-version=2022-08-31"
				+ "&subscription-key="
				+ "9abfc957d16849f7bbf5b669a335f3c1";

		InetSocketAddress proxy =new InetSocketAddress("172.17.0.2", 80);
		//JsonReader reader = WebApiConnector.getJsonReader(strurl,proxy);
		JsonReader reader = WebApiConnector.getJsonReader(strurl);
		
		FormRecognizer analyzeResult = null;
		if (reader != null) {
			analyzeResult = gson.fromJson(reader, FormRecognizer.class);
			reader.close();
		}
		return analyzeResult;
	}
}

class Error {
	String code;
	String message;
	InnerError innererror;
}
class InnerError {
	String code;
	String message;
}
class SendDocs{
    String base64Source;
}
class SendSource{
	SendDocs[] documents;
}
class FormRecognizer{
	String status;
	String createdDateTime;
	String lastUpdateDateTime;
	AnalyzeResult analyzeResult;
	Error error;
}
class AnalyzeResult{
	String apiVersion;
	String modelId;
	String stringIndexType;
	String content;
	Pages[] pages;
	Paragraphs[] paragraphs;
	Styles[] styles;
	Languages[] languages;
}
class Pages {
	int pageNumber;
	double angle;
	int width;
	int height;
	String unit;
	Words[] words;
	//Lines lines;
	Span span;
	String kind;
}
class Words {
	String content;
	int[] polygon;
	double confidence;
	Span span;
}
class Span {
	int offset;
	int length;
}
class Lines {
	String content;
	int[] polygon;
	Span spans;
}
class Paragraphs {
	Span[] spans;
	BoundingRegions[] boundingRegions;
	String content;
}
class BoundingRegions {
	int PageNumber;
	int[] polygon;
}
class Languages {
	Span[] spans;
	String locate;
	double confidence;
}
class Styles {
	double confidence;
	Span spans;
	boolean isHandwritten;
}