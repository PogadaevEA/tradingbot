package org.testtask.demo.console;

/**
 * Validate console bidder on initialization or during the auction.
 */
public class ConsoleBidderValidator {

    public void validateOnInit(int quantity, int cash, boolean initialized) {
        validateAlreadyInitialized(initialized);
        validateAuctionQuantity(quantity);
        validateCashLimit(cash);
    }

    private void validateAlreadyInitialized(boolean initialized) {
        if (initialized) {
            System.out.println("The reinitialization of the same bidder is prohibited.");
        }
    }

    private void validateAuctionQuantity(int quantity) {
        if (quantity < 2 || quantity % 2 != 0) {
            System.out.println(
                    "Attempt to initiate bidder, but the quantity of the product does not comply with the auction rules."
                    + "It must be an even positive number [2, 4, 6 ...]."
            );
        }
    }

    private void validateCashLimit(int cash) {
        if (cash < 0) {
            System.out.println("Attempt to initiate bidder, but cash limit is negative.");
        }
    }

    public void validateOnRoundExecution(boolean initialized) {
        if (!initialized) {
            System.out.println("A bidder has to be initiated before the start of auction.");
        }
    }

    public boolean isValidOnPlaceBid(int userMonetaryUnits, int userBid) {
        if (userBid < 0) {
            System.out.println("A bid should not be negative. Please enter again:");
            return false;
        }
        if (userBid > userMonetaryUnits) {
            System.out.println("A bid should be less or equal your remaining cash limit or ZERO. Please enter again:");
            return false;
        }

        return true;
    }
}
