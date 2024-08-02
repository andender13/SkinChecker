package org.example.tradeitggskinchecker;

import org.example.tradeitggskinchecker.enums.GunType;
import org.example.tradeitggskinchecker.enums.StatTrack;
import org.example.tradeitggskinchecker.enums.WeaponQuality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CheckerRunner {

    private final SeleniumSearchAutomation seleniumSearchAutomation;

    @Autowired
    public CheckerRunner(SeleniumSearchAutomation seleniumSearchAutomation) {
        this.seleniumSearchAutomation = seleniumSearchAutomation;
    }

    @Scheduled(fixedRate = 300000)
    public void startChecking() {
        Weapon weapon1 = Weapon.builder()
                .gunType(GunType.M4A4)
                .weaponQuality(WeaponQuality.FIELD_TESTED)
                .isStattrack(StatTrack.NO)
                .skinName("Temukau")
                .floatBorders(new Float("0.151", "0.27"))
                .build();
        Weapon weapon2 = Weapon.builder()
                .gunType(GunType.AWP)
                .weaponQuality(WeaponQuality.FACTORY_NEW)
                .isStattrack(StatTrack.NO)
                .skinName("The prince")
                .floatBorders(new Float("0", "0.27"))
                .build();

        Thread thread1 = new Thread(() -> {
            seleniumSearchAutomation.run(weapon1);
        });

        Thread thread2 = new Thread(() -> {
            seleniumSearchAutomation.run(weapon2);
        });

        thread1.start();
        thread2.start();
    }
}
