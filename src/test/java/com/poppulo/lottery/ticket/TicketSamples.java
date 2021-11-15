package com.poppulo.lottery.ticket;

import com.poppulo.lottery.line.Line;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TicketSamples {

    static Ticket generateOne() {
        return new Ticket(List.of(
                new Line(0, 1, 0),
                new Line(2, 2, 2)));
    }

    static void assertOne(Ticket ticket) {
        assertThat(ticket.getLines())
                .extracting(Line::getResult)
                .containsExactly(5, 0);
    }

    static List<Ticket> generateThree() {
        return List.of(
                new Ticket(List.of(
                        new Line(0, 0, 2))),
                new Ticket(List.of(
                        new Line(0, 0, 0),
                        new Line(0, 1, 1))),
                new Ticket(List.of(
                        new Line(0, 0, 1),
                        new Line(0, 1, 2),
                        new Line(0, 2, 0),
                        new Line(1, 1, 1))));
    }

    static void assertThree(Ticket[] tickets) {
        assertThat(tickets)
                .hasSize(3)
                .flatExtracting(Ticket::getLines)
                .extracting(Line::getResult)
                .containsExactly(
                        10,
                        10, 5,
                        10, 5, 1, 0);
    }

}
