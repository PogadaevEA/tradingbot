package org.testtask.auction.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.testtask.auction.exception.BidderValidationException;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BidderValidationServiceTest {

    @ParameterizedTest(name = "{index}. IF quantity = {0}, cash = {1}, initialized = false THAN expect NO exceptions")
    @MethodSource("testHappyPathParameters")
    void should_validate_on_init_happy_path(int quantity, int cash) {
        // given
        boolean initialized = false;

        // then
        assertThatNoException().isThrownBy(() -> new BidderValidationService().validateOnInit(quantity, cash, initialized));
    }

    @Test
    void should_throw_exception_on_validate_on_init_if_bidder_is_already_initiated() {
        // given
        int quantity = 10;
        int cash = 20;
        boolean initialized = true;

        // then
        assertThatThrownBy(() -> new BidderValidationService().validateOnInit(quantity, cash, initialized))
                .isInstanceOf(BidderValidationException.class)
                .hasMessage("The reinitialization of the same bidder is prohibited.");
    }

    @ParameterizedTest(name = "{index}. IF quantity = {0} THAN expect BidderValidationException  ")
    @MethodSource("testQuantityParameters")
    void should_throw_exception_on_validate_on_init_if_quantity_less_than_two_or_not_even_number(int quantity) {
        // given
        int cash = 20;
        boolean initialized = false;

        // then
        assertThatThrownBy(() -> new BidderValidationService().validateOnInit(quantity, cash, initialized))
                .isInstanceOf(BidderValidationException.class)
                .hasMessage("Attempt to initiate bidder, but the quantity of the product does not comply with the auction rules. "
                            + "It must be an even positive number [2, 4, 6 ...].");
    }

    @Test
    void should_throw_exception_on_validate_on_init_if_cash_is_negative_number() {
        // given
        int quantity = 10;
        int cash = -1;
        boolean initialized = false;

        // then
        assertThatThrownBy(() -> new BidderValidationService().validateOnInit(quantity, cash, initialized))
                .isInstanceOf(BidderValidationException.class)
                .hasMessage("Attempt to initiate bidder, but cash limit is negative.");
    }

    @Test
    void should_validate_on_round_execution_happy_path() {
        // given
        boolean initialized = true;

        // then
        assertThatNoException().isThrownBy(() -> new BidderValidationService().validateOnRoundExecution(initialized));
    }

    @Test
    void should_throw_exception_on_validate_on_round_execution_if_bidder_is_not_initiated() {
        // given
        boolean initialized = false;

        // then
        assertThatThrownBy(() -> new BidderValidationService().validateOnRoundExecution(initialized))
                .isInstanceOf(BidderValidationException.class)
                .hasMessage("A bidder has to be initiated before the start of auction.");
    }

    private static Stream<Arguments> testHappyPathParameters() {
        return Stream.of(
                happyPathArgumentsOf(2, 0),
                happyPathArgumentsOf(4, 1),
                happyPathArgumentsOf(4, 2)
        );
    }

    private static Arguments happyPathArgumentsOf(int quantity, int cash) {
        return Arguments.of(quantity, cash);
    }

    private static Stream<Arguments> testQuantityParameters() {
        return Stream.of(
                quantityArgumentsOf(-4),
                quantityArgumentsOf(-1),
                quantityArgumentsOf(0),
                quantityArgumentsOf(1),
                quantityArgumentsOf(3),
                quantityArgumentsOf(11)
        );
    }

    private static Arguments quantityArgumentsOf(int quantity) {
        return Arguments.of(quantity);
    }
}