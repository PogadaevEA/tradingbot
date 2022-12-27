package org.testtask.auction.calculator;

import org.testtask.auction.model.BidderContext;

/**
 * The strategy is suitable if own cash limit is already reached.
 * It always places zero monetary unit.
 */
public class EmptyOwnBalanceBidderCalculator implements BidderCalculator {

    @Override
    public int placeBid(BidderContext context) {
        return 0;
    }

    @Override
    public boolean matches(BidderContext context) {
        return isEmptyOwnBalance(context);
    }

    @Override
    public StrategyType getType() {
        return StrategyType.EMPTY_OWN_BALANCE;
    }

    private boolean isEmptyOwnBalance(BidderContext context) {
        return context.getOwnCashBalance() == 0;
    }
}
