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

package walkingkooka.text;

import org.junit.jupiter.api.Test;
import walkingkooka.io.serialize.SerializationProxyTesting;
import walkingkooka.test.ClassTestCase;
import walkingkooka.type.MemberVisibility;

import static org.junit.jupiter.api.Assertions.assertSame;

final public class IndentationConstantSerializationProxyTest extends ClassTestCase<IndentationConstantSerializationProxy>
        implements SerializationProxyTesting<IndentationConstantSerializationProxy> {

    @Test
    public void testToString() {
        final Indentation indentation = Indentation.with(' ', 10);
        assertSame(indentation, Indentation.with(' ', 10), "indentation instance is not a constant");

        this.toStringAndCheck(indentation.writeReplace(), indentation.toString());
    }

    @Override
    public Class<IndentationConstantSerializationProxy> type() {
        return IndentationConstantSerializationProxy.class;
    }

    @Override
    public MemberVisibility typeVisibility() {
        return MemberVisibility.PACKAGE_PRIVATE;
    }
}
