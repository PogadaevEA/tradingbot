package org.testtask.auction.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testtask.auction.model.BidderContext;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultBidderCalculatorTest {

    @ParameterizedTest(name = "{index}. If quantity = {0}, cash = {1}, own cash balance = {2} Than expected = {3}")
    @MethodSource("testPlaceBidParameters")
    void should_place_bid_happy_path(int quantity, int cash, int ownCashBalance, int expected) {
        // given
        BidderContext context = new BidderContext();
        context.init(quantity, cash);
        context.setOwnCashBalance(ownCashBalance);

        // when
        int result = new DefaultBidderCalculator().placeBid(context);
        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_return_false_on_matches() {
        // given
        BidderContext context = new BidderContext();

        // when
        boolean result = new DefaultBidderCalculator().matches(context);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void should_return_DEFAULT_type_on_get_type() {
        // then
        assertThat(new DefaultBidderCalculator().getType()).isEqualTo(StrategyType.DEFAULT);
    }

    private static Stream<Arguments> testPlaceBidParameters() {
        return Stream.of(
                placeBidArgumentsOf(10, 20, 10, 2),
                placeBidArgumentsOf(10, 20, 1, 1),
                placeBidArgumentsOf(10, 20, 2, 2)
        );
    }

    private static Arguments placeBidArgumentsOf(int quantity, int cash, int ownCashBalance, int expected) {
        return Arguments.of(quantity, cash, ownCashBalance, expected);
    }
}