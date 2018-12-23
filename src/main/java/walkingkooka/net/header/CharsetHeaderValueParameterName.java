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

package walkingkooka.net.header;

import walkingkooka.collect.map.Maps;
import walkingkooka.naming.Name;

import java.util.Map;


/**
 * The {@link Name} of an optional parameter accompanying a {@link MediaType}. Note the name may not contain the whitespace, equals sign, semi colons
 * or commas.
 */
final public class CharsetHeaderValueParameterName<T> extends HeaderParameterName<T> implements
        Comparable<CharsetHeaderValueParameterName<?>> {

    // constants

    /**
     * A read only cache of already prepared {@link CharsetHeaderValueParameterName names}. These constants are incomplete.
     */
    final static Map<String, CharsetHeaderValueParameterName> CONSTANTS = Maps.sorted(String.CASE_INSENSITIVE_ORDER);

    /**
     * Creates and adds a new {@link CharsetHeaderValueParameterName} to the cache being built.
     */
    private static <T> CharsetHeaderValueParameterName<T> registerConstant(final String name,
                                                                           final HeaderValueConverter<T> converter) {
        final CharsetHeaderValueParameterName<T> parameterName = new CharsetHeaderValueParameterName<T>(name, converter);
        CharsetHeaderValueParameterName.CONSTANTS.put(name, parameterName);
        return parameterName;
    }

    /**
     * The q factor weight parameter.
     */
    public final static CharsetHeaderValueParameterName<Float> Q_FACTOR = registerConstant("q", HeaderValueConverter.qWeight());

    /**
     * Factory that creates a {@link CharsetHeaderValueParameterName}
     */
    public static CharsetHeaderValueParameterName<?> with(final String value) {
        MediaType.check(value, "value");

        final CharsetHeaderValueParameterName<?> parameterName = CONSTANTS.get(value);
        return null != parameterName ?
                parameterName :
                new CharsetHeaderValueParameterName<String>(value, QUOTED_UNQUOTED_STRING);
    }

    private final static HeaderValueConverter<String> QUOTED_UNQUOTED_STRING = HeaderValueConverter.quotedUnquotedString(
            CharsetHeaderValueListHeaderParser.QUOTED_PARAMETER_VALUE,
            false,
            CharsetHeaderValueListHeaderParser.UNQUOTED_PARAMETER_VALUE);

    /**
     * Private ctor use factory.
     */
    private CharsetHeaderValueParameterName(final String value,
                                            final HeaderValueConverter<T> converter) {
        super(value, converter);
    }

    // Comparable

    @Override
    public int compareTo(final CharsetHeaderValueParameterName<?> other) {
        return this.compareTo0(other);
    }

    // Object

    @Override
    boolean canBeEqual(final Object other) {
        return other instanceof CharsetHeaderValueParameterName;
    }
}
