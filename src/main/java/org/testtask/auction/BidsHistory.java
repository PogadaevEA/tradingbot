package org.testtask.auction;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

public class BidsHistory {

    private final Queue<BidsRound> roundsHistory = new ArrayDeque<>();

    public Queue<BidsRound> getRoundsHistory() {
        return roundsHistory;
    }

    public void addRoundHistory(BidsRound bidsRound) {
        if (bidsRound == null) {
            throw new IllegalArgumentException("Attempt to add bid round to the history, but nothing has been passed.");
        }
        roundsHistory.add(bidsRound);
    }

    public boolean isEmptyBidsHistory() {
        return roundsHistory.isEmpty();
    }

    public Optional<Integer> getLastCompetitorBid() {
        return roundsHistory.peek() != null ? Optional.of(roundsHistory.peek().getOther()) : Optional.empty();
    }
}
