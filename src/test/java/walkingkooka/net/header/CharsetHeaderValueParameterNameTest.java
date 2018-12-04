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


import org.junit.Test;
import walkingkooka.Cast;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

final public class CharsetHeaderValueParameterNameTest extends HeaderParameterNameTestCase<CharsetHeaderValueParameterName<?>> {

    @Test(expected = IllegalArgumentException.class)
    public void testControlCharacterFails() {
        CharsetHeaderValueParameterName.with("parameter\u0001;");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSpaceFails() {
        CharsetHeaderValueParameterName.with("parameter ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTabFails() {
        CharsetHeaderValueParameterName.with("parameter\t");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonAsciiFails() {
        CharsetHeaderValueParameterName.with("parameter\u0100;");
    }

    @Test
    public void testValid() {
        this.createNameAndCheck("Custom");
    }

    @Test
    public void testConstantNameReturnsConstant() {
        assertSame(CharsetHeaderValueParameterName.Q_FACTOR,
                CharsetHeaderValueParameterName.with(CharsetHeaderValueParameterName.Q_FACTOR.value()));
    }

    // parameter value......................................................................................

    @Test
    public void testParameterValueAbsent() {
        this.parameterValueAndCheckAbsent(CharsetHeaderValueParameterName.with("absent-parameter"),
                this.charsetHeaderValue());
    }

    @Test
    public void testParameterValuePresent() {
        final CharsetHeaderValueParameterName<Float> parameter = CharsetHeaderValueParameterName.Q_FACTOR;
        final float value = 0.5f;

        this.parameterValueAndCheckPresent(parameter,
                this.charsetHeaderValue(),
                value);
    }

    private CharsetHeaderValue charsetHeaderValue() {
        return CharsetHeaderValue.parse("utf-8; q=0.5").get(0);
    }

    // toValue...........................................................................................

    @Test
    public void testToValueQFactor() {
        this.toValueAndCheck(CharsetHeaderValueParameterName.Q_FACTOR,
                "0.5",
                0.5f);
    }

    @Test
    public void testToValueString() {
        this.toValueAndCheck(Cast.to(CharsetHeaderValueParameterName.with("xyz")),
                "abc",
                "abc");
    }

    // toString...........................................................................................

    @Test
    public void testToString() {
        final String name = "parameter123";
        assertEquals(name, CharsetHeaderValueParameterName.with(name).toString());
    }

    @Override
    protected CharsetHeaderValueParameterName<Object> createName(final String name) {
        return Cast.to(CharsetHeaderValueParameterName.with(name));
    }

    @Override
    protected String nameText() {
        return "parameter123";
    }

    @Override
    protected Class<CharsetHeaderValueParameterName<?>> type() {
        return Cast.to(CharsetHeaderValueParameterName.class);
    }
}