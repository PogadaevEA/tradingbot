package org.testtask.auction.calculator;

import org.testtask.auction.model.BidderContext;

/**
 * The strategy is suitable if an opponent already reached the advantage in win quantity compared by opponent.
 * It always places zero monetary unit (MU), because it doesn't make a sense to spend MU, since we already reached the advantage.
 */
public class AdvantageInQuantityBidderCalculator implements BidderCalculator {

    @Override
    public int placeBid(BidderContext context) {
        return 0;
    }

    @Override
    public boolean matches(BidderContext context) {
        return hasAdvantageInQuantity(context);
    }

    @Override
    public StrategyType getType() {
        return StrategyType.ADVANTAGE_IN_QUANTITY;
    }

    private boolean hasAdvantageInQuantity(BidderContext context) {
        return context.getWonProductQuantity() >= (context.getTotalProductQuantity() / 2) + 1;
    }
}
