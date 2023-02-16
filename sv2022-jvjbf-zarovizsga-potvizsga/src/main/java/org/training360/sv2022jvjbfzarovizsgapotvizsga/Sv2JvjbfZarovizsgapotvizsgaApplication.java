package org.training360.sv2022jvjbfzarovizsgapotvizsga;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Sv2JvjbfZarovizsgapotvizsgaApplication {

    public static void main(String[] args) {
        SpringApplication.run(Sv2JvjbfZarovizsgapotvizsgaApplication.class, args);
    }


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
