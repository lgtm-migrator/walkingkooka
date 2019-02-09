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
import walkingkooka.test.ClassTesting2;
import walkingkooka.type.MemberVisibility;

import static org.junit.jupiter.api.Assertions.assertNotSame;

final public class IndentationSerializationProxyTest implements ClassTesting2<IndentationSerializationProxy>,
        SerializationProxyTesting<IndentationSerializationProxy> {

    @Test
    public void testToString() {
        final Indentation indentation = Indentation.with('\t', 10);
        assertNotSame(indentation, Indentation.with('\t', 10), "indentation instance is a constant");

        this.toStringAndCheck(indentation.writeReplace(), indentation.toString());
    }

    @Override
    public Class<IndentationSerializationProxy> type() {
        return IndentationSerializationProxy.class;
    }

    @Override
    public MemberVisibility typeVisibility() {
        return MemberVisibility.PACKAGE_PRIVATE;
    }
}
