package com.store.order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import com.store.order.model.PetModel;
import com.store.order.repository.PetRepo;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = "com.store")
@EnableSwagger2
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	   public Docket productApi() {
	      return new Docket(DocumentationType.SWAGGER_2).select()
	         .apis(RequestHandlerSelectors.basePackage("com.store")).build();
	   }
	
	@Bean
    CommandLineRunner init (PetRepo petRepo){
        return args -> {
            List<PetModel> names = new ArrayList<PetModel>();
            names.add(new PetModel("Max", "dog", 15));
            names.add(new PetModel("ambaa", "cow", 15000));
            names.forEach(name -> petRepo.saveAll(names));
        };
    }
}
