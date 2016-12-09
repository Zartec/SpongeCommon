/*
 * This file is part of Sponge, licensed under the MIT License (MIT).
 *
 * Copyright (c) SpongePowered <https://www.spongepowered.org>
 * Copyright (c) contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.spongepowered.common.registry.type.statistic;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.minecraft.stats.AchievementList;
import org.spongepowered.api.registry.AdditionalCatalogRegistryModule;
import org.spongepowered.api.registry.util.RegisterCatalog;
import org.spongepowered.api.statistic.achievement.Achievement;
import org.spongepowered.api.statistic.achievement.Achievements;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public final class AchievementTypeRegistryModule implements AdditionalCatalogRegistryModule<Achievement> {

    @RegisterCatalog(Achievements.class)
    private final Map<String, Achievement> achievementMappings = Maps.newHashMap();

    @Override
    public Optional<Achievement> getById(String id) {
        return Optional.ofNullable(this.achievementMappings.get(checkNotNull(id).toLowerCase()));
    }

    @Override
    public Collection<Achievement> getAll() {
        return ImmutableList.copyOf(this.achievementMappings.values());
    }

    @Override
    public void registerDefaults() {
        for (net.minecraft.stats.Achievement mcAchievement : AchievementList.ACHIEVEMENTS) {
            Achievement achievement = (Achievement) mcAchievement;
            this.achievementMappings.put(achievement.getId(), achievement);
        }
    }

    @Override
    public void registerAdditionalCatalog(Achievement achievement) {
        checkNotNull(achievement, "null achievement");
        checkState(!this.achievementMappings.containsKey(achievement.getId()),
            "an achievement with that ID already exists");
        registerDefaults();
        // TODO: Builder
        this.achievementMappings.put(achievement.getId(), achievement);
    }

}
