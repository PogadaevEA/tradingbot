package org.testtask.auction.service;

import org.testtask.auction.exception.BidderValidationException;

/**
 * Validate bidder on initialization or during the auction.
 */
public class BidderValidationService {

    public void validateOnInit(int quantity, int cash, boolean initialized) {
        validateAlreadyInitialized(initialized);
        validateAuctionQuantity(quantity);
        validateCashLimit(cash);
    }

    private void validateAlreadyInitialized(boolean initialized) {
        if (initialized) {
            throw new BidderValidationException("The reinitialization of the same bidder is prohibited.");
        }
    }

    private void validateAuctionQuantity(int quantity) {
        if (quantity < 2 || quantity % 2 != 0) {
            throw new BidderValidationException(
                    "Attempt to initiate bidder, but the quantity of the product does not comply with the auction rules. "
                    + "It must be an even positive number [2, 4, 6 ...]."
            );
        }
    }

    private void validateCashLimit(int cash) {
        if (cash < 0) {
            throw new BidderValidationException("Attempt to initiate bidder, but cash limit is negative.");
        }
    }

    public void validateOnRoundExecution(boolean initialized) {
        if (!initialized) {
            throw new BidderValidationException("A bidder has to be initiated before the start of auction.");
        }
    }
}
