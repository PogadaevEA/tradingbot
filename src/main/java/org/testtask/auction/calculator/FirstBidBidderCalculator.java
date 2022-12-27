package org.testtask.auction.calculator;

import org.testtask.auction.BidderContext;

/**
 * The strategy is suitable if current round is first.
 * It always places zero monetary unit.
 */
public class FirstBidBidderCalculator implements BidderCalculator {

    @Override
    public int placeBid(BidderContext context) {
        return 0;
    }

    @Override
    public boolean matches(BidderContext context) {
        return isFirstBidRound(context);
    }

    @Override
    public StrategyType getType() {
        return StrategyType.FIRST_BID;
    }

    private boolean isFirstBidRound(BidderContext context) {
        return context.getBidsHistory().isEmptyBidsHistory();
    }
}
