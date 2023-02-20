package org.training360.sv2022jvjbfzarovizsgapotvizsga;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Sv2022JvjbfZarovizsgaPotvizsgaApplication {

	public static void main(String[] args) {
		SpringApplication.run(Sv2022JvjbfZarovizsgaPotvizsgaApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

}
