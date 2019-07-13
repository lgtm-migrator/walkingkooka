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

package walkingkooka.net.header;

import org.junit.jupiter.api.Test;
import walkingkooka.InvalidCharacterException;
import walkingkooka.collect.map.Maps;
import walkingkooka.naming.NameTesting2;

import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class LanguageTagNameNonWildcardTest extends LanguageTagNameTestCase<LanguageTagNameNonWildcard>
        implements NameTesting2<LanguageTagNameNonWildcard, LanguageTagName> {

    private final static String VALUE = "en";
    private final static Optional<Locale> LOCALE = Optional.of(Locale.forLanguageTag("en"));

    @Test
    public void testWithNullFails() {
        assertThrows(NullPointerException.class, () -> {
            LanguageTagNameNonWildcard.nonWildcard(null);
        });
    }

    @Test
    public void testWithEmptyFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            LanguageTagNameNonWildcard.nonWildcard("");
        });
    }

    @Test
    public void testWithInvalidLanguageTagNameFails() {
        assertThrows(InvalidCharacterException.class, () -> {
            LanguageTagNameNonWildcard.nonWildcard("\0xyz");
        });
    }

    @Test
    public void testWith() {
        final LanguageTagNameNonWildcard LanguageTagName = LanguageTagNameNonWildcard.nonWildcard(VALUE);
        this.check(LanguageTagName);
    }

    private void check(final LanguageTagName name) {
        this.check(name, VALUE, LOCALE);
    }

    @Test
    public void testConstant() {
        assertSame(LanguageTagNameNonWildcard.nonWildcard("en"),
                LanguageTagNameNonWildcard.nonWildcard("en"));
    }

    @Test
    public void testTestSameTrue() {
        this.testTrue(LanguageTag.with(LanguageTagName.with(this.nameText())));
    }

    @Test
    public void testTestSameTrueWithParameters() {
        this.testTrue(LanguageTagName.with(this.nameText()).setParameters(Maps.of(LanguageTagParameterName.Q_FACTOR, 0.5f)),
                LanguageTag.with(LanguageTagName.with(this.nameText())));
    }

    @Test
    public void testTestDifferentFalse() {
        this.testFalse(LanguageTag.with(LanguageTagName.with(this.differentNameText())));
    }

    @Test
    public void testTestDifferentFalseWithParameters() {
        this.testFalse(LanguageTagName.with(this.nameText()).setParameters(Maps.of(LanguageTagParameterName.Q_FACTOR, 0.5f)),
                LanguageTag.with(LanguageTagName.with(this.differentNameText())));
    }

    @Test
    public void testEqualsWildcard() {
        this.checkNotEquals(LanguageTagName.WILDCARD);
    }

    @Test
    public void testEqualsDifferentCase() {
        this.checkEquals(LanguageTagNameNonWildcard.nonWildcard("EN"));
    }

    @Test
    public void testCompareToArraySort() {
        final LanguageTagName ar = LanguageTagName.with("ar");
        final LanguageTagName br = LanguageTagName.with("br");
        final LanguageTagName cz = LanguageTagName.with("cz");
        final LanguageTagName de = LanguageTagName.with("de");
        final LanguageTagName wildcard = LanguageTagName.WILDCARD;

        this.compareToArraySortAndCheck(de, ar, cz, wildcard, br,
                wildcard, ar, br, cz, de);
    }

    @Override
    public LanguageTagNameNonWildcard createName(final String name) {
        return LanguageTagNameNonWildcard.nonWildcard(name);
    }

    @Override
    public String nameText() {
        return "en";
    }

    @Override
    public String differentNameText() {
        return "fr";
    }

    @Override
    public String nameTextLess() {
        return "de";
    }

    @Override
    public int minLength() {
        return 2;
    }

    @Override
    public int maxLength() {
        return 7;
    }

    @Override
    public String possibleValidChars(final int position) {
        return ASCII_LETTERS + "_";
    }

    @Override
    public String possibleInvalidChars(final int position) {
        return CONTROL + WHITESPACE;
    }

    @Override
    Class<LanguageTagNameNonWildcard> languageTagNameType() {
        return LanguageTagNameNonWildcard.class;
    }
}
