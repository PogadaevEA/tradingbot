package org.testtask.auction.service;

import org.testtask.auction.BidderContext;
import org.testtask.auction.calculator.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Executes resolving of suitable strategies during the auction. Since multiple strategies exists {@link StrategyType},
 * the decision is made on the basis of {@link BidderContext}. In case a suitable strategy is not found, the default strategy
 * is returned (see {@link DefaultBidderCalculator}).
 */
public class BidderStrategyResolverService {

    private final Map<StrategyType, BidderCalculator> strategies = new HashMap<>();

    public BidderStrategyResolverService() {
        FirstBidBidderCalculator firstBidBidderStrategy = new FirstBidBidderCalculator();
        EmptyOwnBalanceBidderCalculator emptyOwnBalanceBidderStrategy = new EmptyOwnBalanceBidderCalculator();
        EmptyCompetitorBalanceBidderCalculator emptyCompetitorBalanceBidderStrategy = new EmptyCompetitorBalanceBidderCalculator();

        strategies.put(firstBidBidderStrategy.getType(), firstBidBidderStrategy);
        strategies.put(emptyOwnBalanceBidderStrategy.getType(), emptyOwnBalanceBidderStrategy);
        strategies.put(emptyCompetitorBalanceBidderStrategy.getType(), emptyCompetitorBalanceBidderStrategy);
    }

    public BidderCalculator resolve(BidderContext context) {
        return strategies.values().stream()
                .filter(strategy -> strategy.matches(context))
                .findFirst()
                .orElse(new DefaultBidderCalculator());
    }
}
