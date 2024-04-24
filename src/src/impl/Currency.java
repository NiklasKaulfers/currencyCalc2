package src.impl;

import java.io.Serializable;


/**
 * a class that defines a Currency for later use in the CurrencyCalc
 */
public class Currency implements Serializable {
    /**
     * the currencies name
     */
    String name;
    /**
     * the currencies value to 1 euro
     */
    double valueToEuro;

    /**
     * makes a Currency for the use in the Calculator
     * @param name name of the Currency
     * @param valueToEuro how much of the currency equals 1 euro
     */
    public Currency(String name, double valueToEuro){
        this.name = name;
        this.valueToEuro = valueToEuro;
    }

    /**
     * @return returns the name of the currency
     */
    // getters
    String getName(){
        return name;
    }

    /**
     * allows the user to change the name
     * @param name new name for the currency
     */
    void setName(String name){
        this.name = name;
    }

    /**
     * @return returns how much of this currency is equal to 1 euro
     */
    double getValueToEuro(){
        return valueToEuro;
    }

    void setValueToEuro(double x){
        valueToEuro = x;
    }
}
