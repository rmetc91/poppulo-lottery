package com.poppulo.lottery.line;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.random.RandomGenerator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LineServiceTest {
    @Mock
    private RandomGenerator randomGenerator;

    private LineService lineService;

    @BeforeEach
    void setUp() {
        lineService = new LineService(randomGenerator);
    }

    @Test
    void testGenerateLines() {
        given(randomGenerator.nextInt(3))
                .willReturn(0);
        List<Line> lines = lineService.generateLines(3);
        assertThat(lines)
                .hasSize(3)
                .extracting(Line::getResult)
                .containsExactly(5, 5, 5);
    }

    @Test
    void generateLine() {
        given(randomGenerator.nextInt(3))
                .willReturn(0)
                .willReturn(2)
                .willReturn(0);

        Line line = lineService.generateLine();

        assertThat(line.getResult()).isEqualTo(10);
    }
}