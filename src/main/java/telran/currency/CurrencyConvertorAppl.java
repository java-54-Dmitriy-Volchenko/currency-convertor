package telran.currency;

import telran.currency.service.FixerApiPerDay;

import telran.view.InputOutput;
import telran.view.Menu;
import telran.view.SystemInputOutput;
import telran.view.Item;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;

public class CurrencyConvertorAppl {

    public static void main(String[] args) throws Exception {
        
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(new URI("http://data.fixer.io/api/latest?access_key=464019d7dc3fa6522758f5905747daed"))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

      
        JSONObject jsonObject = new JSONObject(response.body());
        JSONObject jsonRates = jsonObject.getJSONObject("rates");

      
        Map<String, Double> ratesMap = jsonRates.toMap().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> ((Number) e.getValue()).doubleValue()
                ));

      
        FixerApiPerDay currencyConvertor = new FixerApiPerDay(new HashMap<>(ratesMap));

        InputOutput io = new  SystemInputOutput();
        
        List<Item> itemList = CurrencyItems.getItems(currencyConvertor);  
        itemList.add(Item.ofExit());
        Item[] items = itemList.toArray(new Item[0]);
        Menu menu = new Menu("Currency Conversion Menu", items);
        menu.perform(io);
    }
}
