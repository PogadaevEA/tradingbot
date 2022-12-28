package org.testtask.demo.console;

import org.testtask.auction.Bidder;

import java.util.InputMismatchException;
import java.util.Scanner;

import static org.testtask.demo.console.ConsoleExecutor.PRODUCTS_TO_DRAW_ROUND;
import static org.testtask.demo.console.ConsoleExecutor.PRODUCTS_TO_ROUND_WINNER;

/**
 * Console bidder is used only in console application to test programmed bidder, and it cannot be used in a real business case.
 * Places the competitor bid based on console input.
 */
class ConsoleBidder implements Bidder {

    private final ConsoleBidderValidator consoleBidderValidator;
    private final Scanner scanner;

    private boolean initialized;
    private int monetaryUnits;
    private int quantityUnits;
    private int wonQuantity;

    public ConsoleBidder(Scanner scanner) {
        this.consoleBidderValidator = new ConsoleBidderValidator();
        this.scanner = scanner;
        this.initialized = false;
        this.wonQuantity = 0;
    }

    @Override
    public void init(int quantity, int cash) {
        consoleBidderValidator.validateOnInit(quantity, cash, initialized);
        this.quantityUnits = quantity;
        this.monetaryUnits = cash;
        this.initialized = true;
    }

    @Override
    public int placeBid() {
        consoleBidderValidator.validateOnRoundExecution(initialized);

        return getConsoleInputBid(scanner);
    }

    @Override
    public void bids(int own, int other) {
        consoleBidderValidator.validateOnRoundExecution(initialized);

        if (own > other) {
            System.out.println("Congrats! You won this round.");
            wonQuantity += PRODUCTS_TO_ROUND_WINNER;
        } else if (own == other) {
            System.out.println("This round is a draw.");
            wonQuantity += PRODUCTS_TO_DRAW_ROUND;
        } else {
            System.out.println("Sorry, you lost in this round.");
        }

        monetaryUnits -= own;
        printUnitsAmount();
    }

    private int getConsoleInputBid(Scanner scanner) {
        System.out.println("Enter your next bid:");
        int consoleInputBid = 0;
        boolean valid = false;

        while (!valid) {
            try {
                consoleInputBid = scanner.nextInt();
                valid = consoleBidderValidator.isValidOnPlaceBid(monetaryUnits, consoleInputBid);
            } catch (InputMismatchException | IllegalStateException exception) {
                System.out.println("A bid must be a non negative integer number (e.g. 0, 1, 2, 3 ...). Please, reenter next bid:");
                scanner.nextLine();
            }
        }

        return consoleInputBid;
    }

    private void printUnitsAmount() {
        System.out.println("You have %s monetary units left. Your current winnings are %s quantity units."
                .formatted(monetaryUnits, wonQuantity));
    }

    public int getMonetaryUnits() {
        return monetaryUnits;
    }

    public int getQuantityUnits() {
        return quantityUnits;
    }

    public int getWonQuantity() {
        return wonQuantity;
    }
}
