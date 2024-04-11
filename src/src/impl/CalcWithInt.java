package src.impl;

import src.api.CalcInt;

import java.util.ArrayList;
import java.util.List;

public class CalcWithInt implements CalcInt {
    private List<Currency> currencies;

    @Override
    public double exchange(Currency fromCurrency, Currency toCurrency, double amount)  {
        double result = Math.round((1/fromCurrency.getValueToEuro()) * amount * toCurrency.getValueToEuro()*100);
        result /= 100;
            return result;
    }

    @Override
    public void addCurrency(Currency a) {
        boolean exists =
                currencies
                        .stream()
                        .anyMatch(c -> c
                                .getName()
                                .equals(a
                                        .getName()));

        if (!exists){
            currencies.add(a);
        }
    }

    @Override
    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    @Override
    public List<Currency> getCurrencies(){
        return new ArrayList<>(currencies);
    }

}

