package org.testtask.auction.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testtask.auction.model.BidderContext;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class EmptyCompetitorBalanceBidderCalculatorTest {

    @ParameterizedTest(name = "{index}. IF own cash balance = {0} THEN expected = {1}")
    @MethodSource("testPlaceBidParameters")
    void should_place_bid_happy_path(int ownCashBalance, int expected) {
        // given
        BidderContext context = new BidderContext.BidderContextBuilder()
                .withOwnCashBalance(ownCashBalance)
                .build();

        // when
        int result = new EmptyCompetitorBalanceBidderCalculator().placeBid(context);
        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_return_true_on_matches_if_empty_competitor_balance() {
        // given
        BidderContext context = new BidderContext.BidderContextBuilder()
                .withCompetitorCashBalance(0)
                .build();

        // when
        boolean result = new EmptyCompetitorBalanceBidderCalculator().matches(context);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void should_return_false_on_matches_if_not_empty_competitor_balance() {
        // given
        BidderContext context = new BidderContext.BidderContextBuilder()
                .withCompetitorCashBalance(1)
                .build();

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

    private static Stream<Arguments> testPlaceBidParameters() {
        return Stream.of(
                placeBidArgumentsOf(10, 1),
                placeBidArgumentsOf(1, 1),
                placeBidArgumentsOf(0, 0),
                placeBidArgumentsOf(22, 1),
                placeBidArgumentsOf(3, 1)
        );
    }

    private static Arguments placeBidArgumentsOf(int ownCashBalance, int expected) {
        return Arguments.of(ownCashBalance, expected);
    }
}