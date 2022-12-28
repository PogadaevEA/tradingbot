package org.testtask.auction.calculator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testtask.auction.model.BidderContext;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ForecastPriceBidderCalculatorTest {

    @ParameterizedTest(name = """
            {index}. IF own cash balance = {0}, competitor cash balance = {1}, estimated product cost = {2},
            winner bids history = {3}, remaining auction rounds = {4} THAN expected = {2}
            """)
    @MethodSource("testPlaceBidParameters")
    void should_place_bid_happy_path(
            int ownCashBalance,
            int competitorCashBalance,
            int estimatedProductCost,
            List<Integer> winnerBidsHistory,
            int remainingAuctionRounds,
            int expected
    ) {
        // given
        BidderContext context = new BidderContext.BidderContextBuilder()
                .withOwnCashBalance(ownCashBalance)
                .withCompetitorCashBalance(competitorCashBalance)
                .withEstimatedProductCost(estimatedProductCost)
                .withWinnerBidsHistory(winnerBidsHistory)
                .withRemainingAuctionRoundst(remainingAuctionRounds)
                .build();

        // when
        int result = new ForecastPriceBidderCalculator().placeBid(context);
        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void should_return_true_on_matches_if_non_empty_history_and_competitors_cash_balance() {
        // given
        BidderContext context = new BidderContext.BidderContextBuilder()
                .withOwnCashBalance(1)
                .withCompetitorCashBalance(1)
                .withWinnerBidsHistory(List.of(1, 2, 1))
                .build();

        // when
        boolean result = new ForecastPriceBidderCalculator().matches(context);

        // then
        assertThat(result).isTrue();
    }

    @Test
    void should_return_false_on_matches_if_non_empty_history_and_one_of_competitors_has_empty_cash_balance() {
        // given
        BidderContext context = new BidderContext.BidderContextBuilder()
                .withOwnCashBalance(1)
                .withCompetitorCashBalance(0)
                .withWinnerBidsHistory(List.of(1, 2, 1))
                .build();

        // when
        boolean result = new ForecastPriceBidderCalculator().matches(context);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void should_return_false_on_matches_if_non_empty_history_and_both_competitors_has_empty_cash_balance() {
        // given
        BidderContext context = new BidderContext.BidderContextBuilder()
                .withOwnCashBalance(0)
                .withCompetitorCashBalance(0)
                .withWinnerBidsHistory(List.of(1, 2, 1))
                .build();

        // when
        boolean result = new ForecastPriceBidderCalculator().matches(context);

        // then
        assertThat(result).isFalse();
    }

    @Test
    void should_return_FORECAST_PRICE_type_on_get_type() {
        // then
        assertThat(new ForecastPriceBidderCalculator().getType()).isEqualTo(StrategyType.FORECAST_PRICE);
    }

    private static Stream<Arguments> testPlaceBidParameters() {
        return Stream.of(
                placeBidArgumentsOf(15, 9, 4, List.of(3,8,5,1,2), 2, 5),
                placeBidArgumentsOf(9, 9, 4, List.of(3,8,5,1,2), 2, 3),
                placeBidArgumentsOf(1, 9, 4, List.of(3,8,5,1,2), 2, 1)
        );
    }

    private static Arguments placeBidArgumentsOf(
            int ownCashBalance,
            int competitorCashBalance,
            int estimatedProductCost,
            List<Integer> winnerBidsHistory,
            int remainingAuctionRounds,
            int expected
    ) {
        return Arguments.of(ownCashBalance, competitorCashBalance, estimatedProductCost, winnerBidsHistory, remainingAuctionRounds, expected);
    }
}