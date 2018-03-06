package pl.edu.agh.po.wallet.model;

public class Money {
    private Currency currency;
    private double amount;
    private double costInPLNs;

    public Money(Currency currency, double amount, double costInPLNs){
        this.currency = currency;
        this.amount = amount;
        this.costInPLNs = costInPLNs;
    }

    public double getAmount() {
        return this.amount;
    }

    public double getCostInPLNs(){
        return this.costInPLNs;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCostInPLNs(double valueInPLNs){
        this.costInPLNs = valueInPLNs;
    }

    public Currency getCurrency(){
        return this.currency;
    }
}
