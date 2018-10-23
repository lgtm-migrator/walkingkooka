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

package walkingkooka.text.spreadsheetformat;

import walkingkooka.DateTimeContext;
import walkingkooka.color.Color;
import walkingkooka.math.DecimalNumberContext;

/**
 * Context that accompanies a value format, holding local sensitive attributes such as the decimal point character.
 */
public interface SpreadsheetTextFormatContext extends DecimalNumberContext, DateTimeContext {

    /**
     * Returns the {@link Color} with the given number.
     */
    Color colorNumber(int number);

    /**
     * Returns the {@link Color} with the given name.
     */
    Color colorName(String name);

    /**
     * A {@link java.text.DecimalFormat} pattern, that should be used for non text values.
     */
    String generalDecimalFormatPattern();

    /**
     * The width of the "cell" in characters.
     * This value affects STAR operator.
     */
    int width();

    /**
     * Handles converting the given value to the target.
     */
    <T> T convert(final Object value, final Class<T> target);
}
