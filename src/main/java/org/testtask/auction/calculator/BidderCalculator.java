package org.testtask.auction.calculator;

import org.testtask.auction.model.BidderContext;

/**
 * Represents a bidder calculator. The decision on the amount of the bet and
 * the appropriate strategy is made on the basis of {@link BidderContext}
 */
public interface BidderCalculator {

    int placeBid(BidderContext context);

    boolean matches(BidderContext context);

    StrategyType getType();
}
