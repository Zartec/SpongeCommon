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
package org.spongepowered.common.mixin.core.stats;

import net.minecraft.stats.IStatType;
import net.minecraft.stats.StatBase;
import net.minecraft.util.text.TextComponentString;
import org.spongepowered.api.statistic.StatisticFormat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.common.interfaces.statistic.IMixinStatBase;
import org.spongepowered.common.registry.type.statistic.StatisticFormatTypeRegistryModule;

@Mixin(IStatType.class)
public interface MixinIStatType extends StatisticFormat {

    @Override
    default String format(long value) {
        IMixinStatBase dummy = (IMixinStatBase) new StatBase("dummy", new TextComponentString("dummy"));
        if (this.equals(StatBase.simpleStatType)) {
            return dummy.getNumberFormat().format(value);
        } else if (this.equals(StatBase.timeStatType) || this.equals(StatBase.distanceStatType)
            || this.equals(StatBase.divideByTen)) {
            return dummy.getDecimalFormat().format(value);
        }
        return null;
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    @Override
    default String getId() {
        return StatisticFormatTypeRegistryModule.ID_MAP.get(this);
    }

    @Override
    default String getName() {
        return getId();
    }

}
