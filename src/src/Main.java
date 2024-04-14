package src;

import src.impl.*;

import java.io.IOException;

/**
 * creates an Object of CalcGUI
 */
class Main {
    public static void main(String[] args) {
        try {
            new CalcGUI();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
