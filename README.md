# Trading bot

This trading can participate in the auction and can compete with one of another trading bot, which follow the same rules of the auction:

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
The bot encapsulates several betting [strategy types](src%2Fmain%2Fjava%2Forg%2Ftesttask%2Fauction%2Fcalculator%2FStrategyType.java)
within itself. The decision about a next bet is made based on the current
[BidderContext](src%2Fmain%2Fjava%2Forg%2Ftesttask%2Fauction%2Fmodel%2FBidderContext.java). 
The [BidderContext](src%2Fmain%2Fjava%2Forg%2Ftesttask%2Fauction%2Fmodel%2FBidderContext.java) stores the fields relevant to 
the current auction for the current round. Also, it keeps the [Historical Data](src%2Fmain%2Fjava%2Forg%2Ftesttask%2Fauction%2Fmodel%2FBidsHistory.java) of 
the current auction from the very beginning.

### Available calculators:
1. [EmptyOwnBalanceBidder calculator](src%2Fmain%2Fjava%2Forg%2Ftesttask%2Fauction%2Fcalculator%2FEmptyOwnBalanceBidderCalculator.java)

The strategy _EMPTY_OWN_BALANCE_ is suitable if own cash limit is already reached. It always places ZERO monetary unit.

2. [EmptyCompetitorBalanceBidder calculator](src%2Fmain%2Fjava%2Forg%2Ftesttask%2Fauction%2Fcalculator%2FEmptyCompetitorBalanceBidderCalculator.java)

The strategy _EMPTY_COMPETITOR_BALANCE_ is suitable if an opponent already reached the cash limit. It always places ONE monetary unit, which is enough to win the round.

3. [AdvantageInQuantityBidder calculator](src%2Fmain%2Fjava%2Forg%2Ftesttask%2Fauction%2Fcalculator%2FAdvantageInQuantityBidderCalculator.java)

The strategy _ADVANTAGE_IN_QUANTITY_ is suitable if an opponent already reached the advantage in win quantity compared by opponent.
It always places ZERO monetary unit (MU), because it doesn't make any sense to spend MU, since we already reached the advantage in auction.

4. [AdvantageInQuantityBidder calculator](src%2Fmain%2Fjava%2Forg%2Ftesttask%2Fauction%2Fcalculator%2FAdvantageInQuantityBidderCalculator.java)

This strategy _FORECAST_PRICE_ is based on the article ["Forecasting electricity prices using bid data"](https://www.sciencedirect.com/science/article/pii/S0169207022000711).
The strategy is suitable when some historical data are existing to make some forecast about next bid. It uses historical data to 
analyze previous bids, own cash profit in any, number of remaining rounds and bit THRESHOLD for each round.
Historical data contains the previous bids of winner. This data are approximated using median formula in order to level the spread over points through
and get a stable and smooth function. Also, we need to avoid to be very risky, since we could spend all cash limit, and it is easy to lose. 
There is a threshold applier to avoid this case.
Steps of calculation:
1. Get bid as a median value of winners based on historical data
2. Calculate round threshold product cost based on 
   1. Estimated product cost for one round = **initial cash limit / total rounds quantity**.
   2. Threshold parameter = **4**.  
Round threshold product cost = Estimated product cost for one round * Threshold parameter
3. Take the minimum value of 1 and 2 steps. Since, there is the estimated cost of product for round, it doesn't make much sense to 
overpay significantly. This leads to premature spending of all the cash limit and perhaps the opponent simply takes the risk of making high bets. 
4. If bidder has profit in cash we could use this value in addition. It could be when opponent makes relly high bids.
5. Next bid can't be more than remaining cash limit. Return the less of them.

5. [DefaultBidder calculator](src%2Fmain%2Fjava%2Forg%2Ftesttask%2Fauction%2Fcalculator%2FDefaultBidderCalculator.java)

If none of the above strategies fit the provided BidderContext, the _DEFAULT_ strategy will be chosen.
It places either remaining cash limit or estimated product price, depending on what is left less.

> ❗No matter what strategy is used, the bit never exceeds the current cash limit.

## Further enhancements
Further improvements **should primarily be based on formal product requirements**. But already now we can suggest possible ways to improve the application.
1. Adhere to **microservice architecture**. This follows from the **domain-driven design** and introduces isolation between
the auction and bidders, and makes delivery and release independent.
2. Depending on the method of communication between this bot and the auction we need implement the contract for communication.
Most probably connection will be through **HTTP**, since we need to support consistency between auction and bot.
It should be kept in mind that there may be two possible options that primarily depend on the business logic of the auction application.
   1. The auction itself can execute requests to our bot, then we need to implement a connection point to our bot.
   2. The bot can call an auction when it receives a signal to bid, then you need to implement a call to the api of this auction.
In any of these options, we need to take care that several auctions can be running at the same time, which means we need to split the resources.
Bidding contexts between different auctions can be separated by a unique identifier. Access to the context within the same read/write auction 
must also be synchronized, for example, 
using locks [ReentrantReadWriteLock](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/ReentrantReadWriteLock.html).
3. **Improve the accuracy function** of forecast calculation. Unfortunately, there is not much current data to build full-fledged forecasts.
Additional parameters that can be relied upon would be - for example, historical data on other auctions, product type, real value of the product.
Based on this data, you can build more complex models and even apply machine learning. 
4. Since we must always take care of CI/CD processes, we need to introduce: 
   1. Insure in code quality
   - Auto-checking on code linter during the pull request checking, before merging new features.
   E.g. using **GitHub actions** or **SonarQube**.
   - Auto execution tests on each pull request and provide detailed report of it.
   2. Automatic delivery and deployment of new features. E.g configure **GitLab CI/CD** or **Jenkins**.
   3. Application containerization and separate stands for developers, testers and users.  It contributes to the stability of each stand, 
   independent feature delivery and increased product satisfaction. (**Docker, Kubernetes, Cloud platforms**)

## Getting started
This application provides a simple console application to compete with implemented bot.
To test it, please do the following instructions:

> ❗Preconditions: Java 17 should be installed at you machine.
1. Clone repository:
```text
git clone https://github.com/PogadaevEA/tradingbot.git
```
2. Build fat jar and run tests:
```text
./mvnw clean install
```
3. Run jar file, placed `/target`
```text
java -jar target/tradingbot-1.0-SNAPSHOT.jar
```