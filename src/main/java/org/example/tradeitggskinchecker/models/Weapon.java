package org.example.tradeitggskinchecker.models;

import lombok.Builder;
import org.example.tradeitggskinchecker.enums.StatTrack;
import org.example.tradeitggskinchecker.enums.WeaponQuality;


@Builder
public class Weapon {
    public String skinName;
    public String gunType;
    public StatTrack isStattrack;
    public WeaponQuality weaponQuality;
    public Float floatBorders;
}
