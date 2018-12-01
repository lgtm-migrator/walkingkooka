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
import walkingkooka.naming.Name;
import walkingkooka.test.PackagePrivateClassTestCase;
import walkingkooka.text.CharSequences;

import static org.junit.Assert.assertEquals;

public abstract class HeaderValueConverterTestCase<C extends HeaderValueConverter<T>, T> extends PackagePrivateClassTestCase<C> {

    private final static String SUFFIX = HeaderValueConverter.class.getSimpleName();

    @Test
    public final void testNaming() {
        this.checkNamingStartAndEnd(this.requiredPrefix(), SUFFIX);
    }

    abstract protected String requiredPrefix();

    @Test(expected = HeaderValueException.class)
    public void testInvalidHeaderValueFails() {
        this.converter().parse(this.invalidHeaderValue(), this.name());
    }

    abstract protected String invalidHeaderValue();

    @Test(expected = NullPointerException.class)
    public void testCheckNullFails() {
        this.check(null);
    }

    @Test(expected = HeaderValueException.class)
    public void testCheckWrongTypeFails() {
        this.check(this);
    }

    @Test
    public void testCheck() {
        this.check(this.value());
    }

    @Test
    public final void testIsString() {
        final C converter = this.converter();

        assertEquals(converter + ".isString returned incorrect value",
                "String".equals(this.requiredPrefix()),
                converter.isString());
    }

    @Test
    public final void testToString() {
        assertEquals(this.converterToString(), this.converter().toString());
    }

    protected abstract String converterToString();

    protected abstract C converter();

    protected final void parseAndFormatAndCheck(final String text, final T value) {
        this.parseAndCheck(text, value);
        this.formatAndCheck(value, text);
    }

    protected final void parseAndCheck(final String value, final T expected) {
        this.parseAndCheck(value, this.name(), expected);
    }

    protected abstract Name name();

    protected final void parseAndCheck(final String value, final Name name, final T expected) {
        this.parseAndCheck(this.converter(), value, name, expected);
    }

    protected final void parseAndCheck(final C converter, final String value, final Name name, final T expected) {
        assertEquals(converter + " " + name + " of " + CharSequences.quoteIfChars(value), expected, converter.parse(value, name));
    }

    protected final void check(final Object value) {
        this.converter().check(value);
    }

    protected final void formatAndCheck(final T value, final String expected) {
        this.formatAndCheck(value, this.name(), expected);
    }

    protected final void formatAndCheck(final T value, final Name name, final String expected) {
        this.formatAndCheck(this.converter(), value, name, expected);
    }

    protected final void formatAndCheck(final C converter, final T value, final Name name, final String expected) {
        assertEquals(converter + " " + name + " of " + CharSequences.quoteIfChars(value), expected, converter.format(value, name));
    }

    protected abstract T value();
}