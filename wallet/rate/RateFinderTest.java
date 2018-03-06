package pl.edu.agh.po.wallet.rate;

import org.junit.Test;
import pl.edu.agh.po.wallet.model.Currency;

import java.io.IOException;

import static org.junit.Assert.*;

public class RateFinderTest {

    private RateFinder rateFinder = new RateFinder();

    @Test
    public void shouldReturnCorrectRateForHUF() {
        String hufXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ExchangeRatesSeries xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Table>A</Table><Currency>forint (Węgry)</Currency><Code>HUF</Code><Rates><Rate><No>014/A/NBP/2018</No><EffectiveDate>2018-01-19</EffectiveDate><Mid>0.013498</Mid></Rate></Rates></ExchangeRatesSeries>";

        double currentHufRate = rateFinder.getCurrentRateFromXmlString(Currency.HUF, hufXml);

        assertEquals(0.00013498, currentHufRate, 0.0001);
    }


    @Test
    public void shouldReturnCorrectRateForUSD() {
        String usdXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ExchangeRatesSeries xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Table>A</Table><Currency>dolar amerykański</Currency><Code>USD</Code><Rates><Rate><No>014/A/NBP/2018</No><EffectiveDate>2018-01-19</EffectiveDate><Mid>3.3994</Mid></Rate></Rates></ExchangeRatesSeries>";

        double currentUSDRate = rateFinder.getCurrentRateFromXmlString(Currency.USD, usdXml);

        assertEquals(3.3994, currentUSDRate , 0.0001);
    }


    @Test
    public void shouldReturnCorrectRateForIDR() {
        String idrXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ExchangeRatesSeries xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><Table>A</Table><Currency>rupia indonezyjska</Currency><Code>IDR</Code><Rates><Rate><No>014/A/NBP/2018</No><EffectiveDate>2018-01-19</EffectiveDate><Mid>0.00025533</Mid></Rate></Rates></ExchangeRatesSeries>";

        double currentIDRRate = rateFinder.getCurrentRateFromXmlString(Currency.IDR, idrXml);

        assertEquals(0.000000025533, currentIDRRate, 0.00000001);
    }

    @Test
    public void shouldReturnCorrectRateForPLN() throws IOException {

        double currentPLNRate = rateFinder.getCurrentRate(Currency.PLN);

        assertEquals(1.0, currentPLNRate, 0);
    }
}