package src.impl;

public class Currency{
    String name;
    double valueToEuro;
    public Currency(String name, double valueToEuro){
        this.name = name;
        this.valueToEuro = valueToEuro;
    }
    // getters
    String getName(){
        return name;
    }

    double getValueToEuro(){
        return valueToEuro;
    }

    void setValueToEuro(double x){
        valueToEuro = x;
    }
}
