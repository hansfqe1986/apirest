package com.prueba.apirest;

import com.prueba.apirest.utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PreDestroy;


@SpringBootApplication
public class ApirestApplication {

    @Autowired
    private JwtToken tokenStore;

    public static void main(String[] args) {
        SpringApplication.run(ApirestApplication.class, args);
	}

    @PreDestroy
    public void onShutdown() {
        tokenStore.clearAll();
    }
}
