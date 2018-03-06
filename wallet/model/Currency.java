package pl.edu.agh.po.wallet.model;

public enum Currency {
    //getAllDescriptions - do zrobienia
    //getDescription - działa

    PLN("Polski złoty"),
    THB("Bat tajlandzki"),
    USD("Dolar amerykański"),
    AUD("Dolar australijski"),
    HKD("Dolar Hongkongu"),
    CAD("Dolar kanadyjski"),
    NZD("Dolar nowozelandzki"),
    SGD("Dolar singapurski"),
    EUR("Euro"),
    HUF("Forint węgierski", 100),            //cena HUF jest podawana za 100 jedn.
    CHF("Frank szwajcarski"),
    GBP("Funt szterling - brytyjski"),
    UAH("Hrywna ukraińska"),
    JPY("Jen japoński", 100),                //cena JPY jest podawana za 100 jedn.
    CZK("Korona czeska"),
    DKK("Korona duńska"),
    ISK("Korona islandzka", 100),            //cena ISK jest podawana za 100 jedn.
    NOK("Korona norweska"),
    SEK("Korona szwedzka"),
    HRK("Kuna (Chorwacja)"),
    RON("Lej rumuński"),
    BGN("Lew (Bułgaria)"),
    TRY("Lira turecka"),
    ILS("Nowy izraelski szekel"),
    CLP("Peso chilijskie", 100),             //cena CLP jest podawana za 100 jedn.
    PHP("Peso filipińskie"),
    MXN("Peso meksykańskie"),
    ZAR("Rand (Republika Południowej Afryki)"),
    BRL("Real (Brazylia)"),
    MYR("Ringgit (Malezja)"),
    RUB("Rubel rosyjski"),
    IDR("Rupia indonezyjska", 10000),          //cena IDR jest podawana za 10000 jedn.
    INR("Rupia indyjska", 100),              //cena INR jest podawana za 100 jedn.
    KRW("Won południowokoreański", 100),     //cena KRW jest podawana za 100 jedn.
    CNY("Yuan renminbi (Chiny)");


    private final String description;
    private final int divisionFactor;

    Currency(String description) {
        this(description, 1);
    }

    Currency(String description, int divisionFactor) {
        this.description = description;
        this.divisionFactor = divisionFactor;
    }

    public int getDivisionFactor(){
        return this.divisionFactor;
    }

    public String getDescription() {
        return description;
    }

    public static void printAllDescriptions(){
        for( Currency currency : values()){
            System.out.println(currency + " - " + currency.getDescription() );
        }
    }
}
