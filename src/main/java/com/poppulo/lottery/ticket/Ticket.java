package com.poppulo.lottery.ticket;

import com.poppulo.lottery.line.Line;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Ticket {

    @Id
    @GeneratedValue
    private long id;

    @OneToMany(cascade = ALL, fetch = EAGER)
    private List<Line> lines;

    private boolean checked;

    public Ticket(List<Line> lines) {
        this.lines = lines;
    }

}
