package src.impl;

public enum CurrencyEnum{
    EURO ("EUR",1),
    DOLLAR ("USD", 1.09),
    CZECH_CROWNS ("CZK", 25),
    DANISH_CROWNS ("DKK", 7.46),
    AUSTRALIAN_DOLLARS ("AUD", 1.65),
    RUBEL ("RUB", 99.73),
    YEN("JPY", 163.29);
    final Currency currency;

    CurrencyEnum(String name, double valueToEuro){
        this.currency = new Currency(name, valueToEuro);
    }
}