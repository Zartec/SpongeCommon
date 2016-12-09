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
import org.spongepowered.api.registry.AdditionalCatalogRegistryModule;
import org.spongepowered.api.registry.util.RegisterCatalog;
import org.spongepowered.api.statistic.UniversalStatistic;
import org.spongepowered.api.statistic.UniversalStatistics;
import org.spongepowered.common.statistic.SpongeUniversalStatistic;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public final class UniversalStatisticTypeRegistryModule implements AdditionalCatalogRegistryModule<UniversalStatistic> {

    @RegisterCatalog(UniversalStatistics.class)
    private final Map<String, UniversalStatistic> universalStatisticMappings = Maps.newHashMap();

    @Override
    public Optional<UniversalStatistic> getById(String id) {
        return Optional.ofNullable(this.universalStatisticMappings.get(id.toLowerCase()));
    }

    @Override
    public Collection<UniversalStatistic> getAll() {
        return ImmutableList.copyOf(this.universalStatisticMappings.values());
    }

    @Override
    public void registerDefaults() {
        this.universalStatisticMappings.put("mineBlock", new SpongeUniversalStatistic("mineBlock"));
        this.universalStatisticMappings.put("craftItem", new SpongeUniversalStatistic("craftItem"));
        this.universalStatisticMappings.put("useItem", new SpongeUniversalStatistic("useItem"));
        this.universalStatisticMappings.put("breakItem", new SpongeUniversalStatistic("breakItem"));
        this.universalStatisticMappings.put("pickup", new SpongeUniversalStatistic("pickup"));
        this.universalStatisticMappings.put("drop", new SpongeUniversalStatistic("drop"));
    }

    @Override
    public void registerAdditionalCatalog(UniversalStatistic stat) {
        checkNotNull(stat, "null statistic");
        checkState(!this.universalStatisticMappings.containsKey(stat.getId()),
            "a statistic with that ID already exists");
        this.universalStatisticMappings.put(stat.getId(), stat);
    }

}
