package src.impl;

import src.api.CalcInt;

import java.util.ArrayList;
import java.util.List;

public class CalcWithEnum implements CalcInt {

    @Override
    public double exchange(Currency fromCurrency, Currency toCurrency, double amount) {
        double result = (double) (Math.round(
                        (1 / fromCurrency.getValueToEuro()) * amount
                                * toCurrency.getValueToEuro() * 100)
                );
        return result / 100;
    }

    @Override
    public void addCurrency(Currency a) {
        System.err.println("no currency adding in enum.");
    }

    @Override
    public void setCurrencies(List<Currency> currencies) {
        System.err.println("no currency setting in enum.");
    }

    @Override
    public List<Currency> getCurrencies() {
        List<Currency> currencies = new ArrayList<>();
        for (CurrencyEnum value: CurrencyEnum.values()){
            currencies.add(value.currency);
        }
        return
                currencies;
    }
}


