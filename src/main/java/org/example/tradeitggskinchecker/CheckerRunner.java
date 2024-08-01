package org.example.tradeitggskinchecker;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CheckerRunner {
    @Scheduled(fixedRate = 300000)
    public static void startChecking() {
        SeleniumSearchAutomation.run();
    }
}
