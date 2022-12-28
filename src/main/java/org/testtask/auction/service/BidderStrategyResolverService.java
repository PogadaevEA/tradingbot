package org.testtask.auction.service;

import org.testtask.auction.calculator.*;
import org.testtask.auction.model.BidderContext;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Executes resolving of suitable strategies during the auction. Since multiple strategies exists {@link StrategyType},
 * the decision is made on the basis of {@link BidderContext}. In case a suitable strategy is not found, the default strategy
 * is returned (see {@link DefaultBidderCalculator}).
 */
public class BidderStrategyResolverService {

    private static final Logger log = Logger.getLogger(BidderStrategyResolverService.class.getName());

    private final Set<BidderCalculator> bidderCalculators;

    public BidderStrategyResolverService() {
        bidderCalculators = Set.of(
                new EmptyOwnBalanceBidderCalculator(),
                new EmptyCompetitorBalanceBidderCalculator(),
                new AdvantageInQuantityBidderCalculator(),
                new ForecastPriceBidderCalculator()
        );
    }

    public BidderCalculator resolve(BidderContext context) {
        BidderCalculator calculator = bidderCalculators.stream()
                .filter(strategy -> strategy.matches(context))
                .findFirst()
                .orElse(new DefaultBidderCalculator());
        log.config("%s type will be used for getting next bid".formatted(calculator.getType()));

        return calculator;
    }
}
