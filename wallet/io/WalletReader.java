package pl.edu.agh.po.wallet.io;

import pl.edu.agh.po.wallet.Constants;
import pl.edu.agh.po.wallet.model.Currency;
import pl.edu.agh.po.wallet.model.Money;
import pl.edu.agh.po.wallet.rate.RateFinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

//howManyPLNs - działa
//getMoneyList - działa
//getMyExpenses - działa

public class WalletReader {

    private final RateFinder rateFinder;

    public WalletReader(RateFinder rateFinder){
        this.rateFinder = rateFinder;
    }

    public double howManyPLNs() throws IOException {
        List<Money> moneyList = getMoneyList(Constants.CURRENCIES_FILE_PATH);

        double sum = 0.0;
        for(Money element: moneyList) {
            if (element.getCurrency() == Currency.PLN) {
                sum += element.getAmount();
            }
            else {
                sum += element.getAmount() * rateFinder.getCurrentRate( element.getCurrency() );
            }
        }
        Files.write(Paths.get(Constants.PLNS_FILE_PATH), String.valueOf(sum).getBytes());
        return sum;
    }


    public List<Money> getMoneyList(String CURRENCIES_FILE_PATH) throws FileNotFoundException {

        Scanner scanner = new Scanner( new File(CURRENCIES_FILE_PATH) ).useDelimiter("\r\n");

        List<Money> moneyList = new ArrayList<>();
        while(scanner.hasNext()){
            String newLine = scanner.next();
            String[] array = newLine.split(" ");

            Currency currency = Currency.valueOf(array[1]);
            double amount = Double.parseDouble(array[0]);
            double costInPLNs = Double.parseDouble(array[2]);

            moneyList.add( new Money(currency, amount, costInPLNs));
        }

        return moneyList;
    }

    public void getMyExpenses(String category, String dateFrom, String dateTo) throws IOException {
        //wypisuje na ekran wszystkie wydatki z danej kategorii, z danego przedziału czasu + suma wydatków

        double sum = 0.0;
        Scanner scanner = new Scanner( new File(Constants.EXPENSES_FILE_PATH) ).useDelimiter("\n");
        while(scanner.hasNext()){
            String newLine = scanner.next();

            String[] array = newLine.split("\t");

            //array: ["2018-01-01", "200", "PLN", "jedzonko", "zakupy"]

            //rzutowanie na LocalDate, double, currency, string
            LocalDate from = LocalDate.parse(dateFrom);
            LocalDate to = LocalDate.parse(dateTo);
            LocalDate expenseDate = LocalDate.parse( array[0] );
            double amount = Double.parseDouble(array[1]);
            Currency currency = Currency.valueOf(array[2]);
            String cat = array[3];

            if( expenseDate.isAfter(from) && expenseDate.isBefore(to) && category.equals( cat ) ){
                sum += amount * rateFinder.getCurrentRate(currency);
                System.out.println(newLine);
            }
        }
        System.out.println("Suma wydatków wynosi " + sum + " PLN");
    }
}
