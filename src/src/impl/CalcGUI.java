package src.impl;

import src.api.*;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * the GUI that allows user input and shows the output for the currency calculator
 */
public class CalcGUI  implements ActionListener {
    private final JTextField fromCurrencyValue, changeExchangeRateTextField, addCurrencyNameField, addCurrencyValueField;
    private final JLabel toCurrencyValue;
    private final JComboBox<String> fromCurrencyName, toCurrencyName, changeExchangeRateBox;
    private final JComboBox<String> chooseCalculatorImpl;
    private final JMenuBar menuBar;
    private final JButton addCurrency;

    private final String ASENUM = "as enum";
    private final String ASINTERFACE = "as interface";
    private final Currency euro = new Currency("EUR", 1);
    private CalcInt calc = new CalcWithInt();
    private Currency fromCurrency;
    private Currency toCurrency;
    private String[] currencyNamesExport;
    private List<Currency> currencies;
    private List<Currency> currencyCarry;
    private String chosenImpl = ASINTERFACE;


    /**
     * basic creating of the GUI
     */
    public CalcGUI() {
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(700, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Default currencies
        currencies = new ArrayList<>();

        intakeCurrencies();

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

        // for the comboBoxes as they need an array
        // gets the names of each Currency and puts them in an array
        currencyNamesExport = currencies.stream().map(Currency::getName).toArray(String[]::new);

        menuBar = new JMenuBar();
        JPanel exchangeRateItem = new JPanel();
        JLabel changeExchangeRatLabel = new JLabel("Change value to 1 euro of:");
        changeExchangeRateBox = new JComboBox<>(currencies
                .stream()
                .map(Currency::getName)
                .filter(c -> !c.equals(euro.getName()))
                .toArray(String[]::new)
        );
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

        // updates save data and the comboBoxes
        updateCurrencies();
        updateComboBoxes();

        frame.add(defaultScreen, BorderLayout.CENTER);
        frame.add(menuBar, BorderLayout.NORTH);
        frame.setVisible(true);
        frame.setTitle("Currency Calculator");
        frame.pack();
    }

    /**
     * handles the events if the user interacts with the GUI
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fromCurrencyValue) {
            convert();
        }
        if (e.getSource() == fromCurrencyName) {
            // it´s different from other code and sets the result easier
            String selectedFromCurrency = (String) fromCurrencyName.getSelectedItem();
            Optional<Currency> possibleCurrency = currencies
                    .stream()
                    .filter(currency -> currency.getName().equals(selectedFromCurrency))
                    .findFirst();
            if (possibleCurrency.isPresent()) {
                fromCurrency = possibleCurrency.get();
            } else {
                System.err.println("error getting currency: " + selectedFromCurrency);
                fromCurrency = currencies.getFirst();
            }
            convert();
        }

        if (e.getSource() == toCurrencyName) {
            String selectedCurrencyName = (String) toCurrencyName.getSelectedItem();
            Optional<Currency> optionalCurrency = currencies.stream()
                    .filter(currency -> currency.getName().equals(selectedCurrencyName))
                    .findFirst();

            if (optionalCurrency.isPresent()) {
                // If the currency is found, assign it to toCurrency
                toCurrency = optionalCurrency.get();
            } else {
                // If no currency is found, handle the error
                System.err.println("Currency not found: " + selectedCurrencyName);
                // Set toCurrency to a default currency (for example, the first currency in the list)
                toCurrency = currencies.get(1);
            }
            convert();
        }
        if (e.getSource() == changeExchangeRateTextField) {
            String selectedExchangeRateChangeString = (String) changeExchangeRateBox.getSelectedItem();
            for (Currency currency : currencies) {
                assert selectedExchangeRateChangeString != null;
                if (selectedExchangeRateChangeString.equals(currency.getName()))
                    currency.setValueToEuro(Double.parseDouble(changeExchangeRateTextField.getText()));
            }
            updateCurrencies();
            convert();
        }
        if (e.getSource() == addCurrency) {
            if (!addCurrencyNameField.getText().isEmpty()
                    && !addCurrencyValueField.getText().isEmpty()
                    && Double.parseDouble(addCurrencyValueField.getText()) != 0) {

                Currency newCurrency = new Currency(
                        addCurrencyNameField.getText().toUpperCase(),
                        Double.parseDouble(addCurrencyValueField.getText())
                );
                try {
                    calc.addCurrency(newCurrency);
                    updateCurrencies();
                    updateComboBoxes();
                    addCurrencyNameField.setText("");
                    addCurrencyValueField.setText("");
                } catch (UCE a) {
                    System.err.println(a.getMessage());
                }
            }
        }
        if (e.getSource() == chooseCalculatorImpl) {
            toCurrencyValue.setText("---");
            chosenImpl = (String) chooseCalculatorImpl.getSelectedItem();
            assert chosenImpl != null;
            // decided which calc to use and changes the GUI for the user
            // both come from the interface CalcInt
            if (chosenImpl.equals(ASINTERFACE)) {
                calc = new CalcWithInt();
                // uses the original currencies + the ones that already got created in the interface earlier
                calc.setCurrencies(currencyCarry);
                menuBar.setVisible(true);
                updateCurrencies();
                updateComboBoxes();
            }
            // as enum
            if (chosenImpl.equals(ASENUM)) {
                // to Carry the values over in case new ones got created
                currencyCarry = calc.getCurrencies();
                calc = new CalcWithEnum();
                menuBar.setVisible(false);
                updateCurrencies();
                updateComboBoxes();
            }
        }
    }

    /**
     * calls the exchange function of CalcInt
     */
    private void convert() {
        // only activates if value is given, so no error occurs
        if (!fromCurrencyValue.getText().isEmpty()) {
            double toExchangeCurrencyConverted =
                    calc.exchange(fromCurrency
                            , toCurrency
                            , Double.parseDouble(fromCurrencyValue.getText())
                    );
            toCurrencyValue.setText(Double.toString(toExchangeCurrencyConverted));
        }
    }

    /**
     * sets the values in the combo boxes, that display the currencies
     * also uses the CalcInt interface
     */

    private void updateComboBoxes() {
        // comboBoxes need an array of Strings to work
        // to not change currencyNames to Array 3 times, we have the currencyNamesExport variable
        currencyNamesExport = currencies.stream().map(Currency::getName).toArray(String[]::new);

        fromCurrencyName.setModel(new DefaultComboBoxModel<>(currencyNamesExport));
        toCurrencyName.setModel(new DefaultComboBoxModel<>(currencyNamesExport));
        // slightly big expression
        // makes currencies with no euro and to an array of Strings
        currencyNamesExport = currencies
                .stream()
                .map(Currency::getName)
                .filter(c -> !c.equals(euro.getName()))
                .toArray(String[]::new);
        changeExchangeRateBox.setModel(new DefaultComboBoxModel<>(currencyNamesExport));
    }

    /**
     * updates the currencies and saves them to the currencies.txt text-file
     */
    private void updateCurrencies() {
        currencies = calc.getCurrencies();
        outputCurrencies();
    }

    /**
     * handles the reading of the save files
     * (unchecked typecast warning -> cleared with check)
     */
    private void intakeCurrencies() {
        if (new File("currencies.dat").exists()) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("currencies.dat"))) {
                Object obj = inputStream.readObject();
                if (obj instanceof ArrayList<?>) {
                    currencies = new ArrayList<>();
                    for (Object o : (ArrayList<?>) obj) {
                        if (o instanceof Currency) {
                            currencies.add((Currency) o);
                        } else {
                            System.err.println("error getting currencies: safe file issues (making of currency)");
                            defaultCurrencies();
                            break;
                        }
                    }
                } else {
                    System.err.println("error in fileReader: safe file issues (currencies cant be read)");
                }
            } catch (IOException | ClassNotFoundException ioEx) {
                System.err.println("error in fileInputStream: " + ioEx.getMessage());
            }
        } else {
            defaultCurrencies();
        }
        calc.setCurrencies(currencies);
    }

    /**
     * handles the writing of the save files
     */
    private void outputCurrencies() {
        if (chosenImpl.equals(ASINTERFACE)) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("currencies.dat"))) {
                outputStream.writeObject(currencies);
            } catch (IOException ioEx) {
                System.err.println("error in fileOutputStream: " + ioEx.getMessage());
            }
        }
    }

    /**
     * creates the default currencies for 1st open or if an error loading the saved currencies occurs
     */
    private void defaultCurrencies(){
        currencies = new ArrayList<>();
        currencies.add(euro);
        Currency usd = new Currency("USD", 1.09);
        currencies.add(usd);
        Currency czk = new Currency("CZK", 25);
        currencies.add(czk);
    }
}