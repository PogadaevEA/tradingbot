package org.testtask.auction;

/**
 * Contains relevant info to the current bidder during the auction execution.
 */
public class BidderContext {

    private static final int PRODUCTS_TO_ROUND_WINNER = 2;
    private static final int PRODUCTS_TO_DRAW_ROUND = 1;

    private boolean initialized = false;
    private final BidsHistory bidsHistory = new BidsHistory();

    private int ownCashBalance;
    private int competitorCashBalance;

    private int wonProductQuantity = 0;
    private int totalProductQuantity;

    private int estimatedProductCost;

    public void init(int quantity, int cash) {
        this.estimatedProductCost = cash / quantity;
        this.initialized = true;
        this.ownCashBalance = cash;
        this.competitorCashBalance = cash;
        this.totalProductQuantity = quantity;
    }

    public void updateAfterBid(int own, int other) {
        if (isCompetitorLoser(own, other)) {
            wonProductQuantity += PRODUCTS_TO_ROUND_WINNER;
        }
        if(isDraw(own, other)) {
            wonProductQuantity += PRODUCTS_TO_DRAW_ROUND;
        }

        ownCashBalance -= own;
        competitorCashBalance -= other;
        bidsHistory.addRoundHistory(new BidsRound(own, other));
    }

    private boolean isCompetitorLoser(int own, int other) {
        return other < own;
    }

    private boolean isDraw(int own, int other) {
        return own == other;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public BidsHistory getBidsHistory() {
        return bidsHistory;
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
}
