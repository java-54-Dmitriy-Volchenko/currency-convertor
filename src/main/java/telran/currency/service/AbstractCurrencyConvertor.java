package telran.currency.service;

import java.util.*;
import java.util.stream.Collectors;

public class AbstractCurrencyConvertor implements CurrencyConvertor {
protected Map<String, Double> rates; //key - currency ISO code;
//value - amount of code's units in 1 EUR
	@Override
	public List<String> strongestCurrencies(int amount) {
		 return rates.entrySet()
	                .stream()
	                .sorted(Map.Entry.comparingByValue()) 
	                .limit(amount)
	                .map(Map.Entry::getKey)
	                .collect(Collectors.toList());
	}

	@Override
	public List<String> weakestCurrencies(int amount) {
		return rates.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed()) 
                .limit(amount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
	}

	@Override
	public double convert(String codeFrom, String codeTo, int amountSum) {
		 Double rateFrom = rates.get(codeFrom);
	     Double rateTo = rates.get(codeTo);
	        if (rateFrom == null || rateTo == null) {
	            throw new IllegalArgumentException("Invalid currency code.");
	        }
	        return amountSum * rateTo / rateFrom;
	}

	@Override
	public HashSet<String> getAllCodes() {
		return new HashSet<>(rates.keySet());
	}

}
