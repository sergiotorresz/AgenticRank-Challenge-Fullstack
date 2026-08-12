package com.dishdash.config;

import com.dishdash.seed.DataSeeder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneOffset;

@Configuration
public class ClockConfig {

    /**
     * Fixed clock pinned to the same base instant the seed data is generated from,
     * so "today" (and therefore revenue) is deterministic and testable.
     */
    @Bean
    public Clock clock() {
        return Clock.fixed(DataSeeder.TODAY_BASE, ZoneOffset.UTC);
    }
}
