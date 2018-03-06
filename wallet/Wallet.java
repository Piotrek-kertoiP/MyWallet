package pl.edu.agh.po.wallet;

import pl.edu.agh.po.wallet.io.WalletReader;
import pl.edu.agh.po.wallet.io.WalletWriter;
import pl.edu.agh.po.wallet.model.Currency;
import pl.edu.agh.po.wallet.model.Money;
import pl.edu.agh.po.wallet.rate.RateFinder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Wallet {
    public static void main(String[] args) throws IOException {
        RateFinder rateFinder = new RateFinder();
        Scanner scanner = new Scanner(System.in);
        WalletReader walletReader = new WalletReader(rateFinder);
        WalletWriter walletWriter = new WalletWriter(rateFinder);

        System.out.println("Uruchamiam demo \n 1. Metoda obliczająca ilość PLN, uwzględniając waluty obce:");
        System.out.println("Masz aktualnie " + walletReader.howManyPLNs() + " PLN\n");

        System.out.println(" 2. Metoda służąca do zamiany waluty obcej na PLN:");
        walletWriter.sellCurrency(50, Currency.USD, getMoneyList(walletReader));

        System.out.println("\n 3. Metoda służąca do kupna waluty obcej za PLN:");
        walletWriter.buyCurrency(100, Currency.EUR, getMoneyList(walletReader));

        System.out.println("\n 4. Metoda służąca do dodawania notatki o wydatkach (nic nie wypisuje, zmiany w plikach)");
        walletWriter.addExpense(50, Currency.PLN, "jedzonko", "mniam mniam", getMoneyList(walletReader));

        System.out.println("\n 5. Metoda służąca do dodawania notatki o przypływie gotówki (nic nie wypisuje, zmiany w plikach)");
        walletWriter.addIncome(100, Currency.PLN, "praca", "się opłaca", getMoneyList(walletReader));

        System.out.println("\n 6. Metoda służąca do obliczania aktualnego kursu wybranej waluty wg notowań NBP");
        System.out.println("Aktualny kurs dolara to " + rateFinder.getCurrentRate(Currency.USD) + " PLN/USD");

        System.out.println("\n 7. Metoda służąca do zwracania opisu wybranej waluty");
        System.out.println("Opis waluty USD to " + Currency.USD.getDescription());

        System.out.println("\n 8. Metoda służąca do zwracania listy wydatków z podanej kategorii, w podanym przedziale czasowym");
        walletReader.getMyExpenses("jedzonko", "2018-01-01", "2018-01-30" );

        System.out.println("\n 9. Metoda służąca do wypisywania wszystkich opisów walut (gdyby ktoś np. chciał kupić rupię indonezyjską, ale nie znał jej kodu)");
        Currency.printAllDescriptions();
    }

    private static List<Money> getMoneyList(WalletReader walletReader) throws FileNotFoundException {
        return walletReader.getMoneyList(Constants.CURRENCIES_FILE_PATH);
    }
}
