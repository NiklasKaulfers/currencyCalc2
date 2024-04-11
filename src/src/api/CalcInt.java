package src.api;

import src.impl.Currency;

import java.util.List;

public interface CalcInt {
    double exchange(Currency from, Currency to, double value);
    void addCurrency(Currency a);
    void setCurrencies(List<Currency> currencies);
    List<Currency> getCurrencies();
}
