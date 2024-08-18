package telran.currency;
import java.util.ArrayList;
import java.util.List;

import telran.currency.service.CurrencyConvertor;

import telran.view.InputOutput;
import telran.view.Item;

public class CurrencyItems {
private static final int MIN_VALUE = 1;
private static final int MAX_VALUE = 85;
private static final double MIN = 0;
private static final double MAX = 9000000000d;
private static CurrencyConvertor currencyConvertor;
public static List<Item> getItems(CurrencyConvertor currencyConvertor) {
	CurrencyItems.currencyConvertor = currencyConvertor;
	Item[] items = {
			Item.of("Today's strongest currencies", CurrencyItems::strongestCurrency),
			Item.of("Today's weakest currencies", CurrencyItems::weekestCurrency),
			Item.of("Convert currency", CurrencyItems::convert)
	};
	return new ArrayList<>(List.of(items));
}
static void strongestCurrency(InputOutput io) {
	 int amount = readAmount(io);
     List<String> strongest = currencyConvertor.strongestCurrencies(amount);
     io.writeLine("Strongest currencies:");
     strongest.forEach(currency -> io.writeLine(currency));
}

static void weekestCurrency(InputOutput io) {
	  int amount = readAmount(io);
      List<String> weakest = currencyConvertor.weakestCurrencies(amount);
      io.writeLine("Weakest currencies:");
      weakest.forEach(currency -> io.writeLine(currency));
}


private static int readAmount(InputOutput io) {
	return io.readNumberRange("Enter amount value", "Wrong amount", MIN_VALUE, MAX_VALUE).intValue();
	
}

static void convert(InputOutput io) {
	 ConvertDetails details = readConvertDetails(io);
     if (details != null) {
    	 double rate = currencyConvertor.convert(details.codeFrom, details.codeTo, details.amountSum);
         io.writeLine(details.amountSum + " " + details.codeFrom + " = " + rate + " " + details.codeTo);}
     else { io.writeLine("Invalid conversion details.");
        
     }
     
}
private static ConvertDetails readConvertDetails(InputOutput io) {
    String codeFrom = io.readString("Enter code of the currency you want to convert from");
    String codeTo = io.readString("Enter code of the currency you want to convert to");
    int amountSum = io.readNumberRange("Enter amount value", "Wrong amount", MIN, MAX).intValue();
    return new ConvertDetails(codeFrom, codeTo, amountSum);
}

private static class ConvertDetails {
    String codeFrom;
    String codeTo;
    int amountSum;

    ConvertDetails(String codeFrom, String codeTo, int amountSum) {
        this.codeFrom = codeFrom;
        this.codeTo = codeTo;
        this.amountSum = amountSum;
    }
}

}
