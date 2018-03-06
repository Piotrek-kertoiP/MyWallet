package pl.edu.agh.po.wallet.io;

import pl.edu.agh.po.wallet.Constants;
import pl.edu.agh.po.wallet.model.Currency;
import pl.edu.agh.po.wallet.model.Money;
import pl.edu.agh.po.wallet.rate.RateFinder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.APPEND;

//addExpense - działa
//addIncome - działa
//buyCurrency - działa
//sellCurrency - działa

public class WalletWriter {

    private final RateFinder rateFinder;

    public WalletWriter(RateFinder rateFinder){
        this.rateFinder = rateFinder;
    }

    public void addExpense(double amount, Currency currency, String category, String description, List<Money> moneyList) throws IOException {
        //dodaj notatkę o wydatku do pliku expenses.txt - działa
        LocalDate date = LocalDate.now();
        String newExpense = date + "\t" + amount + "\t" + currency + "\t" + category + "\t" + description + "\r\n";
        Files.write(Paths.get(Constants.EXPENSES_FILE_PATH), newExpense.getBytes(), APPEND);

        //zmniejsz w pliku currencies.txt ilość waluty o amount i zmień koszt waluty currency na getCostInPLNs * (getAmount - amount)/getAmount
        //zwiększ koszt waluty currency (costInPLNs) o amount * getCurrentRate(currency)

        for( Money element : moneyList){
            if( element.getCurrency() == currency ){
                element.setAmount( element.getAmount() - amount);
                if(element.getCurrency() == Currency.PLN){
                    element.setCostInPLNs( element.getAmount() );
                } else {
                    element.setCostInPLNs(element.getCostInPLNs() * (element.getAmount() - amount) / element.getAmount());
                }
            }

        }
        moneyListToFile(moneyList, Constants.CURRENCIES_FILE_PATH);

        //wyciągnij z pliku plns.txt ilość złotówek i zmniejsz ją o currency.getCurrentRate() * amount i zapisz do pliku plns.txt
        Scanner scanner = new Scanner( new File(Constants.PLNS_FILE_PATH) ).useDelimiter("\r\n");
        String plns = scanner.next();
        double amountOfPLNs = Double.parseDouble(plns) - rateFinder.getCurrentRate(currency) * amount;
        Files.write(Paths.get(Constants.PLNS_FILE_PATH), (amountOfPLNs + " ").getBytes() );
    }

    public void addIncome(double amount, Currency currency, String category, String description, List<Money> moneyList) throws IOException {
        addExpense( (-1)*amount, currency, category, description, moneyList);
    }

    public void buyCurrency(double amount, Currency currency, List<Money> moneyList) throws IOException {

        double currencyRate = rateFinder.getCurrentRate(currency);

        double costInPLNs = currencyRate * amount;

        double amountOfPLNs = 0.0;                  //liczy ile mam złotówek (nie licząc tych w walutach obcych)
        for(Money element : moneyList){
            if( element.getCurrency() == Currency.PLN){
                amountOfPLNs = element.getAmount();
                break;
            }
        }

        if( amountOfPLNs < costInPLNs ){
            System.out.println("Nie masz wystarczającej ilości złotówek, by kupić " + amount + " * " + currency.getDescription());
            return;
        }


        for(Money element : moneyList){
            if( element.getCurrency() == Currency.PLN){
                element.setAmount( element.getAmount() - costInPLNs);           //zmniejsza ilość złotówek o 'costInPLNs'
                element.setCostInPLNs( element.getAmount());                    //koszt zakupu X PLN = X PLN (zawsze)
            } else if( element.getCurrency() == currency){

                if(amount > 0 ) {                                                       //kupno
                    element.setCostInPLNs( costInPLNs + element.getCostInPLNs() );
                } else {                                                                //sprzedaż
                    element.setCostInPLNs( element.getCostInPLNs() * (element.getAmount() + amount) / element.getAmount() );
                }
                element.setAmount( element.getAmount() + amount );              //zwiększa ilość dolarów o 'amount'
            }
        }

        moneyListToFile(moneyList, Constants.CURRENCIES_FILE_PATH);

        if( amount > 0) {
            System.out.println("Kupiłeś " + amount + currency + " za " + costInPLNs + " PLN");
        } else {
            System.out.println("Sprzedałeś " + (-1)*amount + " " + currency + " za " + (-1)*costInPLNs + " PLN");
        }
    }

    public void sellCurrency(double amount, Currency currency, List<Money> moneyList) throws IOException {

        double avgCurrencyPrice = 0.0;
        for( Money element : moneyList ){
            if( element.getCurrency() == currency ){
                if( element.getAmount() < amount ){
                    System.out.println("Masz mniejszą ilość waluty, niż chcesz sprzedać");
                    return;
                }
                avgCurrencyPrice = element.getCostInPLNs() / element.getAmount();
            }
        }

        buyCurrency( (-1)*amount, currency, moneyList);

        double currentRate = rateFinder.getCurrentRate(currency);
        double delta = currentRate - avgCurrencyPrice;
        double profit = delta * amount;
        if(profit > 0) System.out.println("Na tej transakcji zyskałeś " + profit + " PLN");
        else System.out.println("Na tej transakcji straciłeś " + (-1)*profit + " PLN");
    }

    private void moneyListToFile(List<Money> moneyList, String currenciesFilePath) throws IOException {
        Files.write(Paths.get(currenciesFilePath), "".getBytes());              //opróżniam plik currencies.txt

        for(Money element : moneyList){
            String line = element.getAmount() + " " + element.getCurrency() + " " + element.getCostInPLNs() + "\r\n";
            Files.write(Paths.get(currenciesFilePath), line.getBytes(), APPEND);
        }
    }





}
