package org.testtask.auction.calculator;

import org.testtask.auction.model.BidderContext;

/**
 * The strategy is suitable when some historical data are existing to make some forecast about next bid.
 * It uses historical data to analyze previous bids and remaining own balance of cash.
 */
public class ForecastPriceBidderCalculator implements BidderCalculator {

    @Override
    public int placeBid(BidderContext context) {
        return 0;//todo
    }

    @Override
    public boolean matches(BidderContext context) {
        return false;//todo
    }

    @Override
    public StrategyType getType() {
        return StrategyType.ADVANTAGE_IN_QUANTITY;
    }
}
