package org.spongepowered.common.mixin.core.stats;

import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.common.interfaces.statistic.IMixinAchievement;

@Mixin(AchievementList.class)
public class MixinAchievementList {

    @Inject(method = "init", at = @At("RETURN"))
    public void buildAchievementTree(CallbackInfo ci) {
        for (Achievement achievement : AchievementList.ACHIEVEMENTS) {
            Achievement parent = achievement.parentAchievement;
            if (parent != null) {
                ((IMixinAchievement) parent).addChild(achievement);
            }
        }
    }

}
