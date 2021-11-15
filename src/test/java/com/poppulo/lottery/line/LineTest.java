package com.poppulo.lottery.line;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class LineTest {

    @ParameterizedTest
    @CsvSource({
            "0,0,2",
            "0,1,1",
            "0,2,0",
            "1,0,1",
            "1,1,0",
            "2,0,0"
    })
    void result_10_whenSumOfNumbersIs2(int firstNumber, int secondNumber, int thirdNumber) {
        Line line = new Line(firstNumber, secondNumber, thirdNumber);
        assertThat(line.getResult()).isEqualTo(10);
    }

    @ParameterizedTest
    @CsvSource({
            "0,0,0",
            "1,1,1",
            "2,2,2"
    })
    void result_5_whenAllNumbersEqual(int firstNumber, int secondNumber, int thirdNumber) {
        Line line = new Line(firstNumber, secondNumber, thirdNumber);
        assertThat(line.getResult()).isEqualTo(5);
    }

    @ParameterizedTest
    @CsvSource({
            "0,1,2",
            "0,2,1",
            "0,2,2",
            "1,0,0",
            "1,0,2",
            "1,2,0",
            "1,2,2",
            "2,0,1",
            "2,1,0",
            "2,1,1",
    })
    void result_1_whenSecondAndThirdNumbersDifferFromFirst(int firstNumber, int secondNumber, int thirdNumber) {
        Line line = new Line(firstNumber, secondNumber, thirdNumber);
        assertThat(line.getResult()).isOne();
    }

    @ParameterizedTest
    @CsvSource({
            "0,0,1",
            "0,1,0",
            "1,1,2",
            "1,2,1",
            "2,0,2",
            "2,1,2",
            "2,2,0",
            "2,2,1"
    })
    void result_0_whenNoOtherConditionsMet(int firstNumber, int secondNumber, int thirdNumber) {
        Line line = new Line(firstNumber, secondNumber, thirdNumber);
        assertThat(line.getResult()).isZero();
    }

}
