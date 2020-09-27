package ru.job4j.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

/**
 * The configuration file
 * @author Roman Yakimkin (r.yakimin@yandex.ru)
 * @since 26.09.2020
 * @version 1.0
 */
@Configuration
public class Config {

    @Bean
    public RestTemplate getTemplate() {
        return new RestTemplate();
    }
}
