package org.testtask.auction.calculator;

import org.testtask.auction.model.BidderContext;

/**
 * Default strategy, if a suitable strategy could not be found based on {@link BidderContext}.
 * It places either remaining cash limit or estimated product price, depending on what is left less.
 */
public class DefaultBidderCalculator implements BidderCalculator {

    @Override
    public int placeBid(BidderContext context) {
        return Math.min(context.getOwnCashBalance(), getEstimatedProductCostForRound(context));
    }

    @Override
    public boolean matches(BidderContext context) {
        return false;
    }

    @Override
    public StrategyType getType() {
        return StrategyType.DEFAULT;
    }

    private int getEstimatedProductCostForRound(BidderContext context) {
        return context.getEstimatedProductCost() * 2;
    }
}
