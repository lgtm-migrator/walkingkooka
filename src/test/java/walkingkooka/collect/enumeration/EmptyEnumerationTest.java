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
 */

package walkingkooka.collect.enumeration;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.test.ClassTestCase;
import walkingkooka.type.MemberVisibility;

final public class EmptyEnumerationTest extends ClassTestCase<EmptyEnumeration<Void>>
        implements EnumerationTesting<EmptyEnumeration<Void>, Void> {

    @Test
    public void testWithoutMoreElements() {
        this.checkDoesntHasMoreElements(EmptyEnumeration.instance());
    }

    @Test
    public void testNextElementFails() {
        this.checkNextElementFails(this.createEnumeration());
    }

    @Override
    public EmptyEnumeration<Void> createEnumeration() {
        return EmptyEnumeration.instance();
    }

    @Override
    public Class<EmptyEnumeration<Void>> type() {
        return Cast.to(EmptyEnumeration.class);
    }

    @Override
    public MemberVisibility typeVisibility() {
        return MemberVisibility.PACKAGE_PRIVATE;
    }
}
