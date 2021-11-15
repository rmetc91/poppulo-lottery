package com.poppulo.lottery.line;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class LineService {

    private static final int NUMBERS_PER_LINE = 3;

    private final RandomGenerator random;

    public List<Line> generateLines(int lineCount) {
        if (lineCount <= 0) {
            throw new IllegalArgumentException("Line count must be positive. Got " + lineCount);
        }
        return Stream.generate(this::generateLine)
                .limit(lineCount)
                .toList();
    }

    public Line generateLine() {
        return new Line(
                random.nextInt(NUMBERS_PER_LINE),
                random.nextInt(NUMBERS_PER_LINE),
                random.nextInt(NUMBERS_PER_LINE));
    }

}
