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

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import net.minecraft.stats.IStatType;
import net.minecraft.stats.StatBase;
import org.spongepowered.api.registry.CatalogRegistryModule;
import org.spongepowered.api.registry.util.RegisterCatalog;
import org.spongepowered.api.statistic.StatisticFormat;
import org.spongepowered.api.statistic.StatisticFormats;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

public final class StatisticFormatTypeRegistryModule implements CatalogRegistryModule<StatisticFormat> {

    public static final BiMap<IStatType, String> ID_MAP = HashBiMap.create(4);

    static {
        ID_MAP.put(StatBase.simpleStatType, "count");
        ID_MAP.put(StatBase.distanceStatType, "distance");
        ID_MAP.put(StatBase.divideByTen, "fractional");
        ID_MAP.put(StatBase.timeStatType, "time");
    }

    @RegisterCatalog(StatisticFormats.class)
    private final Map<String, StatisticFormat> formatMappings = Maps.newHashMap();

    @Override
    public Optional<StatisticFormat> getById(String id) {
        return Optional.ofNullable(this.formatMappings.get(id.toLowerCase()));
    }

    @Override
    public Collection<StatisticFormat> getAll() {
        return ImmutableList.copyOf(this.formatMappings.values());
    }

    @Override
    public void registerDefaults() {
        for (Entry<IStatType, String> entry : ID_MAP.entrySet()) {
            this.formatMappings.put(entry.getValue(), (StatisticFormat) entry.getKey());
        }
    }

}
