package telran.currency;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse.BodySubscriber;
import java.net.http.HttpResponse.ResponseInfo;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;

public class HttpTestAppl {

	public static void main(String[] args) throws Exception {
		HttpClient httpClient = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder
	(new URI("http://data.fixer.io/api/latest?access_key=464019d7dc3fa6522758f5905747daed"))
	.build();
		HttpResponse<String> response =
				httpClient.send(request, BodyHandlers.ofString());
		JSONObject jsonObject = new JSONObject(response.body());
		JSONObject jsonRates = jsonObject.getJSONObject("rates");
		String[]codes = {"USD", "EUR", "ILS", "RUB", "KUK"};
		Map<String, Double> map = Arrays.stream(codes)
				.collect(Collectors.toMap(c -> c, c -> jsonRates.optDouble(c)));
		System.out.println(map);
		

	}

}
