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
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.map.Maps;
import walkingkooka.net.http.HttpHeaderScope;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

final public class MediaTypeTest extends HeaderValueWithParametersTestCase<MediaType, MediaTypeParameterName<?>> {

    // constants

    private final static String TYPE = "type";
    private final static String SUBTYPE = "subtype";
    private final static String PARAMETER_NAME = "parameter123";
    private final static String PARAMETER_VALUE = "value456";

    // tests

    @Test(expected = NullPointerException.class)
    public void testNullTypeFails() {
        MediaType.with(null, SUBTYPE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyTypeAndSubTypeFails() {
        MediaType.with("", SUBTYPE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTypeContainsSlashFails() {
        MediaType.with("ty/pe", SUBTYPE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTypeContainsInvalidCharacterFails() {
        MediaType.with("ty?pe", SUBTYPE);
    }

    @Test(expected = NullPointerException.class)
    public void testNullSubTypeFails() {
        MediaType.with(TYPE, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTypeAndEmptySubTypeFails() {
        MediaType.with(TYPE, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyIncludesSlashFails() {
        MediaType.with(TYPE, "s/ub");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyIncludesInvalidCharacterFails() {
        MediaType.with(TYPE, "s?ub");
    }

    @Test
    public void testWithTypeAndSubType() {
        check(MediaType.with(TYPE, SUBTYPE), TYPE, SUBTYPE);
    }

    @Test
    public void testWithTypeAndWildcardSubType() {
        check(MediaType.with(TYPE, MediaType.WILDCARD.string()),
                TYPE,
                MediaType.WILDCARD.string());
    }

    @Test
    public void testWithWildcardTypeAndWildcardSubType() {
        final String wildcard = MediaType.WILDCARD.string();
        check(MediaType.with(wildcard, wildcard),
                wildcard,
                wildcard);
    }

    // constants .........................................................................

    @Test
    public void testWithExistingConstant() {
        final MediaType constant = MediaType.APPLICATION_JAVASCRIPT;
        assertSame(constant, MediaType.with(constant.type().toUpperCase(), constant.subType().toUpperCase()));
    }

    @Test
    public void testWithExistingConstant2() {
        final MediaType constant = MediaType.APPLICATION_JAVASCRIPT;
        assertSame(constant, MediaType.with(constant.type().toLowerCase(), constant.subType().toLowerCase()));
    }

    // setType .........................................................................

    @Test(expected = NullPointerException.class)
    public void testSetTypeNullFails() {
        this.mediaType().setType(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetTypeEmptyFails() {
        this.mediaType().setType("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetTypeInvalidCharacterFails() {
        this.mediaType().setType("type/");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetTypeInvalidCharacterFails2() {
        this.mediaType().setType("type?");
    }

    @Test
    public void testSetTypeSame() {
        final MediaType mediaType = this.mediaType();
        assertSame(mediaType, mediaType.setType(TYPE));
    }

    @Test
    public void testSetTypeSameDifferentCase() {
        final MediaType mediaType = this.mediaType();
        assertNotEquals(TYPE, TYPE.toUpperCase());
        assertSame(mediaType, mediaType.setType(TYPE.toUpperCase()));
    }

    @Test
    public void testSetTypeDifferent() {
        final MediaType mediaType = this.mediaType();
        final String type = "different";
        final MediaType different = mediaType.setType(type);
        check(different, type, SUBTYPE, parameters());
    }

    // setSubType ..........................................................................................

    @Test(expected = NullPointerException.class)
    public void testSetSubTypeNullFails() {
        this.mediaType().setSubType(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetSubTypeEmptyFails() {
        this.mediaType().setSubType("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetSubTypeInvalidCharacterFails() {
        this.mediaType().setSubType("type/");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetSubTypeInvalidCharacterFails2() {
        this.mediaType().setSubType("type?");
    }

    @Test
    public void testSetSubTypeSame() {
        final MediaType mediaType = this.mediaType();
        assertSame(mediaType, mediaType.setSubType(SUBTYPE));
    }

    @Test
    public void testSetSubTypeSameDifferentCase() {
        final MediaType mediaType = this.mediaType();
        assertNotEquals(SUBTYPE, SUBTYPE.toUpperCase());
        assertSame(mediaType, mediaType.setSubType(SUBTYPE.toUpperCase()));
    }

    @Test
    public void testSetSubTypeDifferent() {
        final MediaType mediaType = this.mediaType();
        final String subtype = "different";
        final MediaType different = mediaType.setSubType(subtype);
        check(different, TYPE, subtype, parameters());
    }

    // setParameters ..........................................................................................

    @Test
    public void testSetParametersSameDifferentCase() {
        final MediaType mediaType = this.mediaType();

        final String parameter = PARAMETER_NAME.toUpperCase();
        assertNotEquals(parameter, PARAMETER_NAME);
        assertSame(mediaType, mediaType.setParameters(this.parameters(parameter, PARAMETER_VALUE)));
    }

    @Test
    public void testSetParametersDifferent() {
        final MediaType mediaType = this.mediaType();
        final Map<MediaTypeParameterName<?>, Object> parameters = MediaType.NO_PARAMETERS;
        final MediaType different = mediaType.setParameters(parameters);
        check(different, TYPE, SUBTYPE, parameters);
    }

    @Test
    public void testSetParametersDifferent2() {
        final MediaType mediaType = this.mediaType();
        final Map<MediaTypeParameterName<?>, Object> parameters = Maps.one(MediaTypeParameterName.with("different"), "value789");
        final MediaType different = mediaType.setParameters(parameters);
        check(different, TYPE, SUBTYPE, parameters);
    }

    private void check(final MediaType mediaType, final String type, final String subtype) {
        check(mediaType, type, subtype, MediaType.NO_PARAMETERS);
    }

    private void check(final MediaType mediaType,
                       final String type,
                       final String subtype,
                       final Map<MediaTypeParameterName<?>, Object> parameters) {
        assertEquals("type=" + mediaType, type, mediaType.type());
        assertEquals("subType=" + mediaType, subtype, mediaType.subType());
        assertEquals("parameters=" + mediaType, parameters, mediaType.parameters());
    }

    @Test
    public void testSetParametersConstant() {
        final MediaType constant = MediaType.TEXT_PLAIN;
        final MediaType withParameters = constant.setParameters(this.parameters());
        assertNotEquals(constant, withParameters);
        assertSame(constant, withParameters.setParameters(MediaType.NO_PARAMETERS));
    }

    // qWeight .......................................................................

    @Test
    public void testQParameterPresent() {
        this.qFactorWeightAndCheck(this.mediaType()
                .setParameters(parameters(MediaTypeParameterName.Q_FACTOR.value(), 0.5f)),
                0.5f);
    }

    @Test(expected = HeaderValueException.class)
    public void testQParameterPresentInvalidFails() {
        this.mediaType().setParameters(parameters(MediaTypeParameterName.Q_FACTOR.value(), "XYZ")).qFactorWeight();
    }

    @Test
    public void testQParameterAbsent() {
        this.qFactorWeightAndCheck(this.mediaType(), MediaType.Q_FACTOR_WEIGHT_ABSENT);
    }

    private void qFactorWeightAndCheck(final MediaType type, final float weight) {
        qFactorWeightAndCheck(type, Optional.of(weight));
    }

    private void qFactorWeightAndCheck(final MediaType type, final Optional<Float> weight) {
        assertEquals(type + " q weight", weight, type.qFactorWeight());
    }

    // parse .........................................................................

    @Test(expected = NullPointerException.class)
    public void testParseNullFails() {
        MediaType.parse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseEmptyFails() {
        MediaType.parse("");
    }

    @Test
    public void testParse() {
        assertEquals(MediaType.with("type1", "subtype1"), MediaType.parse("type1/subtype1"));
    }

    @Test
    public void testParseWithUnquotedParameter() {
        assertEquals(MediaType.with("type1", "subtype1")
                        .setParameters(Maps.one(MediaTypeParameterName.with("abc"), "def")),
                MediaType.parse("type1/subtype1;abc=def"));
    }

    @Test
    public void testParseWithQuotedParameter() {
        assertEquals(MediaType.with("type1", "subtype1")
                        .setParameters(Maps.one(MediaTypeParameterName.with("abc"), "d,\\ef")),
                MediaType.parse("type1/subtype1;abc=\"d,\\\\ef\""));
    }

    @Test(expected = NullPointerException.class)
    public void testParseListNullFails() {
        MediaType.parseList(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParseListEmptyFails() {
        MediaType.parseList("");
    }

    @Test
    public void testParseList() {
        assertEquals(Lists.of(
                MediaType.with("type1", "subtype1"),
                MediaType.with("type2", "subtype2")),
                MediaType.parseList("type1/subtype1,type2/subtype2"));
    }

    // isCompatible .........................................................................

    @Test(expected = NullPointerException.class)
    public void testIsCompatibleNullFails() {
        MediaType.ALL.isCompatible(null);
    }

    @Test
    public void testIsCompatibleWithSelf() {
        final MediaType mediaType = MediaType.with("type", "subtype");
        this.isCompatibleCheckTrue(mediaType, mediaType);
    }

    @Test
    public void testIsCompatibleWithSelfDifferentCase() {
        this.isCompatibleCheckTrue(MediaType.with("type", "subtype"),
                MediaType.with("TYPE", "SUBTYPE"));
    }

    @Test
    public void testIsCompatibleAnyAlwaysMatches() {
        this.isCompatibleCheckTrue(MediaType.ALL, MediaType.with("custom", "custom2"));
    }

    @Test
    public void testIsCompatibleAnyAndAnyMatches() {
        this.isCompatibleCheckTrue(MediaType.ALL, MediaType.ALL);
    }

    @Test
    public void testIsCompatibleSameTypeWildcardSubType() {
        final String type = "custom";
        this.isCompatibleCheckTrue(MediaType.with(type, MediaType.WILDCARD.string()),
                MediaType.with(type, SUBTYPE));
    }

    @Test
    public void testIsCompatibleDifferentTypeWildcardSubType() {
        this.isCompatibleCheckFalse(MediaType.with("custom", MediaType.WILDCARD.string()),
                MediaType.with("different", SUBTYPE));
    }

    @Test
    public void testIsCompatibleSameTypeSubType() {
        this.isCompatibleCheckTrue(this.mediaType(), this.mediaType());
    }

    @Test
    public void testIsCompatibleDifferentTypeSameSubType() {
        this.isCompatibleCheckFalse(this.mediaType(), MediaType.with("different", SUBTYPE));
    }

    @Test
    public void testIsCompatibleSameTypeDifferentSubType() {
        final String type = "type";
        this.isCompatibleCheckFalse(MediaType.with(type, "subtype"), MediaType.with(type, "different"));
    }

    private void isCompatibleCheckTrue(final MediaType mediaType, final MediaType other) {
        if (false == mediaType.isCompatible(other)) {
            fail(mediaType + " should be compatible with " + other + " but FALSE was returned.");
        }
    }

    private void isCompatibleCheckFalse(final MediaType mediaType, final MediaType other) {
        if (mediaType.isCompatible(other)) {
            fail(mediaType + " should NOT be compatible with " + other + " but TRUE was returned.");
        }
    }

    // toHeaderText........................................................................................................

    @Test
    public void testToHeaderTextParse() {
        final String text = "type/subtype";
        assertEquals(text, MediaType.parse(text).toHeaderText());
    }

    @Test
    public void testToHeaderTextParseWithParameters() {
        final String text = "type/subtype;a=b;c=d";
        assertEquals(text, MediaType.parse(text).toHeaderText());
    }

    @Test
    public void testToHeaderTextParseWithParametersWithQuotes() {
        final String text = "type/subtype;a=b;c=\"d\"";
        assertEquals(text, MediaType.parse(text).toHeaderText());
    }

    @Test
    public void testToHeaderText() {
        this.toHeaderTextAndCheck(MediaType.with(TYPE, SUBTYPE),
                TYPE + "/" + SUBTYPE);
    }

    @Test
    public void testToHeaderTextWithParameters() {
        this.toHeaderTextAndCheck(mediaType(), TYPE + "/" + SUBTYPE + ";parameter123=value456");
    }

    @Test
    public void testToHeaderTextWithParametersRequireQuotesWhitespace() {
        this.toHeaderTextAndCheck(MediaType.with(TYPE, SUBTYPE).setParameters(parameters("a", "b c")),
                "type/subtype; a=\"b c\"");
    }

    @Test
    public void testToHeaderTextWithParametersRequireQuotesBackslash() {
        this.toHeaderTextAndCheck(MediaType.with(TYPE, SUBTYPE).setParameters(parameters("a", "b\\c")),
                "type/subtype; a=\"b\\\\c\"");
    }

    // toString........................................................................................................

    @Test
    public void testToStringParse() {
        final String text = "type/subtype";
        assertEquals(text, MediaType.parse(text).toString());
    }

    @Test
    public void testToStringParseWithParameters() {
        final String text = "type/subtype;a=b;c=d";
        assertEquals(text, MediaType.parse(text).toString());
    }

    @Test
    public void testToStringParseWithParametersWithQuotes() {
        final String text = "type/subtype;a=b;c=\"d\"";
        assertEquals(text, MediaType.parse(text).toString());
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(MediaType.with(TYPE, SUBTYPE),
                TYPE + "/" + SUBTYPE);
    }

    @Test
    public void testToStringWithParameters() {
        this.toStringAndCheck(mediaType(), TYPE + "/" + SUBTYPE + ";parameter123=value456");
    }

    @Test
    public void testToStringWithParametersRequireQuotesWhitespace() {
        this.toStringAndCheck(MediaType.with(TYPE, SUBTYPE).setParameters(parameters("a", "b c")),
                "type/subtype; a=\"b c\"");
    }

    @Test
    public void testToStringWithParametersRequireQuotesBackslash() {
        this.toStringAndCheck(MediaType.with(TYPE, SUBTYPE).setParameters(parameters("a", "b\\c")),
                "type/subtype; a=\"b\\\\c\"");
    }

    @Test
    public void testToStringWithParametersRequireQuotesDoubleQuoteChar() {
        this.toStringAndCheck( MediaType.with(TYPE, SUBTYPE).setParameters(parameters("a", "b\"c")),
                "type/subtype; a=\"b\\\"c\"");
    }

    private void toStringAndCheck(final MediaType type, final String toString) {
        assertEquals("toString", toString, type.toString());
    }

    // toHeaderTextList...............................................................................................

    @Test(expected = NullPointerException.class)
    public void testToHeaderTextListNullFails() {
        MediaType.toHeaderTextList(null);
    }

    @Test
    public void testToHeaderTextListOne() {
        this.toHeaderTextListAndCheck("type1/subtype1",
                MediaType.with("type1", "subtype1"));
    }

    @Test
    public void testToHeaderTextListMany() {
        this.toHeaderTextListAndCheck("type1/subtype1, type2/subtype2",
                MediaType.with("type1", "subtype1"),
                MediaType.with("type2", "subtype2"));
    }

    private void toHeaderTextListAndCheck(final String toString, final MediaType... mediaTypes) {
        assertEquals("Format " + Arrays.toString(mediaTypes) + " failed",
                toString,
                MediaType.toHeaderTextList(Lists.of(mediaTypes)));
    }

    // helpers........................................................................................................

    @Override
    protected MediaType createHeaderValueWithParameters() {
        return this.mediaType();
    }

    private MediaType mediaType() {
        return MediaType.withParameters(TYPE, SUBTYPE, parameters(), "type/subtype;parameter123=value456");
    }

    private Map<MediaTypeParameterName<?>, Object> parameters() {
        return this.parameters(PARAMETER_NAME, PARAMETER_VALUE);
    }

    private Map<MediaTypeParameterName<?>, Object> parameters(final String name, final Object value) {
        return Maps.one(MediaTypeParameterName.with(name), value);
    }

    @Override
    protected HttpHeaderScope httpHeaderScope() {
        return HttpHeaderScope.REQUEST_RESPONSE;
    }

    @Override
    protected Class<MediaType> type() {
        return MediaType.class;
    }
}
