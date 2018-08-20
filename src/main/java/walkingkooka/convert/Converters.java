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

import walkingkooka.Value;
import walkingkooka.text.cursor.parser.Parser;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.type.PublicStaticHelper;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Supplier;

/**
 * Factory methods for numerous {@link Converter converters}.
 */
public final class Converters implements PublicStaticHelper {

    /**
     * Hours per day.
     */
    static final long HOURS_PER_DAY = 24;

    /**
     * Mins per day.
     */
    static final long MINUTES_PER_HOUR = 60;

    /**
     * Seconds per day.
     */
    static final long SECONDS_PER_MINUTE = 60;

    /**
     * Nanos per second.
     */
    static final long NANOS_PER_SECOND =  1000_000_000L;
    /**
     * Nanos per minute.
     */
    static final long NANOS_PER_MINUTE = NANOS_PER_SECOND * SECONDS_PER_MINUTE;
    /**
     * Nanos per hour.
     */
    static final long NANOS_PER_HOUR = NANOS_PER_MINUTE * MINUTES_PER_HOUR;
    /**
     * Nanos per day.
     */
    static final long NANOS_PER_DAY = NANOS_PER_HOUR * HOURS_PER_DAY;

    /**
     * [@see BooleanConverter}
     */
    public static <T> Converter booleanConverter(final Class<T> type, final T trueValue, final T falseValue) {
        return BooleanConverter.with(type, trueValue, falseValue);
    }

    /**
     * {@see ConverterCollection}
     */
    public static Converter collection(final List<Converter> converters) {
        return ConverterCollection.with(converters);
    }

    /**
     * {@see LocalDateLocalDateTimeConverter}
     */
    public static Converter localDateLocalDateTime() {
        return LocalDateLocalDateTimeConverter.INSTANCE;
    }

    /**
     * {@see LocalDateStringDateTimeFormatterConverter}
     */
    public static Converter localDateString(final DateTimeFormatter formatter) {
        return LocalDateStringDateTimeFormatterConverter.with(formatter);
    }

    /**
     * {@see LocalDateTimeStringDateTimeFormatterConverter}
     */
    public static Converter localDateTime(final DateTimeFormatter formatter) {
        return LocalDateTimeStringDateTimeFormatterConverter.with(formatter);
    }

    /**
     * {@see LocalDateTimeLocalDateConverter}
     */
    public static Converter localDateTimeLocalDate() {
        return LocalDateTimeLocalDateConverter.INSTANCE;
    }

    /**
     * {@see LocalDateTimeLocalTimeConverter}
     */
    public static Converter localDateTimeLocalTime() {
        return LocalDateTimeLocalTimeConverter.INSTANCE;
    }

    /**
     * {@see LocalTimeLocalDateTimeConverter}
     */
    public static Converter localTimeLocalDateTime() {
        return LocalTimeLocalDateTimeConverter.INSTANCE;
    }

    /**
     * {@see LocalTimeStringDateTimeFormatterConverter}
     */
    public static Converter localTimeString(final DateTimeFormatter formatter) {
        return LocalTimeStringDateTimeFormatterConverter.with(formatter);
    }

    /**
     * [@see NumberBigDecimalConverter}
     */
    public static Converter numberBigDecimal() {
        return NumberBigDecimalConverter.INSTANCE;
    }

    /**
     * [@see NumberBigIntegerConverter}
     */
    public static Converter numberBigInteger() {
        return NumberBigIntegerConverter.INSTANCE;
    }

    /**
     * [@see NumberDoubleConverter}
     */
    public static Converter numberDouble() {
        return NumberDoubleConverter.INSTANCE;
    }

    /**
     * {@see NumberLocalDateConverter}
     */
    public static Converter numberLocalDate() {
        return NumberLocalDateConverter.INSTANCE;
    }

    /**
     * {@see NumberLocalDateTimeConverter}
     */
    public static Converter numberLocalDateTime() {
        return NumberLocalDateTimeConverter.INSTANCE;
    }

    /**
     * {@see NumberLocalTimeConverter}
     */
    public static Converter numberLocalTime() {
        return NumberLocalTimeConverter.INSTANCE;
    }

    /**
     * [@see NumberLongConverter}
     */
    public static Converter numberLong() {
        return NumberLongConverter.INSTANCE;
    }

    /**
     * {@see NumberNumberConverter}
     */
    public static Converter numberNumber(final Converter bigDecimal,
                                      final Converter bigInteger,
                                      final Converter doubleConverter,
                                      final Converter longConverter) {
        return NumberNumberConverter.with(bigDecimal, bigInteger, doubleConverter, longConverter);
    }

    /**
     * {@see ParserConverter}
     */
    public static <V, PT extends ParserToken & Value<V>, PC extends ParserContext> Converter parser(final Class<V> type, final Parser<PT , PC> parser, final Supplier<PC> context){
        return ParserConverter.with(type, parser, context);
    }

    /**
     * {@see SimpleConverter}
     */
    public static Converter simple() {
        return SimpleConverter.INSTANCE;
    }

    /**
     * [@see StringConverter}
     */
    public static Converter string() {
        return StringConverter.INSTANCE;
    }

    /**
     * [@see StringBooleanConverter}
     */
    public static Converter stringBoolean() {
        return StringBooleanConverter.INSTANCE;
    }

    /**
     * {@see TruthyNumberBooleanConverter}
     */
    public static Converter truthyNumberBoolean() {
        return TruthyNumberBooleanConverter.INSTANCE;
    }

    /**
     * Stop creation
     */
    private Converters() {
        throw new UnsupportedOperationException();
    }
}
