package src.impl;

/**
 * stores the currencies for CalcWithEnum
 */
public enum CurrencyEnum{
    /**
     * Euro (is the standard for all other currencies here, so valueToEuro is 1)
     */
    EURO ("EUR",1),
    /**
     * US-Dollar
     */
    DOLLAR ("USD", 1.09),
    /**
     * Czech Crowns
     */
    CZECH_CROWNS ("CZK", 25),
    /**
     * Danish Crowns
     */
    DANISH_CROWNS ("DKK", 7.46),
    /**
     * Australian Dollars
     */
    AUSTRALIAN_DOLLARS ("AUD", 1.65),
    /**
     * Russian Rubel
     */
    RUBEL ("RUB", 99.73),
    /**
     * Japanese Yen
     */
    YEN("JPY", 163.29);
    final Currency currency;

    /**
     * creates the Currency out of the enums
     * @param name name
     * @param valueToEuro equal value of the currency to 1 euro
     */
    CurrencyEnum(String name, double valueToEuro){
        this.currency = new Currency(name, valueToEuro);
    }
}