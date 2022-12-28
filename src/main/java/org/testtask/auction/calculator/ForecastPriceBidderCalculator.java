package org.testtask.auction.calculator;

import org.testtask.auction.model.BidderContext;

import java.util.Collections;
import java.util.List;

/**
 * The strategy is suitable when some historical data are existing to make some forecast about next bid.
 * It uses historical data to analyze previous bids, own cash profit in any, number of remaining rounds and bit THRESHOLD for each round.
 */
public class ForecastPriceBidderCalculator implements BidderCalculator {

    private static final int ROUND_THRESHOLD = 4;

    @Override
    public int placeBid(BidderContext context) {
        int medianBid  = getMedianOfHistoricalWinnerBids(context);
        int roundThreshold = getRoundThreshold(context);

        int nextBid = Math.min(roundThreshold, medianBid);
        int nextBidWithRoundProfit = nextBid + getRoundsProfit(context);

        return Math.min(context.getOwnCashBalance(), nextBidWithRoundProfit);
    }

    @Override
    public boolean matches(BidderContext context) {
        return hasHistoricalDataAndNotEmptyQuantityBalance(context);
    }

    @Override
    public StrategyType getType() {
        return StrategyType.FORECAST_PRICE;
    }

    private int getMedianOfHistoricalWinnerBids(BidderContext context) {
        List<Integer> winnerBids = context.getBidsHistory().getWinnerBids();
        Collections.sort(winnerBids);

        int middle = winnerBids.size()/2;
        if (winnerBids.size() % 2 == 1) {
            return winnerBids.get(middle);
        } else {
            return (winnerBids.get(middle - 1) + winnerBids.get(middle)) / 2;
        }
    }

    private int getRoundsProfit(BidderContext context) {
        if (context.getOwnCashBalance() > context.getCompetitorCashBalance()) {
            return (context.getOwnCashBalance() - context.getCompetitorCashBalance()) / (context.getRemainingAuctionRounds() + 1);
        }

        return 0;
    }

    private int getRoundThreshold(BidderContext context) {
        return context.getEstimatedProductCost() * 2 * ROUND_THRESHOLD;
    }

    private boolean hasHistoricalDataAndNotEmptyQuantityBalance(BidderContext context) {
        return !context.getBidsHistory().isEmpty() && noneCompetitorsHasEmptyCashBalance(context);
    }

    private boolean noneCompetitorsHasEmptyCashBalance(BidderContext context) {
        return context.getOwnCashBalance() > 0 && context.getCompetitorCashBalance() > 0;
    }
}
