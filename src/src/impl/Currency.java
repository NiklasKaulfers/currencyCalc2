package src.impl;

/**
 * defines each of the currencies
 */
public class Currency{
    String name;
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
     * @return returns how much of this currency is equal to 1 euro
     */
    double getValueToEuro(){
        return valueToEuro;
    }

    void setValueToEuro(double x){
        valueToEuro = x;
    }
}
