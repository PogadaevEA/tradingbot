package org.testtask.auction;

import org.junit.jupiter.api.Test;
import org.testtask.auction.exception.BidderValidationException;

import static org.assertj.core.api.Assertions.*;

class StrategiesBasedBidderTest {

    @Test
    void should_init_bidder_happy_path() {
        // given
        int quantity = 10;
        int cash = 20;

        // then
        assertThatNoException().isThrownBy(() -> new StrategiesBasedBidder().init(quantity, cash));
    }

    @Test
    void should_trow_exception_on_init_bidder_if_any_validation_failed() {
        // given
        int quantity = 10;
        int cash = -20;

        // then
        assertThatThrownBy(() -> new StrategiesBasedBidder().init(quantity, cash))
                .isInstanceOf(BidderValidationException.class)
                .hasMessage("Attempt to initiate bidder, but cash limit is negative.");
    }

    @Test
    void should_place_bid_happy_path() {
        // given
        StrategiesBasedBidder bidder = new StrategiesBasedBidder();
        bidder.init(10, 20);

        // when
        int result = bidder.placeBid();

        // then
        assertThat(result).isNotNegative();
    }

    @Test
    void should_trow_exception_on_place_bid_if_bidder_has_not_been_initialized_in_advance() {
        // given
        StrategiesBasedBidder bidder = new StrategiesBasedBidder();

        // then
        assertThatThrownBy(bidder::placeBid)
                .isInstanceOf(BidderValidationException.class)
                .hasMessage("A bidder has to be initiated before the start of auction.");
    }

    @Test
    void should_handle_bids_of_round_happy_path() {
        // given
        StrategiesBasedBidder bidder = new StrategiesBasedBidder();
        bidder.init(10, 20);

        // then
        assertThatNoException().isThrownBy(() -> bidder.bids(1, 2));
    }

    @Test
    void should_trow_exception_on_handle_bids_of_round_if_bidder_has_not_been_initialized_in_advance() {
        // given
        StrategiesBasedBidder bidder = new StrategiesBasedBidder();

        // then
        assertThatThrownBy(() -> bidder.bids(1, 2))
                .isInstanceOf(BidderValidationException.class)
                .hasMessage("A bidder has to be initiated before the start of auction.");
    }
}