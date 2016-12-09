package org.spongepowered.common.interfaces.statistic;

import net.minecraft.stats.Achievement;

public interface IMixinAchievement {

    void addChild(Achievement child);

}
