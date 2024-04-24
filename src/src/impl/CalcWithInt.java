package src.impl;

import src.api.CalcInt;

import java.util.ArrayList;
import java.util.List;

/**
 * Calculator implementation of CalcInt
 */
public class CalcWithInt implements CalcInt {
    private List<Currency> currencies;

    /**
     * @param fromCurrency the currency to convert from
     * @param toCurrency the currency to convert to
     * @param amount the amount of the fromCurrency that will get converted
     * @return the equal amount of the toCurrency currencies to the fromCurrency currency
     */
    @Override
    public double exchange(Currency fromCurrency, Currency toCurrency, double amount)  {
        double result = Math.round((1/fromCurrency.getValueToEuro()) * amount * toCurrency.getValueToEuro()*100);
        result /= 100;
            return result;
    }

    /**
     * adds a new Currency. throws an unknown currency exception if the currency is an empty string
     * @param a the currency that will get added to the Calculator and will be usable afterward
     */
    @Override
    public void addCurrency(Currency a) throws UCE {
        String currencyName = a.getName();
        StringBuilder correctedName = new StringBuilder();
        for (int i = 0; i < currencyName.length() ; i++) {
            if (Character.isLetter(currencyName.charAt(i))) {
                correctedName.append(currencyName.charAt(i));
            }
        }
        if (correctedName.toString().isEmpty()){
            throw new UCE("no currency");
        }
        a.setName(correctedName.toString());
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

    /**
     * @param currencies an ArrayList of currencies that will replace the current currencies
     */
    @Override
    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    /**
     * @return returns an ArrayList of all usable currencies
     */
    @Override
    public List<Currency> getCurrencies(){
        return new ArrayList<>(currencies);
    }

}

