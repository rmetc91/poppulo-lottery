package com.poppulo.lottery;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

@Configuration
public class Beans {

    /**
     * Components need a {@link RandomGenerator} bean that can be mocked in order to write deterministic unit tests.
     * <p>
     * However, it is recommended to use {@link ThreadLocalRandom} in a multi-threaded program for better performance,
     * which cannot be a Spring bean.
     * <p>
     * This bean solves that by providing a thin wrapper that delegates to {@code ThreadLocalRandom.current()} on demand.
     *
     * @return a {@link RandomGenerator} that delegates to {@code ThreadLocalRandom}.
     */
    @Bean
    RandomGenerator random() {
        return () -> ThreadLocalRandom.current().nextLong();
    }

}
