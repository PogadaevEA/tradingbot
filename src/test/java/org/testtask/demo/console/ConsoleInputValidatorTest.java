package org.testtask.demo.console;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ConsoleInputValidatorTest {

    @ParameterizedTest(name = "{index}. If quantity = {0} Then expected validation result = {1}")
    @MethodSource("testValidateQuantityParameters")
    void should_validate_auction_quantity(int quantity, boolean valid) {
        // when
        boolean result = new ConsoleInputValidator().isValidAuctionQuantity(quantity);

        // then
        assertThat(result).isEqualTo(valid);
    }

    @ParameterizedTest(name = "{index}. If cash limit = {0} Then expected validation result = {1}")
    @MethodSource("testValidateCashLimitParameters")
    void should_validate_cash_limit(int cashLimit, boolean valid) {
        // when
        boolean result = new ConsoleInputValidator().isValidCashLimit(cashLimit);

        // then
        assertThat(result).isEqualTo(valid);
    }

    private static Stream<Arguments> testValidateQuantityParameters() {
        return Stream.of(
                quantityArgumentsOf(-2, false),
                quantityArgumentsOf(-1, false),
                quantityArgumentsOf(0, false),
                quantityArgumentsOf(1, false),
                quantityArgumentsOf(2, true),
                quantityArgumentsOf(3, false),
                quantityArgumentsOf(4, true)
        );
    }

    private static Arguments quantityArgumentsOf(int quantity, boolean valid) {
        return Arguments.of(quantity, valid);
    }

    private static Stream<Arguments> testValidateCashLimitParameters() {
        return Stream.of(
                cashLimitArgumentsOf(-100, false),
                cashLimitArgumentsOf(-1, false),
                cashLimitArgumentsOf(0, true),
                cashLimitArgumentsOf(1, true),
                cashLimitArgumentsOf(100, true)
        );
    }

    private static Arguments cashLimitArgumentsOf(int quantity, boolean valid) {
        return Arguments.of(quantity, valid);
    }
}