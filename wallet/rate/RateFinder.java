package pl.edu.agh.po.wallet.rate;

import pl.edu.agh.po.wallet.model.Currency;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

// TB: strings to constants
// TB: delimiter for scanner
public class RateFinder {

    public double getCurrentRate(Currency currency) throws IOException {

        if( currency == Currency.PLN ) return 1.0;

        InputStream inputstream = new URL( "http://api.nbp.pl/api/exchangerates/rates/A/"+currency+"/?format=xml" ).openStream();
        Scanner scanner = new Scanner(inputstream).useDelimiter("\n");

        String currencyRate = scanner.next();

        return getCurrentRateFromXmlString(currency, currencyRate);
    }

    double getCurrentRateFromXmlString(Currency currency, String currencyRate) {
        int from = currencyRate.indexOf("<Mid>");
        int to = currencyRate.indexOf("</Mid>");

        currencyRate = currencyRate.substring(from+"<Mid>".length(), to);

        double rate = Double.parseDouble(currencyRate);
        return rate/currency.getDivisionFactor();
    }
}
