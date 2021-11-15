package com.poppulo.lottery.line;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static javax.persistence.GenerationType.AUTO;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Line {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    private int firstNumber;
    private int secondNumber;
    private int thirdNumber;
    private int result;

    public Line(int firstNumber, int secondNumber, int thirdNumber) {
        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
        this.thirdNumber = thirdNumber;
        this.result = calculateStatus();
    }

    private int calculateStatus() {
        if (firstNumber + secondNumber + thirdNumber == 2) {
            return 10;
        }

        if (firstNumber == secondNumber && secondNumber == thirdNumber) {
            return 5;
        }

        if (firstNumber != secondNumber && firstNumber != thirdNumber) {
            return 1;
        }

        return 0;
    }

}
