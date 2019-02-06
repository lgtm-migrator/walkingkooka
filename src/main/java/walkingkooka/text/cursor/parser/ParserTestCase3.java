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
package walkingkooka.text.cursor.parser;

/**
 * The same as {@link ParserTestCase} but with the type related tests disabled.
 */
public abstract class ParserTestCase3<P extends Parser<T, C>, T extends ParserToken, C extends ParserContext> extends ParserTestCase<P, T, C> {

    @Override
    public final void testTestNaming() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void testAllConstructorsVisibility() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void testClassVisibility() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void testClassIsFinalIfAllConstructorsArePrivate() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void testAllMethodsVisibility() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void testAllFieldsVisibility() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final void testCheckToStringOverridden() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Class<P> type() {
        throw new UnsupportedOperationException();
    }
}
