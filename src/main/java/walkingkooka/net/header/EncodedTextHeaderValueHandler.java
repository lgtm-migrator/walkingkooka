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


import walkingkooka.naming.Name;

/**
 * A {@link HeaderValueHandler} that parses a content header value into a {@link EncodedText}.
 */
final class EncodedTextHeaderValueHandler extends NonStringHeaderValueHandler<EncodedText> {

    /**
     * Singleton
     */
    final static EncodedTextHeaderValueHandler INSTANCE = new EncodedTextHeaderValueHandler();

    /**
     * Private ctor use singleton.
     */
    private EncodedTextHeaderValueHandler() {
        super();
    }

    @Override
    EncodedText parse0(final String text, final Name name) {
        return EncodedTextHeaderValueHandlerHeaderValueParser.parseEncodedText(text, name.value());
    }

    @Override
    void check0(final Object value, final Name name) {
        this.checkType(value, EncodedText.class, name);
    }

    @Override
    String toText0(final EncodedText filename, final Name name) {
        return filename.toHeaderText();
    }

    @Override
    public String toString() {
        return EncodedText.class.getSimpleName();
    }
}