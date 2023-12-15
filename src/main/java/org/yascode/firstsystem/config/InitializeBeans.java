package org.yascode.firstsystem.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitializeBeans {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
