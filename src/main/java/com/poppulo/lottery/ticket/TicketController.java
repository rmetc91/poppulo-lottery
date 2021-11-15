package com.poppulo.lottery.ticket;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    Ticket create(@RequestParam int lineCount) {
        return ticketService.create(lineCount);
    }

    @GetMapping()
    List<Ticket> list() {
        return ticketService.list();
    }

    @GetMapping("/{id}")
    ResponseEntity<Ticket> find(@PathVariable long id) {
        if (id <= 0) {
            throw new BadIdException(id);
        }
        return ticketService.find(id)
                .map(ResponseEntity.ok()::body)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    ResponseEntity<Ticket> addMoreLines(@PathVariable long id, @RequestParam int lineCount) {
        if (id <= 0) {
            throw new BadIdException(id);
        }
        Optional<Ticket> maybeTicket = ticketService.find(id);
        if (maybeTicket.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Ticket ticket = maybeTicket.get();
        if (ticket.isChecked()) {
            throw new AlreadyCheckedException();
        }
        ticketService.addMoreLines(ticket, lineCount);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    ResponseEntity<String> handleBadLineCount(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
    }

}
