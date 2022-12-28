package org.testtask.auction.service;

import org.junit.jupiter.api.Test;
import org.testtask.auction.calculator.BidderCalculator;
import org.testtask.auction.calculator.StrategyType;
import org.testtask.auction.model.BidderContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BidderStrategyResolverServiceTest {

    @Test
    void should_return_DEFAULT_bidder_strategy_on_resolve_if_match_not_found() {
        // given
        BidderContext context = new BidderContext.BidderContextBuilder()
                .withOwnCashBalance(10)
                .withCompetitorCashBalance(10)
                .withTotalProductQuantity(10)
                .withEstimatedProductCost(1)
                .build();

        // when
        BidderCalculator result = new BidderStrategyResolverService().resolve(context);

        // then
        assertThat(result.getType()).isEqualTo(StrategyType.DEFAULT);
    }

    @Test
    void should_return_EMPTY_OWN_BALANCE_bidder_strategy_on_resolve_if_matches() {
        // given
        BidderContext context = new BidderContext.BidderContextBuilder()
                .withOwnCashBalance(0)
                .withCompetitorCashBalance(10)
                .withTotalProductQuantity(10)
                .withEstimatedProductCost(1)
                .build();

        // when
        BidderCalculator result = new BidderStrategyResolverService().resolve(context);

        // then
        assertThat(result.getType()).isEqualTo(StrategyType.EMPTY_OWN_BALANCE);
    }

    @Test
    void should_return_EMPTY_COMPETITOR_BALANCE_bidder_strategy_on_resolve_if_matches() {
        // given
        BidderContext context = new BidderContext.BidderContextBuilder()
                .withOwnCashBalance(10)
                .withCompetitorCashBalance(0)
                .withTotalProductQuantity(10)
                .withEstimatedProductCost(1)
                .build();

        // when
        BidderCalculator result = new BidderStrategyResolverService().resolve(context);

        // then
        assertThat(result.getType()).isEqualTo(StrategyType.EMPTY_COMPETITOR_BALANCE);
    }

    @Test
    void should_return_ADVANTAGE_IN_QUANTITY_bidder_strategy_on_resolve_if_matches() {
        // given
        BidderContext context = new BidderContext.BidderContextBuilder()
                .withTotalProductQuantity(10)
                .withWonProductQuantity(6)
                .withCompetitorCashBalance(4)
                .withOwnCashBalance(4)
                .build();

        // when
        BidderCalculator result = new BidderStrategyResolverService().resolve(context);

        // then
        assertThat(result.getType()).isEqualTo(StrategyType.ADVANTAGE_IN_QUANTITY);
    }
}