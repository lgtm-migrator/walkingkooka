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

package walkingkooka.build.tostring;

import org.junit.Test;

public final class ByteArrayToStringBuilderTest extends VectorToStringBuilderTestCase<byte[]> {

    @Test
    public void testValueIncludesDefault(){
        final ToStringBuilder b = this.builder();
        b.label(LABEL);
        b.value(new byte[]{0, 1, 2});

        this.buildAndCheck(b, "LABEL=0, 1, 2");
    }

    @Override
    byte[] defaultValue() {
        return new byte[0];
    }

    @Override
    byte[] value1() {
        return new byte[]{123};
    }

    @Override
    byte[] value2() {
        return new byte[]{1, 2, 33};
    }

    @Override
    void append(final ToStringBuilder builder, final byte[] value) {
        builder.append(value);
    }

    @Override
    void value(final ToStringBuilder builder, final byte[] value) {
        builder.value(value);
    }

    @Override
    String value1ToString() {
        return "123";
    }

    @Override
    String value2ToString(final String separator) {
        return 1 + separator + 2 + separator + 33;
    }
}
