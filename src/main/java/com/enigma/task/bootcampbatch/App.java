package com.enigma.task.bootcampbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.enigma.task.bootcampbatch.config.DaoSpringConfig;

@ComponentScan(basePackages={"com.enigma.task.bootcampbatch"})
@EnableJpaRepositories({"com.enigma.task.bootcampbatch.repository"})
@EntityScan({"com.enigma.task.bootcampbatch.model"})
@Import({DaoSpringConfig.class})
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
		SpringApplication.run(App.class, args);
    }
}
