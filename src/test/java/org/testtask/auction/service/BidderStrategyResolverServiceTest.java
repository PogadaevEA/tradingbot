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
        BidderContext context = new BidderContext();
        context.init(10, 20);

        // when
        BidderCalculator result = new BidderStrategyResolverService().resolve(context);

        // then
        assertThat(result.getType()).isEqualTo(StrategyType.DEFAULT);
    }

    @Test
    void should_return_EMPTY_OWN_BALANCE_bidder_strategy_on_resolve_if_matches() {
        // given
        BidderContext context = new BidderContext();
        context.init(10, 20);
        context.setOwnCashBalance(0);

        // when
        BidderCalculator result = new BidderStrategyResolverService().resolve(context);

        // then
        assertThat(result.getType()).isEqualTo(StrategyType.EMPTY_OWN_BALANCE);
    }

    @Test
    void should_return_EMPTY_COMPETITOR_BALANCE_bidder_strategy_on_resolve_if_matches() {
        // given
        BidderContext context = new BidderContext();
        context.init(10, 20);
        context.setCompetitorCashBalance(0);

        // when
        BidderCalculator result = new BidderStrategyResolverService().resolve(context);

        // then
        assertThat(result.getType()).isEqualTo(StrategyType.EMPTY_COMPETITOR_BALANCE);
    }
}