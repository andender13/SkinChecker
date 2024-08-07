package org.example.tradeitggskinchecker.selenium;

import org.example.tradeitggskinchecker.enums.WeaponType;
import org.example.tradeitggskinchecker.enums.StatTrack;
import org.example.tradeitggskinchecker.enums.WeaponQuality;
import org.example.tradeitggskinchecker.models.Float;
import org.example.tradeitggskinchecker.models.Weapon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SeleniumCheckerRunner {

    private final SeleniumSearchAutomation seleniumSearchAutomation;

    @Autowired
    public SeleniumCheckerRunner(SeleniumSearchAutomation seleniumSearchAutomation) {
        this.seleniumSearchAutomation = seleniumSearchAutomation;
    }

    @Scheduled(fixedRate = 300000)
    public void startChecking() {
        Weapon weapon1 = Weapon.builder()
                .gunType(WeaponType.M4A4)
                .weaponQuality(WeaponQuality.FIELD_TESTED)
                .isStattrack(StatTrack.NO)
                .skinName("Temukau")
                .floatBorders(new Float("0.151", "0.23"))
                .build();

        Thread thread1 = new Thread(() -> {
            seleniumSearchAutomation.run(weapon1);
        });

        thread1.start();
//        Weapon weapon2 = Weapon.builder()
//                .gunType(GunType.AWP)
//                .weaponQuality(WeaponQuality.FACTORY_NEW)
//                .isStattrack(StatTrack.NO)
//                .skinName("The prince")
//                .floatBorders(new Float("0", "0.27"))
//                .build();
//
//        Thread thread2 = new Thread(() -> {
//            seleniumSearchAutomation.run(weapon2);
//        });
//
//        thread2.start();
    }
}
