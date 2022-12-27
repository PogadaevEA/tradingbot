package org.testtask.auction.exception;

/**
 * Thrown to indicate that the bidder validation failed.
 */
public class BidderValidationException extends RuntimeException {

    public BidderValidationException(String message) {
        super(message);
    }
}
