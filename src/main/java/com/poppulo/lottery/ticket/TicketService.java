package com.poppulo.lottery.ticket;

import com.poppulo.lottery.line.Line;
import com.poppulo.lottery.line.LineComparator;
import com.poppulo.lottery.line.LineService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final LineService lineService;
    private final LineComparator lineComparator;

    public Ticket create(int lineCount) {
        List<Line> lines = lineService.generateLines(lineCount);
        Ticket ticket = new Ticket(lines);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> list() {
        List<Ticket> tickets = ticketRepository.findAll();
        for (Ticket ticket : tickets) {
            ticket.getLines().sort(lineComparator);
        }
        return tickets;
    }

    public Optional<Ticket> find(long id) {
        return ticketRepository.findById(id)
                .stream()
                .peek(ticket -> ticket.getLines().sort(lineComparator))
                .findAny();
    }

    public void addMoreLines(Ticket ticket, int lineCount) {
        List<Line> moreLines = lineService.generateLines(lineCount);
        ticket.getLines().addAll(moreLines);
        ticketRepository.save(ticket);
    }

}
