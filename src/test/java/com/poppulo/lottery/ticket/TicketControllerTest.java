package com.poppulo.lottery.ticket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class TicketControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    TicketRepository ticketRepository;

    @BeforeEach
    void setUp() {
        ticketRepository.deleteAll();
    }

    @Test
    void createTicket_status400_whenLineCountNotSpecified() {
        ResponseEntity<String> response = testRestTemplate.postForEntity(
                "/tickets",
                null,
                String.class);
        assertThat(response.getStatusCode())
                .isEqualTo(BAD_REQUEST);
    }

    @ParameterizedTest
    @ValueSource(ints = {Integer.MIN_VALUE, -1, 0})
    void createTicket_status400_whenLinesNotPositive(int nonPositiveLineCount) {
        ResponseEntity<String> response = testRestTemplate.postForEntity(
                "/tickets?lineCount=" + nonPositiveLineCount,
                null,
                String.class);
        assertThat(response.getStatusCode())
                .isEqualTo(BAD_REQUEST);
        assertThat(response.getBody())
                .isEqualTo("Line count must be positive. Got " + nonPositiveLineCount);
    }

    @Test
    void createTicket_status200_bodyContainsTicket_whenLinesSpecified() {
        ResponseEntity<Ticket> response = testRestTemplate.postForEntity(
                "/tickets?lineCount=3",
                null,
                Ticket.class);
        assertThat(response.getStatusCode())
                .isEqualTo(OK);
        assertThat(response.getBody())
                .extracting(Ticket::getLines)
                .asList()
                .hasSize(3);
    }

    @Test
    void listTickets_status200_bodyContainsTicketsWithSortedLines() {
        ticketRepository.saveAll(TicketSamples.generateThree());
        ResponseEntity<Ticket[]> response = testRestTemplate.getForEntity(
                "/tickets",
                Ticket[].class);
        assertThat(response.getStatusCode())
                .isEqualTo(OK);
        TicketSamples.assertThree(response.getBody());
    }

    @Test
    void findTicket_status200_bodyContainsSortedLines_whenIdMatches() {
        Ticket savedTicket = ticketRepository.save(TicketSamples.generateOne());
        ResponseEntity<Ticket> response = testRestTemplate.getForEntity(
                "/tickets/{id}",
                Ticket.class,
                savedTicket.getId());
        assertThat(response.getStatusCode())
                .isEqualTo(OK);
        TicketSamples.assertOne(response.getBody());
    }

    @Test
    void findTicket_status400_whenIdIllegal() {
        ResponseEntity<String> response = testRestTemplate.getForEntity(
                "/tickets/{id}",
                String.class,
                -1);
        assertThat(response.getStatusCode())
                .isEqualTo(BAD_REQUEST);
        assertThat(response.getBody())
                .isEqualTo("ID must be positive, got -1");
    }

    @Test
    void findTicket_status404_whenIdDoesNotMatch() {
        ResponseEntity<Ticket> response = testRestTemplate.getForEntity(
                "/tickets/{id}",
                Ticket.class,
                1);
        assertThat(response.getStatusCode())
                .isEqualTo(NOT_FOUND);
    }

    @Test
    void amendTicket_status200_andRepositoryContainsMoreLines_whenIdMatches() {
        Ticket savedTicket = ticketRepository.save(TicketSamples.generateOne());
        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/tickets/{id}?lineCount=1",
                PUT,
                null,
                Void.class,
                savedTicket.getId());
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(ticketRepository.findById(savedTicket.getId()))
                .hasValueSatisfying(ticket ->
                        assertThat(ticket.getLines()).hasSize(3));
    }

    @Test
    void amendTicket_status400_whenBadId() {
        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/tickets/{id}?lineCount=1",
                PUT,
                null,
                Void.class,
                -1);
        assertThat(response.getStatusCode())
                .isEqualTo(BAD_REQUEST);
    }

    @Test
    void amendTicket_status400_whenLineCountNotSpecified() {
        Ticket savedTicket = ticketRepository.save(TicketSamples.generateOne());
        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/tickets/{id}",
                PUT,
                null,
                Void.class,
                savedTicket.getId());
        assertThat(response.getStatusCode())
                .isEqualTo(BAD_REQUEST);
    }

    @Test
    void amendTicket_status400_whenLineCountNotPositive() {
        Ticket savedTicket = ticketRepository.save(TicketSamples.generateOne());
        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/tickets/{id}?lineCount=0",
                PUT,
                null,
                Void.class,
                savedTicket.getId());
        assertThat(response.getStatusCode())
                .isEqualTo(BAD_REQUEST);
    }

    @Test
    void amendTicket_status404_whenTicketNotFound() {
        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/tickets/{id}?lineCount=1",
                PUT,
                null,
                Void.class,
                1);
        assertThat(response.getStatusCode())
                .isEqualTo(NOT_FOUND);
    }

    @Test
    void amendTicket_status400_whenTicketAlreadyChecked() {
        Ticket ticket = TicketSamples.generateOne();
        ticket.setChecked(true);
        Ticket savedTicket = ticketRepository.save(ticket);

        ResponseEntity<Void> response = testRestTemplate.exchange(
                "/tickets/{id}?lineCount=1",
                PUT,
                null,
                Void.class,
                savedTicket.getId());
        assertThat(response.getStatusCode())
                .isEqualTo(BAD_REQUEST);
    }
}
