package org.example.tradeitggskinchecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TradeItGgSkinCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeItGgSkinCheckerApplication.class, args);
    }

}
