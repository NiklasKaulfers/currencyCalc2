package src.impl;

import src.api.CalcInt;

import java.util.ArrayList;
import java.util.List;


/**
 * a class that implements the CalcInt interface
 * uses CurrencyEnum
 */
public class CalcWithEnum implements CalcInt {

    /**
     *
     * @param fromCurrency the currency to convert from
     * @param toCurrency the currency to convert to
     * @param amount the amount of the fromCurrency that will get converted
     * @return the equal amount of the toCurrency currencies to the fromCurrency currency
     */
    @Override
    public double exchange(Currency fromCurrency, Currency toCurrency, double amount) {
        double result = (double) (Math.round(
                        (1 / fromCurrency.getValueToEuro()) * amount
                                * toCurrency.getValueToEuro() * 100)
                );
        return result / 100;
    }

    /**
     * this function has no use and sends an error message when called
     */
    @Override
    public void addCurrency(Currency a) {
        System.err.println("no currency adding in enum.");
    }

    /**
     * this function has no use and sends an error message when called
     */
    @Override
    public void setCurrencies(List<Currency> currencies) {
        System.err.println("no currency setting in enum.");
    }

    /**
     * @return returns an ArrayList of the currencies usable in this Calculator
     */
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


