package com.poppulo.lottery.ticket;

public class BadIdException extends IllegalArgumentException {
    BadIdException(long id) {
        super("ID must be positive, got " + id);
    }
}
