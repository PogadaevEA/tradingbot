package org.testtask.auction.calculator;

import org.junit.jupiter.api.Test;
import org.testtask.auction.model.BidderContext;

import static org.assertj.core.api.Assertions.assertThat;

class EmptyCompetitorBalanceBidderCalculatorTest {

    @Test
    void should_place_bid_happy_path() {
        // given
        BidderContext context = new BidderContext();

        // when
        int result = new EmptyCompetitorBalanceBidderCalculator().placeBid(context);
        // then
        assertThat(result).isEqualTo(1);
    }

    @Test
    void should_return_true_on_matches_if_empty_competitor_balance() {
        // given
        BidderContext context = new BidderContext();
        context.setCompetitorCashBalance(0);

        // when
        boolean result = new EmptyCompetitorBalanceBidderCalculator().matches(context);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void should_return_false_on_matches_if_not_empty_competitor_balance() {
        // given
        BidderContext context = new BidderContext();
        context.setCompetitorCashBalance(1);

        // when
        boolean result = new EmptyCompetitorBalanceBidderCalculator().matches(context);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void should_return_EMPTY_COMPETITOR_BALANCE_type_on_get_type() {
        // then
        assertThat(new EmptyCompetitorBalanceBidderCalculator().getType()).isEqualTo(StrategyType.EMPTY_COMPETITOR_BALANCE);
    }
}