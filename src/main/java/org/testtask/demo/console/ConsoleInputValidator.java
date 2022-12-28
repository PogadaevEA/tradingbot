package org.testtask.demo.console;

/**
 * Validate console input
 */
public class ConsoleInputValidator {

    public boolean isValidAuctionQuantity(int quantity) {
        if (quantity < 2 || quantity % 2 != 0) {
            System.out.println("QU must be an even positive integer number (e.g. 2, 4, 6 ...). Please, enter again:");
            return false;
        }

        return true;
    }

    public boolean isValidCashLimit(int cash) {
        if (cash < 0) {
            System.out.println("MU must be a positive integer number (e.g. 1, 2, 3 ...). Please, enter again:");
            return false;
        }

        return true;
    }
}
