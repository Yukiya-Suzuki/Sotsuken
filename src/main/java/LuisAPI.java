import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

public class LuisAPI {

	public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
		Luis result = getLuis("仙台駅");
		if (result != null) {
			System.out.println(result.prediction.topIntent);
		}
	}

	static Luis getLuis(String s) 
			throws IOException, URISyntaxException, InterruptedException {
		Gson gson = new Gson();

		String ens =URLEncoder.encode(s,"UTF-8");
		String url = "https://r04jk3b11-luis-prediction.cognitiveservices.azure.com/"
				+ "luis/prediction/v3.0/apps/dc99ddd5-8b98-4cd5-90e3-32e91ccf4912/slots/production/"
				+ "predict?verbose=true&show-all-intents=true&log=true&"
				+ "subscription-key=36cdd9448c1847c1b04b43e5d2a6b9e9&"
				+ "query="+ens;
		
		InetSocketAddress proxy =new InetSocketAddress("172.17.0.2", 80);

		//JsonReader reader = WebApiConnector.getJsonReader(url,proxy);
		JsonReader reader = WebApiConnector.getJsonReader(url);


		Luis message = null;
		if (reader != null) {
			message = gson.fromJson(reader, Luis.class);
			reader.close();
		}
		return message;
	}

}
class Luis{
	String query;
	Prediction prediction;
	String[] entities;
	String[] utterances;
}
class Prediction{
	String topIntent;
	Intents intents;
}
class Intents{
	Title title;
	Time time;
	Place place;
	Date date;
	None none;
}
class Title{
	double score;
}
class Time{
	double score;
}
class Place{
	double score;
}
class Date{
	double score;
}
class None{
	double score;
}

