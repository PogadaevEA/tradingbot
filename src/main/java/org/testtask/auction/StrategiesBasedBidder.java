package org.testtask.auction;

import org.testtask.auction.calculator.StrategyType;
import org.testtask.auction.model.BidderContext;
import org.testtask.auction.service.BidderStrategyResolverService;
import org.testtask.auction.service.BidderValidationService;

/**
 * Bidder, which delegates the placing a bid based on certain strategy types {@link StrategyType}.
 */
public class StrategiesBasedBidder implements Bidder {

    private final BidderStrategyResolverService bidderStrategyResolverService;
    private final BidderValidationService bidderValidationService;
    private final BidderContext context = new BidderContext();

    public StrategiesBasedBidder() {
        this.bidderStrategyResolverService = new BidderStrategyResolverService();
        this.bidderValidationService = new BidderValidationService();
    }

    @Override
    public void init(int quantity, int cash) {
        bidderValidationService.validateOnInit(quantity, cash, context.isInitialized());

        context.init(quantity, cash);
    }

    @Override
    public int placeBid() {
        bidderValidationService.validateOnRoundExecution(context.isInitialized());

        return bidderStrategyResolverService.resolve(context).placeBid(context);
    }

    /**
     * TODO:
     * 1. Think about what would have global implications for any auctions that collect historical bid data and adapt when excited
     * 2. mb update the object that stores the percentage / number of successful bets and the score?
     * 3. what if call is in wrong order?
     */
    @Override
    public void bids(int own, int other) {
        bidderValidationService.validateOnRoundExecution(context.isInitialized());

        context.updateAfterBid(own, other);
    }
}
