package src.api;

import src.impl.Currency;
import src.impl.UCE;

import java.util.List;

/**
 * interface for the currency calculator
 */
public interface CalcInt {
    /**
     * calculates the currency exchange
     * @param from the currency the user wants to convert from
     * @param to the currency the user wants to convert to
     * @param value the amount of the fromCurrency
     * @return the amount of the currency the user wants to convert to that equal the value of the currency the user converts from
     */
    double exchange(Currency from, Currency to, double value);

    /**
     * allows the user to add a currency
     * @param a the currency a will get added to the usable currencies
     * @throws UCE UnknownCurrencyException
     */
    void addCurrency(Currency a) throws UCE;

    /**
     * setter for the currencies ArrayList
     * @param currencies sets the currencies and replaces the existing ones
     */
    void setCurrencies(List<Currency> currencies);

    /**
     * getter for the currencies ArrayList
     * @return Arraylist of usable currencies in the Calculator
     */
    List<Currency> getCurrencies();
}
