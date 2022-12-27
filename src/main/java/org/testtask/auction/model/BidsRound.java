package org.testtask.auction.model;

/**
 * Contains own and other bids for a certain auction round.
 */
public class BidsRound {

    private int own;
    private int other;

    public BidsRound(int own, int other) {
        this.own = own;
        this.other = other;
    }

    private BidsRound() {}

    public int getOwn() {
        return own;
    }

    public int getOther() {
        return other;
    }
}
