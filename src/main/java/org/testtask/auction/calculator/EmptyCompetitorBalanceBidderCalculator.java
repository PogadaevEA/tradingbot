package org.testtask.auction.calculator;

import org.testtask.auction.BidderContext;

/**
 * The strategy is suitable if an opponent already reached the cash limit.
 * It always places one monetary unit, which is enough to win the round.
 */
public class EmptyCompetitorBalanceBidderCalculator implements BidderCalculator {

    @Override
    public int placeBid(BidderContext context) {
        return 1;
    }

    @Override
    public boolean matches(BidderContext context) {
        return isEmptyCompetitorBalance(context);
    }

    @Override
    public StrategyType getType() {
        return StrategyType.EMPTY_COMPETITOR_BALANCE;
    }

    private boolean isEmptyCompetitorBalance(BidderContext context) {
        return context.getCompetitorCashBalance() == 0;
    }
}
