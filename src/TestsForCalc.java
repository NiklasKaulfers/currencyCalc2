import src.api.CalcInt;
import src.impl.*;

import java.util.ArrayList;

public class TestsForCalc {
    static int fails = 0;
    public static void main(String[] args) {
        System.out.println("tests for calcWithInt: ");
        testsForCalcWithInt();

        System.out.println("tests for calcWithEnum: ");
        testsForCalcWithEnum();

        System.out.println("errors in tests: " + fails);
    }
    static void testsForCalcWithEnum(){
        CalcInt calc = new CalcWithEnum();
        ArrayList<Currency> currencies = (ArrayList<Currency>) calc.getCurrencies();
        // euro, euro as euro is 1st enum
        double test1 = calc.exchange(currencies.getFirst(), currencies.getFirst(), 100000);
        equalsThis(100000, test1, "test1");
    }

    static void testsForCalcWithInt(){
        CalcInt calc = new CalcWithInt();
        ArrayList<Currency> currencies = new ArrayList<>();
        Currency euro = new Currency("EUR",1) ;
        currencies.add(euro);
        Currency two = new Currency("TWO",2);
        currencies.add(two);
        Currency three = new Currency("THREE",3);
        currencies.add(three);
        Currency aFourth = new Currency("FOURTH",0.25);
        currencies.add(aFourth);
        Currency dollar = new Currency("USD", 1.2);
        currencies.add(dollar);
        Currency yen = new Currency("JPY", 100);
        currencies.add(yen);
        calc.setCurrencies(currencies);

        double test1 = calc.exchange(euro,two,10);
        equalsThis(20, test1, "test1");
        double test2 = calc.exchange(two,aFourth,1);
        equalsThis(0.13, test2, "test2");
        double test3 = calc.exchange(euro,euro,999999999);
        equalsThis(999999999, test3, "test3");
        // euro to dollar
        double test4 = calc.exchange(euro, dollar, 100);
        equalsThis(120, test4, "test4");

        // dollar to yen
        double test5 = calc.exchange(dollar, yen, 50);
        equalsThis(4166.67, test5, "test5");

        // yen to euro
        double test6 = calc.exchange(yen, euro, 5000);
        equalsThis(50, test6, "test6");
    }

    static void equalsThis(double expected, double result, String testCase) {
        if (expected != result) {
            System.out.println("false: " + testCase);
            fails++;
        }
    }
}
