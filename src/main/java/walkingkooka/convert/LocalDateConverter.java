/*
 * Copyright 2018 Miroslav Pokorny (github.com/mP1)
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
 *
 */

package walkingkooka.convert;

import java.time.LocalDate;

/**
 * Converts an object into a {@link LocalDate}
 */
abstract class LocalDateConverter<T> extends LocalDateOrLocalDateTimeNumberConverter<LocalDate, T> {

    /**
     * Package private to limit sub classing.
     */
    LocalDateConverter(final long offset) {
        super(offset);
    }

    @Override
    Class<LocalDate> sourceType() {
        return LocalDate.class;
    }

    @Override
    T convert2(final LocalDate value) {
        return this.convert3(value.toEpochDay() + this.offset);
    }

    abstract T convert3(final long value);
}
