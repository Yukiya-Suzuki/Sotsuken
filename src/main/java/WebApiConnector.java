import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.util.Map;

import com.google.gson.stream.JsonReader;

public class WebApiConnector {

	public static JsonReader getJsonReader(String url) throws IOException, URISyntaxException, InterruptedException {
		HttpClient client = getHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).build();
		HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
		return new JsonReader(new InputStreamReader(response.body()));
	}

	
	public static JsonReader postJsonReader(String url, Map<String, String> map, String jsonData)
			throws URISyntaxException, IOException, InterruptedException {
		HttpClient client = getHttpClient();
		Builder requestBuilder = HttpRequest.newBuilder().uri(new URI(url))
				.POST(HttpRequest.BodyPublishers.ofString(jsonData));
		for (String key : map.keySet()) {
			requestBuilder.setHeader(key, map.get(key));
		}
		HttpRequest request = requestBuilder.build();
		HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

		InputStreamReader inputStreamReader = new InputStreamReader(response.body());
		return new JsonReader(inputStreamReader);
	}

	public static JsonReader postJsonReader(String url, InetSocketAddress proxy, Map<String, String> map,
			String jsonData) throws URISyntaxException, IOException, InterruptedException {
		HttpClient client = getHttpClient(proxy);
		Builder requestBuilder = HttpRequest.newBuilder().uri(new URI(url))
				.POST(HttpRequest.BodyPublishers.ofString(jsonData));
		for (String key : map.keySet()) {
			requestBuilder.setHeader(key, map.get(key));
		}
		HttpRequest request = requestBuilder.build();
		HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

		InputStreamReader inputStreamReader = new InputStreamReader(response.body());
		return new JsonReader(inputStreamReader);
	}
	
	
//自作Connector（Mapを使用したい）
	public static JsonReader getJsonReader(String url, InetSocketAddress proxy, Map<String , String> map)
			throws IOException, URISyntaxException, InterruptedException {
		HttpClient client = getHttpClient(proxy);
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).build();
		HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
		return new JsonReader(new InputStreamReader(response.body()));
	}
	
	
	public static JsonReader getJsonReader(String url, InetSocketAddress proxy)
			throws IOException, URISyntaxException, InterruptedException {
		HttpClient client = getHttpClient(proxy);
		HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).build();
		HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
		return new JsonReader(new InputStreamReader(response.body()));
	}


	private static HttpClient getHttpClient() {
		HttpClient client = HttpClient.newHttpClient();
		return client;
	}

	private static HttpClient getHttpClient(InetSocketAddress proxy) {
		HttpClient client = HttpClient.newBuilder().proxy(ProxySelector.of(proxy)).build();
		return client;
	}

}
