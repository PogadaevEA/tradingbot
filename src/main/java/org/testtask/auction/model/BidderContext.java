package org.testtask.auction.model;

import java.util.List;

/**
 * Contains relevant info to the current bidder during the auction execution.
 */
public class BidderContext {

    private static final int PRODUCTS_TO_ROUND_WINNER = 2;
    private static final int PRODUCTS_TO_DRAW_ROUND = 1;

    private boolean initialized;
    private WinnerBidsHistory winnerBidsHistory;

    private int ownCashBalance;
    private int competitorCashBalance;
    private int wonProductQuantity;
    private int totalProductQuantity;
    private int estimatedProductCost;
    private int remainingAuctionRounds;

    public BidderContext() {
        this.initialized = false;
        this.winnerBidsHistory = new WinnerBidsHistory();
        this.wonProductQuantity = 0;
    }

    public void init(int quantity, int cash) {
        this.estimatedProductCost = cash / quantity;
        this.initialized = true;
        this.ownCashBalance = cash;
        this.competitorCashBalance = cash;
        this.totalProductQuantity = quantity;
        this.remainingAuctionRounds = quantity / PRODUCTS_TO_DRAW_ROUND;
    }

    public void updateAfterBid(int own, int other) {
        updateQuantityAndHistoryData(own, other);

        ownCashBalance -= own;
        competitorCashBalance -= other;
        remainingAuctionRounds -= 1;
    }

    private void updateQuantityAndHistoryData(int own, int other) {
        if (isCompetitorLoser(own, other)) {
            wonProductQuantity += PRODUCTS_TO_ROUND_WINNER;
            winnerBidsHistory.addWinnerBidsHistory(own);
        } else if(isDraw(own, other)) {
            wonProductQuantity += PRODUCTS_TO_DRAW_ROUND;
            winnerBidsHistory.addWinnerBidsHistory(own);
        } else {
            winnerBidsHistory.addWinnerBidsHistory(other);
        }
    }

    private boolean isCompetitorLoser(int own, int other) {
        return other < own;
    }

    private boolean isDraw(int own, int other) {
        return own == other;
    }

    private BidderContext(BidderContextBuilder builder) {
        this.initialized = builder.initialized;
        this.winnerBidsHistory = builder.winnerBidsHistory;
        this.ownCashBalance = builder.ownCashBalance;
        this.competitorCashBalance = builder.competitorCashBalance;
        this.wonProductQuantity = builder.wonProductQuantity;
        this.totalProductQuantity = builder.totalProductQuantity;
        this.estimatedProductCost = builder.estimatedProductCost;
        this.remainingAuctionRounds = builder.remainingAuctionRounds;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public WinnerBidsHistory getBidsHistory() {
        return winnerBidsHistory;
    }

    public int getOwnCashBalance() {
        return ownCashBalance;
    }

    public int getCompetitorCashBalance() {
        return competitorCashBalance;
    }

    public int getEstimatedProductCost() {
        return estimatedProductCost;
    }

    public int getWonProductQuantity() {
        return wonProductQuantity;
    }

    public int getTotalProductQuantity() {
        return totalProductQuantity;
    }

    public int getRemainingAuctionRounds() {
        return remainingAuctionRounds;
    }

    public static class BidderContextBuilder {

        private boolean initialized;
        private WinnerBidsHistory winnerBidsHistory;
        private int ownCashBalance;
        private int competitorCashBalance;
        private int wonProductQuantity;
        private int totalProductQuantity;
        private int estimatedProductCost;
        private int remainingAuctionRounds;

        public BidderContextBuilder() {
            this.initialized = false;
            this.winnerBidsHistory = new WinnerBidsHistory();
            this.wonProductQuantity = 0;
        }

        public BidderContextBuilder withWinnerBidsHistory(List<Integer> winnerBidsHistory) {
            this.winnerBidsHistory.getWinnerBids().addAll(winnerBidsHistory);
            return this;
        }

        public BidderContextBuilder withWonProductQuantity(int wonProductQuantity) {
            this.wonProductQuantity = wonProductQuantity;
            return this;
        }

        public BidderContextBuilder withOwnCashBalance(int ownCashBalance) {
            this.ownCashBalance = ownCashBalance;
            return this;
        }

        public BidderContextBuilder withCompetitorCashBalance(int competitorCashBalance) {
            this.competitorCashBalance = competitorCashBalance;
            return this;
        }

        public BidderContextBuilder withTotalProductQuantity(int totalProductQuantity) {
            this.totalProductQuantity = totalProductQuantity;
            return this;
        }

        public BidderContextBuilder withEstimatedProductCost(int estimatedProductCost) {
            this.estimatedProductCost = estimatedProductCost;
            return this;
        }

        public BidderContextBuilder withRemainingAuctionRoundst(int remainingAuctionRounds) {
            this.remainingAuctionRounds = remainingAuctionRounds;
            return this;
        }

        public BidderContext build() {
            return new BidderContext(this);
        }
    }
}
