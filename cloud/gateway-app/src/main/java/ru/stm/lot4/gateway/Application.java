package ru.stm.lot4.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.codec.ServerCodecConfigurer;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
//    @Bean
//    public ServerCodecConfigurer serverCodecConfigurer() {
//        return ServerCodecConfigurer.create();
//    }
}
