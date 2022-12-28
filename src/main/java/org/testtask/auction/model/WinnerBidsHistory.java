package org.testtask.auction.model;

import java.util.*;

public class WinnerBidsHistory {

    private final List<Integer> winnerBids = new ArrayList<>();

    public void addWinnerBidsHistory(int winnerBid) {
        winnerBids.add(winnerBid);
    }

    public List<Integer> getWinnerBids() {
        return winnerBids;
    }

    public boolean isEmpty() {
        return winnerBids.isEmpty();
    }
}
