package org.testtask.demo.console;

import org.testtask.auction.StrategiesBasedBidder;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Simple console application to try to compete with the programmed bidder.
 */
public class ConsoleExecutor {

    private static final String KEY_WORD_TO_START = "START";
    protected static final int PRODUCTS_TO_ROUND_WINNER = 2;
    protected static final int PRODUCTS_TO_DRAW_ROUND = 1;

    private final ConsoleInputValidator consoleInputValidator = new ConsoleInputValidator();

    public void runConsoleApplication() {
        printAuctionRules();
        Scanner scanner = new Scanner(System.in);
        String userCommand = getCommand(scanner);

        while (userCommand.equals(KEY_WORD_TO_START)) {
            runAuction(scanner);
            scanner.nextLine();
            userCommand = getCommand(scanner);
        }

        scanner.close();
    }

    private void printAuctionRules() {
        System.out.println("""
                This is a console application to try to compete with the programmed bidder.
                
                Auction rules:
                GENERAL INFO: QU = quantity units MU = monetary units
                
                EXPLANATION: A product x QU will be auctioned under 2 parties.
                The parties have each y MU for auction. They then offer an arbitrary number simultaneously of its MU on
                the first 2 QU of the product. After that, the bids will be visible to both.
                The 2 QU of the product is awarded to who has offered the most MU; if both bid the same, then both get 1
                QU. Both bidders must pay their amount - including the defeated. A bid of 0 MU is allowed. Bidding on each
                2 QU is repeated until the supply of x QU is fully auctioned.
                
                THESIS: Each bidder aims to get a larger amount than its competitor.
                In an auction, the program that is able to get more QU than the other wins. In case of a tie, the program that
                retains more MU wins.
                """);
    }

    private String getCommand(Scanner scanner) {
        System.out.println("To start the auction enter START, to exit enter something else:");
        String command = scanner.nextLine();
        while (command.isBlank()) {
            System.out.println("To start the auction enter START, to exit enter something else:");
            command = scanner.nextLine();
        }
        return command;
    }

    private void runAuction(Scanner scanner) {
        System.out.println("Please, initiate auction variables.");
        int remainingQuantityUnits = getQuantityUnits(scanner);
        int monetaryUnits = getMonetaryUnits(scanner);

        System.out.println(
                "Required auction variables have been initiated: QU = %s, MU = %s. Auction has started."
                        .formatted(remainingQuantityUnits, monetaryUnits)
        );

        ConsoleBidder consoleBidder = initiateConsoleBidder(remainingQuantityUnits, monetaryUnits, scanner);
        StrategiesBasedBidder bidder = initiateAppBidder(remainingQuantityUnits, monetaryUnits);

        int bidderRemainingCash = monetaryUnits;

        while (remainingQuantityUnits >= PRODUCTS_TO_ROUND_WINNER) {
            int userBid = consoleBidder.placeBid();
            int bidderBid = bidder.placeBid();

            consoleBidder.bids(userBid, bidderBid);
            bidder.bids(bidderBid, userBid);

            bidderRemainingCash -= bidderBid;
            remainingQuantityUnits -= 2;
        }

        determineAuctionWinner(consoleBidder, bidderRemainingCash);
    }

    private ConsoleBidder initiateConsoleBidder(int quantityUnits, int monetaryUnits, Scanner scanner) {
        ConsoleBidder consoleBidder = new ConsoleBidder(scanner);
        consoleBidder.init(quantityUnits, monetaryUnits);

        return consoleBidder;
    }

    private int getQuantityUnits(Scanner scanner) {
        System.out.println("Enter QU = an even positive integer number (e.g. 2, 4, 6 ...):");
        boolean valid = false;
        int quantityUnits = 0;

        while (!valid) {
            try {
                quantityUnits = scanner.nextInt();
                valid = consoleInputValidator.isValidAuctionQuantity(quantityUnits);
            } catch (InputMismatchException | IllegalStateException exception) {
                System.out.println("QU must be an even positive integer number (e.g. 2, 4, 6 ...). Please, enter again:");
                scanner.nextLine();
            }
        }

        return quantityUnits;
    }

    private int getMonetaryUnits(Scanner scanner) {
        System.out.println("Enter MU = a positive integer number (e.g. 1, 2, 3 ...):");
        boolean valid = false;
        int monetaryUnits = 0;

        while (!valid) {
            try {
                monetaryUnits = scanner.nextInt();
                valid = consoleInputValidator.isValidCashLimit(monetaryUnits);
            } catch (InputMismatchException | IllegalStateException exception) {
                System.out.println("MU must be a positive integer number (e.g. 1, 2, 3 ...). Please, enter again:");
                scanner.nextLine();
            }
        }

        return monetaryUnits;
    }

    private StrategiesBasedBidder initiateAppBidder(int quantityUnits, int monetaryUnits) {
        StrategiesBasedBidder bidder = new StrategiesBasedBidder();
        bidder.init(quantityUnits, monetaryUnits);

        return bidder;
    }

    private void determineAuctionWinner(ConsoleBidder consoleBidder, int bidderRemainingCash) {
        int userWonQuantity = consoleBidder.getWonQuantity();
        int userRemainingCash = consoleBidder.getMonetaryUnits();
        int opponentWonQuantity = consoleBidder.getQuantityUnits() - userWonQuantity;

        if (userWonQuantity > opponentWonQuantity) {
            System.out.println("Congrats! You won this auction.");
        } else if (userWonQuantity == opponentWonQuantity) {
            determinateAuctionWinnerBasedOnRemainingCash(userRemainingCash, bidderRemainingCash);
        } else {
            System.out.println("Sorry, you lost in this auction.");
        }
    }

    private void determinateAuctionWinnerBasedOnRemainingCash(int userRemainingCash, int opponentRemainingCash) {
        if (userRemainingCash > opponentRemainingCash) {
            System.out.println("Congrats! You won this auction.");
        } else if (userRemainingCash == opponentRemainingCash) {
            System.out.println("Can't determinate the winner, since both of you have the equal QU and MU. This action is a draw.");
        } else {
            System.out.println("Sorry, you lost in this auction.");
        }
    }
}
