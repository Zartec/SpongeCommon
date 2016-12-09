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
import net.minecraft.stats.StatList;
import org.spongepowered.api.registry.AdditionalCatalogRegistryModule;
import org.spongepowered.api.registry.util.RegisterCatalog;
import org.spongepowered.api.statistic.Statistic;
import org.spongepowered.api.statistic.Statistics;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public final class StatisticTypeRegistryModule implements AdditionalCatalogRegistryModule<Statistic> {

    @RegisterCatalog(Statistics.class)
    private final Map<String, Statistic> statisticMappings = Maps.newHashMap();

    @Override
    public Optional<Statistic> getById(String id) {
        return Optional.ofNullable(this.statisticMappings.get(id.toLowerCase()));
    }

    @Override
    public Collection<Statistic> getAll() {
        return ImmutableList.copyOf(this.statisticMappings.values());
    }

    @Override
    public void registerDefaults() {
        StatList.ALL_STATS.stream().filter(stat -> !stat.isAchievement()).forEach(mcStat -> {
            Statistic stat = (Statistic) mcStat;
            this.statisticMappings.put(stat.getId(), stat);
        });
    }

    @Override
    public void registerAdditionalCatalog(Statistic stat) {
        checkNotNull(stat, "null statistic");
        checkState(!this.statisticMappings.containsKey(stat.getId()), "a statistic with that ID already exists");
        registerDefaults();
        // TODO: Builder
        this.statisticMappings.put(stat.getId(), stat);
    }

}
