package ru.stm.lot4.receiver.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan({"ru.stm.lot4"})
@EntityScan("ru.stm.lot4.model")
@EnableJpaRepositories("ru.stm.lot4.repository")
public class ReceiverApplicationConfiguration {

    @Bean
    ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
