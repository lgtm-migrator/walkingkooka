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
import walkingkooka.collect.map.Maps;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public abstract class MediaTypeHeaderParserTestCase<P extends MediaTypeHeaderParser, V> extends HeaderParserTestCase<P,
        MediaTypeParameterName<?>,
        V> {

    MediaTypeHeaderParserTestCase() {
        super();
    }

    // constants

    final static String TYPE = "type";
    final static String SUBTYPE = "subtype";

    @Test
    public final void testInvalidFails() {
        this.parseFails("\u0100\u0101",
                0);
    }

    @Test
    public final void testSlashSubtypeFails() {
        this.parseFails("/subtype",
                "Missing type at 0 in \"/subtype\"");
    }

    @Test
    public final void testTypeFails() {
        this.parseFails("type",
                "Missing type at 4 in \"type\"");
    }

    @Test
    public final void testTypeSlashFails() {
        this.parseFails("type/",
                "Missing sub type at 5 in \"type/\"");
    }

    @Test
    public final void testTypeInvalidCharacterFails() {
        this.parseFails("prima?ry/subtype",
                '?');
    }

    @Test
    public final void testTypeSlashSubTypeMissingFails() {
        this.parseFails("type/;");
    }

    @Test
    public final void testTypeSlashSubTypeSpaceInvalidFails() {
        this.parseFails("type/subtype Q", 'Q');
    }

    @Test
    public final void testTypeSlashSubTypeTabInvalidFails() {
        this.parseFails("type/subtype\tQ", 'Q');
    }

    @Test
    public final void testParameterNameInvalidFails() {
        this.parseFails("type/subtype;parameter;");
    }

    @Test
    public final void testParameterValueInvalidFails() {
        this.parseFails("type/subtype;parameter=value/");
    }

    @Test
    public final void testParameterValueMissingFails() {
        this.parseFails("type/subtype;parameter",
                "Missing parameter value at 21 in \"type/subtype;parameter\"");
    }

    @Test
    public final void testParameterValueMissingFails2() {
        this.parseFails("type/subtype;parameter=",
                "Missing parameter value at 22 in \"type/subtype;parameter=\"");
    }

    @Test
    public final void testParameterValueMissingFails3() {
        this.parseFails("type/subtype;p1=v1;p2",
                "Missing parameter value at 20 in \"type/subtype;p1=v1;p2\"");
    }

    @Test
    public final void testParameterValueMissingFails4() {
        this.parseFails("type/subtype;p1=v1;p2=",
                "Missing parameter value at 21 in \"type/subtype;p1=v1;p2=\"");
    }

    @Test
    public final void testParameterValueUnclosedQuoteFails() {
        this.parseFails("type/subtype;parameter=\"",
                "Missing closing '\\\"' in \"type/subtype;parameter=\\\"\"");
    }

    @Test
    public final void testParameterValueInvalidEscapeFails() {
        this.parseFails("type/subtype;parameter=\"\\z\"",
                'z');
    }

    @Test
    public final void testParameterValueQuotedInvalidFails() {
        this.parseFails("type/subtype;p=\"v\";[",
                '[');
    }

    @Test
    public final void testParameterValueQuotedInvalidFails2() {
        this.parseFails("type/subtype;p=\"v\"[",
                '[');
    }

    @Test
    public void testSeparatorFails() {
        this.parseFails(",");
    }

    @Test
    public void testSeparatorTypeSlashSubtypeFails() {
        this.parseFails(",type/subtype", ',');
    }

    @Test
    public final void testTypeSpaceSlashSubFails() {
        this.parseFails("a /b", ' ');
    }

    @Test
    public final void testTypeTabSlashSubFails() {
        this.parseFails("a\t/b", '\t');
    }

    @Test
    public final void testTypeSlashSpaceSubFails() {
        this.parseFails("a/ b", ' ');
    }

    @Test
    public final void testTypeSlashTabSubFails() {
        this.parseFails("a/\tb", '\t');
    }

    @Test
    public final void testTypeSlashSubType() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE,
                TYPE,
                SUBTYPE);
    }

    @Test
    public final void testTypeWildcardSlashSubType() {
        this.parseAndCheck("*/*",
                MediaType.WILDCARD.string(),
                MediaType.WILDCARD.string());
    }

    @Test
    public final void testTypeSlashSubType2() {
        this.parseAndCheck("a/b",
                "a",
                "b");
    }

    @Test
    public final void testSpaceTypeSlashSub() {
        this.parseAndCheck(" a/b",
                "a",
                "b");
    }

    @Test
    public final void testTabTypeSlashSub() {
        this.parseAndCheck("\ta/b",
                "a",
                "b");
    }

    @Test
    public final void testTypeSlashSubTypeWildcard() {
        this.parseAndCheck("a/*",
                "a",
                MediaType.WILDCARD.string());
    }

    @Test
    public final void testTypeSlashSubTypeSpace() {
        this.parseAndCheck("a/b ",
                "a",
                "b");
    }

    @Test
    public final void testTypeSlashSubTypeTab() {
        this.parseAndCheck("a/b\t",
                "a",
                "b");
    }

    @Test
    public final void testTypeSlashSubTypeSpaceSpace() {
        this.parseAndCheck("a/b  ",
                "a",
                "b");
    }

    @Test
    public final void testTypeSlashSubTypeSpaceTab() {
        this.parseAndCheck("a/b \t",
                "a",
                "b");
    }

    @Test
    public final void testTypeSlashSubTypeTabTab() {
        this.parseAndCheck("a/b\t\t",
                "a",
                "b");
    }

    @Test
    public final void testTypeSlashSubTypeWhitespaceTabSpace() {
        this.parseAndCheck("abc/def\t ",
                "abc",
                "def");
    }

    @Test
    public final void testTypeSlashSubTypePlusSuffix() {
        this.parseAndCheck("abc/def+suffix",
                "abc",
                "def+suffix");
    }

    @Test
    public final void testTypeSlashSubTypePrefixSuffix() {
        this.parseAndCheck("a/b+suffix",
                "a",
                "b+suffix");
    }

    @Test
    public final void testTypeSlashVendorDotSubType() {
        this.parseAndCheck("a/vnd.b",
                "a",
                "vnd.b");
    }

    @Test
    public final void testTypeSlashSubTypeParameter() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE + ";p=v",
                TYPE,
                SUBTYPE,
                parameters("p", "v"));
    }

    @Test
    public final void testTypeSlashSubTypeParameter2() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE + ";parameter=value",
                TYPE,
                SUBTYPE,
                parameters("parameter", "value"));
    }

    @Test
    public final void testTypeSlashSubTypeParameterSpace() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE + ";parameter=value ",
                TYPE,
                SUBTYPE,
                parameters("parameter", "value"));
    }

    @Test
    public final void testTypeSlashSubTypeParameterTab() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE + ";parameter=value\t",
                TYPE,
                SUBTYPE,
                parameters("parameter", "value"));
    }

    @Test
    public final void testTypeSlashSubTypeParameterSpaceSpace() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE + ";parameter=value  ",
                TYPE,
                SUBTYPE,
                parameters("parameter", "value"));
    }

    @Test
    public final void testTypeSlashSubTypeQuotedParameterValue() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE + ";parameter=\"value\"",
                TYPE,
                SUBTYPE,
                parameters("parameter", "value"));
    }

    @Test
    public final void testTypeSlashSubTypeQuotedParameterValueSpace() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE + ";parameter=\"value\" ",
                TYPE,
                SUBTYPE,
                parameters("parameter", "value"));
    }

    @Test
    public final void testTypeSlashSubTypeQuotedParameterValueTab() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE + ";parameter=\"value\" ",
                TYPE,
                SUBTYPE,
                parameters("parameter", "value"));
    }

    @Test
    public final void testTypeSlashSubTypeQuotedParameterValueSpaceTabSpaceTab() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE + ";parameter=\"value\" \t \t",
                TYPE,
                SUBTYPE,
                parameters("parameter", "value"));
    }

    @Test
    public final void testTypeSlashSubTypeQuotedParameterValueOtherwiseInvalidCharacters() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE + ";parameter=\"val?ue\"",
                TYPE,
                SUBTYPE,
                parameters("parameter", "val?ue"));
    }

    @Test
    public final void testTypeSlashSubTypeQuotedParameterValueBackslashEscaped() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE + ";parameter=\"val\\\\ue\"",
                TYPE,
                SUBTYPE,
                parameters("parameter", "val\\ue"));
    }

    @Test
    public final void testTypeSlashSubTypeQuotedParameterValueEscapedDoubleQuote() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE + ";parameter=\"val\\\"ue\"",
                TYPE,
                SUBTYPE,
                parameters("parameter", "val\"ue"));
    }

    @Test
    public final void testTypeSlashSubTypeQuotedParameterValueParameter2() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE + ";p1=\"v1\";p2=v2",
                TYPE,
                SUBTYPE,
                parameters("p1", "v1", "p2", "v2"));
    }

    @Test
    public final void testTypeSlashSubTypeSpaceParameter() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE + "; p=v",
                TYPE,
                SUBTYPE,
                parameters("p", "v"));
    }

    @Test
    public final void testTypeSlashSubTypeTabParameter() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE + ";\tp=v",
                TYPE,
                SUBTYPE,
                parameters("p", "v"));
    }

    @Test
    public final void testTypeSlashSubTypeSpaceTabSpaceTabParameter2() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE + "; \t \tp=v",
                TYPE,
                SUBTYPE,
                parameters("p", "v"));
    }

    @Test
    public final void testTypeSlashSubTypeParameterParameter() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE + ";p=v;p2=v2",
                TYPE,
                SUBTYPE,
                parameters("p", "v", "p2", "v2"));
    }

    @Test
    public final void testTypeSlashSubTypeSpaceTabSpaceTabParameter() {
        this.parseAndCheck(TYPE + "/" + SUBTYPE + ";p=v; \t \tp2=v2",
                TYPE,
                SUBTYPE,
                parameters("p", "v", "p2", "v2"));
    }

    @Test
    public void testParameterBoundary() {
        this.parseAndCheck("type/subtype;boundary=\"abc-123\"",
                TYPE,
                SUBTYPE,
                parameters("boundary", MediaTypeBoundary.parse("\"abc-123\"")));
    }

    @Test
    public void testParameterCharset() {
        this.parseAndCheck("type/subtype;charset=utf-8",
                TYPE,
                SUBTYPE,
                parameters("charset", CharsetName.UTF_8));
    }

    final Map<MediaTypeParameterName<?>, Object> parameters(final String name, final Object value) {
        return Maps.one(MediaTypeParameterName.with(name), value);
    }

    final Map<MediaTypeParameterName<?>, Object> parameters(final String name,
                                                            final Object value,
                                                            final String name2,
                                                            final Object value2) {
        final Map<MediaTypeParameterName<?>, Object> parameters = Maps.ordered();
        parameters.put(MediaTypeParameterName.with(name), value);
        parameters.put(MediaTypeParameterName.with(name2), value2);

        return parameters;
    }

    private void parseAndCheck(final String text, final String type, final String subtype) {
        this.check(MediaType.parse(text), type, subtype);
    }

    abstract void parseAndCheck(final String text,
                                final String type,
                                final String subtype,
                                final Map<MediaTypeParameterName<?>, Object> parameters);

    final void check(final MediaType mediaType, final String type, final String subtype) {
        check(mediaType, type, subtype, MediaType.NO_PARAMETERS);
    }

    final void check(final MediaType mediaType,
                     final String type,
                     final String subtype,
                     final Map<MediaTypeParameterName<?>, Object> parameters) {
        assertEquals("type=" + mediaType, type, mediaType.type());
        assertEquals("subType=" + mediaType, subtype, mediaType.subType());
        assertEquals("parameters=" + mediaType, parameters, mediaType.parameters());
    }
}
