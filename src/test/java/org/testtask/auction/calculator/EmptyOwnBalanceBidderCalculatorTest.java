package org.testtask.auction.calculator;

import org.junit.jupiter.api.Test;
import org.testtask.auction.model.BidderContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmptyOwnBalanceBidderCalculatorTest {

    @Test
    void should_place_bid_happy_path() {
        // given
        BidderContext context = new BidderContext();

        // when
        int result = new EmptyOwnBalanceBidderCalculator().placeBid(context);
        // then
        assertThat(result).isEqualTo(0);
    }

    @Test
    void should_return_true_on_matches_if_empty_own_balance() {
        // given
        BidderContext context = new BidderContext();
        context.setOwnCashBalance(0);

        // when
        boolean result = new EmptyOwnBalanceBidderCalculator().matches(context);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void should_return_false_on_matches_if_not_empty_own_balance() {
        // given
        BidderContext context = new BidderContext();
        context.setOwnCashBalance(1);

        // when
        boolean result = new EmptyOwnBalanceBidderCalculator().matches(context);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void should_return_EMPTY_OWN_BALANCE_type_on_get_type() {
        // then
        assertThat(new EmptyOwnBalanceBidderCalculator().getType()).isEqualTo(StrategyType.EMPTY_OWN_BALANCE);
    }

}