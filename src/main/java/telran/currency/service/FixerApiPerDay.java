package telran.currency.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;

public class FixerApiPerDay extends AbstractCurrencyConvertor {
    protected String uriString = "http://data.fixer.io/api/latest?access_key=464019d7dc3fa6522758f5905747daed";
    private LocalDate lastRefreshDate;
   

    public FixerApiPerDay(HashMap<String, Double> initialRates) {
        
        this.rates = new HashMap<>(initialRates);
        this.lastRefreshDate = LocalDate.now();
    }

    @Override
    public List<String> strongestCurrencies(int amount) {
        refresh();
        return super.strongestCurrencies(amount);
    }

    @Override
    public List<String> weakestCurrencies(int amount) {
        refresh();
        return super.weakestCurrencies(amount);
    }

    @Override
    public double convert(String codeFrom, String codeTo, int amount) {
        refresh();
        return super.convert(codeFrom, codeTo, amount);
    }

    private void refresh() {
        if (lastRefreshDate == null || !lastRefreshDate.equals(LocalDate.now())) {
            updateRatesFromApi();
            lastRefreshDate = LocalDate.now();
        }
    }

    private void updateRatesFromApi() {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(new URI(uriString)).build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject jsonObject = new JSONObject(response.body());
            JSONObject jsonRates = jsonObject.getJSONObject("rates");

            rates = jsonRates.toMap().entrySet().stream()
                    .collect(HashMap::new, (m, e) -> m.put(e.getKey(), ((Number) e.getValue()).doubleValue()), HashMap::putAll);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update currency rates from API", e);
        }
    }
}
