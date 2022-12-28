package org.testtask.auction.calculator;

import org.junit.jupiter.api.Test;
import org.testtask.auction.model.BidderContext;

import static org.assertj.core.api.Assertions.assertThat;

class AdvantageInQuantityBidderCalculatorTest {

    @Test
    void should_place_bid_happy_path() {
        // given
        BidderContext context = new BidderContext();

        // when
        int result = new AdvantageInQuantityBidderCalculator().placeBid(context);
        // then
        assertThat(result).isEqualTo(0);
    }

    @Test
    void should_return_true_on_matches_if_won_quantity_is_bigger_then_half_of_total() {
        // given
        BidderContext context = new BidderContext.BidderContextBuilder()
                .withTotalProductQuantity(10)
                .withWonProductQuantity(6)
                .build();

        // when
        boolean result = new AdvantageInQuantityBidderCalculator().matches(context);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void should_return_false_on_matches_if_won_quantity_is_equal_then_half_of_total() {
        // given
        BidderContext context = new BidderContext.BidderContextBuilder()
                .withTotalProductQuantity(10)
                .withWonProductQuantity(5)
                .build();

        // when
        boolean result = new AdvantageInQuantityBidderCalculator().matches(context);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void should_return_false_on_matches_if_won_quantity_is_less_then_half_of_total() {
        // given
        BidderContext context = new BidderContext.BidderContextBuilder()
                .withTotalProductQuantity(10)
                .withWonProductQuantity(4)
                .build();

        // when
        boolean result = new AdvantageInQuantityBidderCalculator().matches(context);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void should_return_ADVANTAGE_IN_QUANTITY_type_on_get_type() {
        // then
        assertThat(new AdvantageInQuantityBidderCalculator().getType()).isEqualTo(StrategyType.ADVANTAGE_IN_QUANTITY);
    }
}