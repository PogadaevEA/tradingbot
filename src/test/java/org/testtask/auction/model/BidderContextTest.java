package org.testtask.auction.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BidderContextTest {

    @Test
    void should_init_bidder_context_happy_path() {
        // given
        int quantity = 10;
        int cash = 20;
        BidderContext context = new BidderContext();

        // when
        context.init(quantity, cash);

        // then
        assertThat(context.isInitialized()).isTrue();
        assertThat(context.getEstimatedProductCost()).isEqualTo(2);
        assertThat(context.getBidsHistory().getWinnerBids()).isEmpty();
        assertThat(context.getOwnCashBalance()).isEqualTo(20);
        assertThat(context.getCompetitorCashBalance()).isEqualTo(20);
        assertThat(context.getWonProductQuantity()).isEqualTo(0);
        assertThat(context.getTotalProductQuantity()).isEqualTo(10);
    }

    @Test
    void should_update_after_bid_if_competitor_is_loser() {
        // given
        int quantity = 10;
        int cash = 20;
        BidderContext context = new BidderContext();
        context.init(quantity, cash);

        // when
        context.updateAfterBid(5, 2);

        // then
        assertThat(context.getBidsHistory().getWinnerBids()).hasSize(1);
        assertThat(context.getOwnCashBalance()).isEqualTo(15);
        assertThat(context.getCompetitorCashBalance()).isEqualTo(18);
        assertThat(context.getWonProductQuantity()).isEqualTo(2);
    }

    @Test
    void should_update_after_bid_if_draw_round() {
        // given
        int quantity = 10;
        int cash = 20;
        BidderContext context = new BidderContext();
        context.init(quantity, cash);

        // when
        context.updateAfterBid(5, 5);

        // then
        assertThat(context.getBidsHistory().getWinnerBids()).hasSize(1);
        assertThat(context.getOwnCashBalance()).isEqualTo(15);
        assertThat(context.getCompetitorCashBalance()).isEqualTo(15);
        assertThat(context.getWonProductQuantity()).isEqualTo(1);
    }

    @Test
    void should_update_after_bid_if_competitor_is_winner() {
        // given
        int quantity = 10;
        int cash = 20;
        BidderContext context = new BidderContext();
        context.init(quantity, cash);

        // when
        context.updateAfterBid(2, 5);

        // then
        assertThat(context.getBidsHistory().getWinnerBids()).hasSize(1);
        assertThat(context.getOwnCashBalance()).isEqualTo(18);
        assertThat(context.getCompetitorCashBalance()).isEqualTo(15);
        assertThat(context.getWonProductQuantity()).isEqualTo(0);
    }
}