package com.poppulo.lottery.line;

import org.springframework.stereotype.Service;

import java.util.Comparator;

/**
 * Compares two lines such that the highest results come first.
 */
@Service
public class LineComparator implements Comparator<Line> {

    @Override
    public int compare(Line line1, Line line2) {
        return Integer.compare(line2.getResult(), line1.getResult());
    }

}
