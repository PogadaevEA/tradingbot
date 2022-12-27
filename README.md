# Trading bot

This trading bot stands for participation in the auction and can compete with one of another trading bot,
which follow the rules of the auction:

**_GENERAL INFO_**

QU = quantity units MU = monetary units

**_EXPLANATION_**

A product x QU will be auctioned under 2 parties.
The parties have each y MU for auction. They then offer an arbitrary number simultaneously of its MU on
the first 2 QU of the product. After that, the bids will be visible to both.
The 2 QU of the product is awarded to who has offered the most MU; if both bid the same, then both get 1
QU. Both bidders must pay their amount - including the defeated. A bid of 0 MU is allowed. Bidding on each
2 QU is repeated until the supply of x QU is fully auctioned.

**_THESIS_**

Each bidder aims to get a larger amount than its competitor.
In an auction, the program that is able to get more QU than the other wins. In case of a tie, the program that
retains more MU wins.

## Trading strategy

//TODO explain trading strategy

## Getting started
This application provides a simple console application to compete with implemented bot.
To test it, please do the following instructions:

> Preconditions: Java 17 should be installed at you machine.
1. Clone repository:
> git clone https://github.com/PogadaevEA/tradingbot.git
2. Build fat jar and run tests:
> ./mvnw clean install
3. Run jar file, placed `/target`
> java -jar target/tradingbot-1.0-SNAPSHOT.jar








