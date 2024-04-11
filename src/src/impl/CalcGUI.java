package src.impl;

import src.api.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CalcGUI implements ActionListener {
    private final JTextField fromCurrencyValue, changeExchangeRateTextField
            , addCurrencyNameField, addCurrencyValueField;
    private final JLabel toCurrencyValue;
    private final JComboBox<String> fromCurrencyName, toCurrencyName, changeExchangeRateBox;
    private final JComboBox<String> chooseCalculatorImpl;
    private final JMenuBar menuBar;
    private final JButton addCurrency;

    private final String ASENUM = "as enum";
    private final String ASINTERFACE = "as interface";
    private CalcInt calc = new CalcWithInt();
    private Currency fromCurrency;
    private Currency toCurrency;
    private List<String> currencyNames;
    private String[] currencyNamesExport;
    private List<Currency> currencies;
    private List<Currency> currencyCarry;


    public CalcGUI() {
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(700, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Default currencies
        currencies = new ArrayList<>();
        Currency euro = new Currency("EUR", 1);
        currencies.add(euro);
        Currency usd = new Currency("USD", 1.09);
        currencies.add(usd);
        Currency czk = new Currency("CZK", 25);
        currencies.add(czk);
        // Calc as Interface
        calc.setCurrencies(currencies);

        frame.setVisible(true);
        frame.setTitle("Currency Calculator");

        JPanel toolsPanel = new JPanel();
        toolsPanel.setPreferredSize(new Dimension(frame.getWidth(), 50));
        JLabel implementationInfo = new JLabel("used implementation of Calc: ");
        toolsPanel.add(implementationInfo);
        chooseCalculatorImpl = new JComboBox<>();
        chooseCalculatorImpl.addItem(ASINTERFACE);
        chooseCalculatorImpl.addItem(ASENUM);
        chooseCalculatorImpl.addActionListener(this);
        toolsPanel.add(chooseCalculatorImpl);
        toolsPanel.setVisible(true);
        frame.add(toolsPanel, BorderLayout.SOUTH);

        JPanel defaultScreen = new JPanel();
        defaultScreen.setBorder(BorderFactory.createEmptyBorder(40, 70, 10, 70));

        // default display currencies
        fromCurrency = euro;
        toCurrency = euro;

        currencyNames = new ArrayList<>();
        for (Currency currency : currencies){
            currencyNames.add(currency.getName());
        }
        currencyNamesExport = currencyNames.toArray(new String[0]);

        menuBar = new JMenuBar();
        JPanel exchangeRateItem = new JPanel();
        JLabel changeExchangeRatLabel = new JLabel("Change value to 1 euro of:");
        changeExchangeRateBox = new JComboBox<>(currencyNamesExport);
        changeExchangeRateTextField = new JTextField();
        changeExchangeRateTextField.setPreferredSize(new Dimension(70, 20));
        changeExchangeRateTextField.addActionListener(this);
        exchangeRateItem.add(changeExchangeRatLabel);
        exchangeRateItem.add(changeExchangeRateBox);
        exchangeRateItem.add(changeExchangeRateTextField);
        menuBar.add(exchangeRateItem);

        JPanel newCurrencyItem = new JPanel();
        addCurrency = new JButton("Add new currency");
        addCurrency.addActionListener(this);
        newCurrencyItem.add(addCurrency);

        JLabel addCurrencyName = new JLabel("Name: ");
        addCurrencyNameField = new JTextField();
        addCurrencyNameField.setPreferredSize(new Dimension(45, 20));
        newCurrencyItem.add(addCurrencyName);
        newCurrencyItem.add(addCurrencyNameField);

        JLabel addCurrencyValue = new JLabel("Value to 1 euro: ");
        addCurrencyValueField = new JTextField();
        addCurrencyValueField.setPreferredSize(new Dimension(30, 20));
        newCurrencyItem.add(addCurrencyValue);
        newCurrencyItem.add(addCurrencyValueField);

        JLabel recentlyAddedCurrency = new JLabel();
        newCurrencyItem.add(recentlyAddedCurrency);

        menuBar.add(newCurrencyItem);

        fromCurrencyName = new JComboBox<>(currencyNamesExport);
        fromCurrencyName.addActionListener(this);

        toCurrencyName = new JComboBox<>(currencyNamesExport);
        toCurrencyName.addActionListener(this);

        fromCurrencyValue = new JTextField();
        fromCurrencyValue.setPreferredSize(new Dimension(60, 20));
        fromCurrencyValue.addActionListener(this);
        toCurrencyValue = new JLabel("---");

        defaultScreen.add(fromCurrencyName, BorderLayout.NORTH);
        defaultScreen.add(fromCurrencyValue, BorderLayout.NORTH);
        defaultScreen.add(toCurrencyName, BorderLayout.SOUTH);
        defaultScreen.add(toCurrencyValue, BorderLayout.SOUTH);
        frame.add(defaultScreen, BorderLayout.CENTER);
        frame.add(menuBar, BorderLayout.NORTH);
        frame.setVisible(true);
        frame.setTitle("Currency Calculator");
        frame.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fromCurrencyValue) {
            convert();
        }
        // TODO: code similar, maybe with one call ?
        // change fromCurrencyName and toCurrencyName to one
        // this should be faster tho
        if (e.getSource() == fromCurrencyName){
            String selectedFromCurrency = (String) fromCurrencyName.getSelectedItem();
            fromCurrency = currencies
                    .stream()
                    .filter(currency -> currency.getName().equals(selectedFromCurrency))
                    .findFirst()
                    .get();
            convert();
        }
        if (e.getSource() == toCurrencyName){
            String selectedToCurrency = (String) toCurrencyName.getSelectedItem();
            toCurrency = currencies
                    .stream()
                    .filter(currency -> currency.getName().equals(selectedToCurrency))
                    .findFirst()
                    .get();
            convert();
        }
        if (e.getSource() == changeExchangeRateTextField) {
            String selectedExchangeRateChangeString = (String) changeExchangeRateBox.getSelectedItem();
            for (Currency currency : currencies) {
                assert selectedExchangeRateChangeString != null;
                if (selectedExchangeRateChangeString.equals(currency.getName()))
                    currency.setValueToEuro(Double.parseDouble(changeExchangeRateTextField.getText()));
            }
            convert();
        }
        if (e.getSource() == addCurrency) {
            if (! addCurrencyNameField.getText().isEmpty()
                    && ! addCurrencyValueField.getText().isEmpty()
                    && Double.parseDouble(addCurrencyValueField.getText()) != 0) {

                Currency newCurrency = new Currency(
                        addCurrencyNameField.getText().toUpperCase(),
                        Double.parseDouble(addCurrencyValueField.getText())
                );
                calc.addCurrency(newCurrency);
                // Update currency arrays and combo boxes
                updateCurrenciesAndComboBoxes();
            }
        }
        if (e.getSource() == chooseCalculatorImpl){
            toCurrencyValue.setText("---");
            String chosenImpl = (String) chooseCalculatorImpl.getSelectedItem();
            assert chosenImpl != null;
            // decided which calc to use and changes the GUI for the user
            // both come from the interface CalcInt
            if (chosenImpl.equals(ASINTERFACE)) {
                calc = new CalcWithInt();
                // uses the original currencies + the ones that already got created in the interface earlier
                calc.setCurrencies(currencyCarry);
                menuBar.setVisible(true);
                updateCurrenciesAndComboBoxes();
            }
            // as enum
            if (chosenImpl.equals(ASENUM)) {
               // to Carry the values over in case new ones got created
               currencyCarry = calc.getCurrencies();
               calc = new CalcWithEnum();
               menuBar.setVisible(false);
               updateCurrenciesAndComboBoxes();
            }
        }
    }

    // calls the exchange methode through the CalcInt interface
    private void convert() {
        // only activates if value is given, so no error occurs
        if (!fromCurrencyValue.getText().isEmpty()){
            double toExchangeCurrencyConverted =
                    calc.exchange(fromCurrency
                    , toCurrency
                    , Double.parseDouble(fromCurrencyValue.getText())
            );
            toCurrencyValue.setText(Double.toString(toExchangeCurrencyConverted));
        }
    }
    // sets the values in the combo boxes, that display the currencies
    // also uses the CalcInt interface
    private void updateCurrenciesAndComboBoxes() {
        List<Currency> updatedCurrencies = calc.getCurrencies();
        currencyNames = new ArrayList<> ();
        // comboBoxes need an array of Strings to work
        // to not change currencyNames to Array 3 times, we have the currencyNamesExport variable
        currencyNamesExport = updatedCurrencies.stream().map(Currency::getName).toArray(String[]::new);
        currencies = updatedCurrencies;

        fromCurrencyName.setModel(new DefaultComboBoxModel<>(currencyNamesExport));
        toCurrencyName.setModel(new DefaultComboBoxModel<>(currencyNamesExport));
        changeExchangeRateBox.setModel(new DefaultComboBoxModel<>(currencyNamesExport));
    }
}