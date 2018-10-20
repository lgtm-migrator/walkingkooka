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

import walkingkooka.DecimalNumberContext;
import walkingkooka.type.PublicStaticHelper;

/**
 * Factory methods for numerous {@link Converter converters}.
 */
public final class ConverterContexts implements PublicStaticHelper {

    /**
     * {@see BasicConverterContext}
     */
    public static ConverterContext basic(final DecimalNumberContext context) {
        return BasicConverterContext.with(context);
    }

    /**
     * {@see FakeConverterContext}
     */
    public static ConverterContext fake() {
        return new FakeConverterContext();
    }

    /**
     * Stop creation
     */
    private ConverterContexts() {
        throw new UnsupportedOperationException();
    }
}