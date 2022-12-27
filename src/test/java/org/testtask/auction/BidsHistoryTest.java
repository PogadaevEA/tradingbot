package org.testtask.auction;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BidsHistoryTest {

    @Test
    void should_add_round_history() {
        // given
        BidsHistory bidsHistory = new BidsHistory();

        // when
        bidsHistory.addRoundHistory(new BidsRound(10, 20));

        // then
        assertThat(bidsHistory.getRoundsHistory()).hasSize(1);
    }

    @Test
    void should_throw_exception_when_add_round_history_with_null_value() {
        // given
        BidsHistory bidsHistory = new BidsHistory();

        // then
        assertThatThrownBy(() -> bidsHistory.addRoundHistory(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Attempt to add bid round to the history, but nothing has been passed.");
    }

    @Test
    void should_return_true_when_round_history_is_empty() {
        // given
        BidsHistory bidsHistory = new BidsHistory();

        // when
        boolean result = bidsHistory.isEmptyBidsHistory();

        // then
        assertThat(result).isTrue();
    }

    @Test
    void should_return_true_when_round_history_is_not_empty() {
        // given
        BidsHistory bidsHistory = new BidsHistory();
        bidsHistory.addRoundHistory(new BidsRound(10, 20));

        // when
        boolean result = bidsHistory.isEmptyBidsHistory();

        // then
        assertThat(result).isFalse();
    }

    @Test
    void should_return_last_competitor_bid() {
        // given
        BidsHistory bidsHistory = new BidsHistory();
        bidsHistory.addRoundHistory(new BidsRound(10, 20));

        // when
        Optional<Integer> result = bidsHistory.getLastCompetitorBid();

        // then
        assertThat(result).isPresent();
    }

    @Test
    void should_return_empty_value_when_last_competitor_bid_does_not_exist() {
        // given
        BidsHistory bidsHistory = new BidsHistory();

        // when
        Optional<Integer> result = bidsHistory.getLastCompetitorBid();

        // then
        assertThat(result).isNotPresent();
    }
}