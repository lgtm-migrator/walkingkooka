/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.convert;

import java.time.LocalDateTime;

/**
 * Creates a {@link Long} from a {@link LocalDateTime}
 */
final class LocalDateTimeConverterLong extends LocalDateTimeConverter<Long> {

    /**
     * Creates a new instance with the given date offset.
     * A value of zero = 1/1/1970.
     */
    static LocalDateTimeConverterLong with(final long offset) {
        return new LocalDateTimeConverterLong(offset);
    }

    /**
     * Private ctor use factory
     */
    private LocalDateTimeConverterLong(final long offset) {
        super(offset);
    }

    @Override
    Long convert3(final long days, final double time, final LocalDateTime localDateTime) {
        if (time != 0) {
            this.failConversion(localDateTime);
        }
        return Long.valueOf(days);
    }

    @Override
    Class<Long> targetType() {
        return Long.class;
    }
}