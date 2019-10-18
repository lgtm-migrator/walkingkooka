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

import org.junit.jupiter.api.Test;
import walkingkooka.math.NumberTypeVisitor;
import walkingkooka.math.NumberVisitorTesting;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.test.ToStringTesting;

public abstract class ConverterNumberNumberNumberTypeVisitorTestCase<V extends ConverterNumberNumberNumberTypeVisitorNumber<N>, N extends Number> implements NumberVisitorTesting<V>,
        ToStringTesting<V> {

    ConverterNumberNumberNumberTypeVisitorTestCase() {
        super();
    }

    @Test
    public final void testToString() {
        final V visitor = this.createVisitor();
        visitor.accept(123);
        this.toStringAndCheck(visitor, "Number->" + this.targetType().getSimpleName());
    }

    abstract Class<N> targetType();

    @Override
    public final String typeNamePrefix() {
        return ConverterNumberNumber.class.getSimpleName() + NumberTypeVisitor.class.getSimpleName();
    }

    @Override
    public final String typeNameSuffix() {
        return this.targetType().getSimpleName();
    }

    @Override
    public final JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
