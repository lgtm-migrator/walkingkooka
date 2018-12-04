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
 * distributed under the License is distributed on an "UTF-8S IS" BASIS,
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

import java.util.List;
import java.util.Map;

public final class CharsetHeaderValueListHeaderParserTest extends HeaderParserTestCase<CharsetHeaderValueListHeaderParser,
        CharsetHeaderValueParameterName<?>,
        List<CharsetHeaderValue>> {

    // parse ...................................................................................................

    @Test
    public void testCharsetInvalidInitialCharacterFails() {
        this.parseFails("!utf-8", '!');
    }

    @Test
    public void testCharsetInvalidPartCharacterFails() {
        this.parseFails("utf!8", '!');
    }

    @Test
    public void testCharset() {
        this.parseAndCheck("utf-8", "utf-8");
    }

    @Test
    public void testCharsetInvalidCharacterFails2() {
        this.parseFails("UTF-8BC<");
    }

    @Test(expected = HeaderValueException.class)
    public void testCharsetEqualsFails() {
        CharsetHeaderValue.parse("UTF-8;b=");
    }

    @Test(expected = HeaderValueException.class)
    public void testCharsetSpaceEqualsFails() {
        CharsetHeaderValue.parse("UTF-8;b =");
    }

    @Test(expected = HeaderValueException.class)
    public void testCharsetTabEqualsFails() {
        CharsetHeaderValue.parse("UTF-8;b =");
    }

    @Test(expected = HeaderValueException.class)
    public void testCharsetSpaceTabSpaceTabEqualsFails() {
        CharsetHeaderValue.parse("UTF-8;b \t \t=");
    }

    @Test(expected = HeaderValueException.class)
    public void testCharsetEqualsSpaceFails() {
        CharsetHeaderValue.parse("UTF-8;b= ");
    }

    @Test(expected = HeaderValueException.class)
    public void testCharsetEqualsTabFails() {
        CharsetHeaderValue.parse("UTF-8;b=\t");
    }

    @Test
    public void testCharsetParameterSeparatorSeparatorFails() {
        this.parseFails("UTF-8;;", 6);
    }

    @Test
    public void testCharset2() {
        this.parseAndCheck("utf-16", "utf-16");
    }

    @Test
    public void testCharsetSpace() {
        this.parseAndCheck("utf-8 ", "utf-8");
    }

    @Test
    public void testCharsetTab() {
        this.parseAndCheck("utf-8\t", "utf-8");
    }

    @Test
    public void testCharsetSpaceTabSpaceTab() {
        this.parseAndCheck("UTF-8 \t \t", "UTF-8");
    }

    @Test
    public void testCharsetSeparator() {
        this.parseAndCheck("UTF-8;", "UTF-8");
    }

    @Test
    public void testCharsetSeparatorSpace() {
        this.parseAndCheck("UTF-8; ", "UTF-8");
    }

    @Test
    public void testCharsetSeparatorTab() {
        this.parseAndCheck("UTF-8;\t", "UTF-8");
    }

    @Test
    public void testCharsetParameterSeparatorFails() {
        final String text = "UTF-8;=";
        this.parseFails(text,
                HeaderParser.emptyToken(HeaderParser.PARAMETER_NAME, 6, text));
    }

    @Test
    public void testCharsetParameterNameInvalidCharFails() {
        this.parseFails("UTF-8;b>=c", '>');
    }

    @Test
    public void testCharsetParameterNameSpaceInvalidCharFails() {
        this.parseFails("UTF-8;b >=c", '>');
    }

    @Test
    public void testCharsetParameterNameTabInvalidCharFails() {
        this.parseFails("UTF-8;b\t>=c", '>');
    }

    @Test
    public void testCharsetParameterNameEqualsInvalidCharFails() {
        this.parseFails("UTF-8;b=\0c", '\0');
    }

    @Test
    public void testCharsetSubTypeSeparator() {
        this.parseAndCheck("UTF-8,",
                this.charsetHeaderValue("utf-8"));
    }

    @Test
    public void testCharsetParameterNameEqualsParameterValue() {
        this.parseAndCheck("UTF-8;b=c",
                "UTF-8",
                "b", "c");
    }

    @Test
    public void testCharsetParameterNameSpaceEqualsParameterValue() {
        this.parseAndCheck("UTF-8;b =c",
                "UTF-8",
                "b", "c");
    }

    @Test
    public void testCharsetParameterNameTabEqualsParameterValue() {
        this.parseAndCheck("UTF-8;b\t=c",
                "UTF-8",
                "b", "c");
    }

    @Test
    public void testCharsetParameterNameSpaceTabSpaceTabEqualsParameterValue() {
        this.parseAndCheck("UTF-8;b \t \t=c",
                "UTF-8",
                "b", "c");
    }

    @Test
    public void testCharsetParameterNameEqualsSpaceParameterValue() {
        this.parseAndCheck("UTF-8;b= c",
                "UTF-8",
                "b", "c");
    }

    @Test
    public void testCharsetParameterNameEqualsTabParameterValue() {
        this.parseAndCheck("UTF-8;b=\tc",
                "UTF-8",
                "b", "c");
    }

    @Test
    public void testCharsetParameterNameEqualsSpaceTabSpaceTabParameterValue() {
        this.parseAndCheck("UTF-8;b= \t \tc",
                "UTF-8",
                "b", "c");
    }

    @Test
    public void testCharsetParameterValueInvalidCharFails() {
        this.parseFails("UTF-8;b=c>", '>');
    }

    @Test
    public void testCharsetParameterValueSpaceInvalidCharFails() {
        this.parseFails("UTF-8;b=c Q", 'Q');
    }

    @Test
    public void testCharsetParameterValueSpaceInvalidCharFails2() {
        this.parseFails("UTF-8;b=c >", '>');
    }

    @Test
    public void testCharsetParameterSpace() {
        this.parseAndCheck("UTF-8;bcd=123 ",
                "UTF-8",
                "bcd", "123");
    }

    @Test
    public void testCharsetParameterSeparator() {
        this.parseAndCheck("UTF-8;bcd=123;",
                "UTF-8",
                "bcd", "123");
    }

    @Test
    public void testCharsetParameterTab() {
        this.parseAndCheck("UTF-8;bcd=123\t",
                "UTF-8",
                "bcd", "123");
    }

    @Test
    public void testCharsetParameterSpaceTabSpaceTab() {
        this.parseAndCheck("UTF-8;bcd=123 \t \t",
                "UTF-8",
                "bcd", "123");
    }

    @Test
    public void testCharsetParameterSeparatorSpaceParameter() {
        this.parseAndCheck("UTF-8; b=c",
                "UTF-8",
                "b", "c");
    }

    @Test
    public void testCharsetParameterSeparatorTabParameter() {
        this.parseAndCheck("UTF-8;\tb=c",
                "UTF-8",
                "b", "c");
    }

    @Test
    public void testCharsetSpaceParameterSeparatorParameter() {
        this.parseAndCheck("UTF-8 ;b=c",
                "UTF-8",
                "b", "c");
    }

    @Test
    public void testCharsetSpaceParameterSeparatorSpaceParameter() {
        this.parseAndCheck("UTF-8 ; b=c",
                "UTF-8",
                "b", "c");
    }

    @Test
    public void testCharsetTabParameterSeparatorTabParameter() {
        this.parseAndCheck("UTF-8\t;\tb=c",
                "UTF-8",
                "b", "c");
    }

    @Test
    public void testCharsetSpaceTabSpaceTabParameterSeparatorSpaceTabParameter() {
        this.parseAndCheck("UTF-8 \t \t; \t \tb=c",
                "UTF-8",
                "b", "c");
    }

    @Test
    public void testCharsetParameterSeparatorParameterNameFails() {
        final String text = "UTF-8;b=c;D";
        this.parseFails(text,
                HeaderParser.emptyToken(HeaderParser.PARAMETER_VALUE,
                        10,
                        text));
    }

    @Test
    public void testCharsetParameter() {
        this.parseAndCheck("utf-8;p1=v1;",
                "utf-8",
                "p1", "v1");
    }

    @Test
    public void testCharsetParameterSeparatorParameter() {
        this.parseAndCheck("utf-8;p1=v1;p2=v2",
                "utf-8",
                "p1", "v1",
                "p2", "v2");
    }

    @Test
    public void testCharsetParameterWhitespaceSeparatorParameter() {
        this.parseAndCheck("utf-8;p1=v1 ;p2=v2",
                "utf-8",
                "p1", "v1",
                "p2", "v2");
    }

    @Test
    public void testCharsetParameterParameterSeparatorWhitespaceParameter() {
        this.parseAndCheck("utf-8;p1=v1; p2=v2",
                "utf-8",
                "p1", "v1",
                "p2", "v2");
    }

    @Test
    public void testCharsetSeparatorCharset() {
        this.parseAndCheck("utf-8,utf-16;",
                this.charsetHeaderValue("utf-8"),
                this.charsetHeaderValue("utf-16"));
    }

    // q............................................................................................

    @Test
    public void testQInvalidValueFails() {
        this.parseFails("utf-8; q=X",
                "Failed to convert \"q\" value \"X\", message: For input string: \"X\"");
    }

    @Test
    public void testQ() {
        this.parseAndCheck("utf-8; q=0.75",
                "utf-8",
                "q",
                0.75f);
    }

    @Test
    public void testCharsetSeparatorCharsetQSorted() {
        this.parseAndCheck("utf-8; q=0.5, utf-16; q=0.75",
                this.charsetHeaderValue("utf-16", "q", 0.75f),
                this.charsetHeaderValue("utf-8", "q", 0.5f));
    }

    // helpers...................................................................................................

    @Override
    List<CharsetHeaderValue> parse(final String text) {
        return CharsetHeaderValueListHeaderParser.parseCharsetHeaderValueList(text);
    }

    private void parseAndCheck(final String headerValue, final String charset) {
        this.parseAndCheck(headerValue, charset, CharsetHeaderValue.NO_PARAMETERS);
    }

    private void parseAndCheck(final String headerValue,
                               final String charset,
                               final String parameterName, final Object parameterValue) {
        this.parseAndCheck(headerValue, charset, this.parameters(parameterName, parameterValue));
    }

    private void parseAndCheck(final String headerValue,
                               final String charset,
                               final String parameterName1, final Object parameterValue1,
                               final String parameterName2, final Object parameterValue2) {
        this.parseAndCheck(headerValue,
                charset,
                parameters(parameterName1, parameterValue1, parameterName2, parameterValue2));
    }

    private void parseAndCheck(final String headerValue,
                               final String charset,
                               final Map<CharsetHeaderValueParameterName<?>, Object> parameters) {
        this.parseAndCheck(headerValue,
                CharsetHeaderValue.with(CharsetName.with(charset)).setParameters(parameters));
    }

    private void parseAndCheck(final String headerValue, final CharsetHeaderValue... values) {
        this.parseAndCheck(headerValue, Lists.of(values));
    }

    private CharsetHeaderValue charsetHeaderValue(final String charset) {
        return CharsetHeaderValue.with(CharsetName.with(charset));
    }

    private CharsetHeaderValue charsetHeaderValue(final String charset,
                                                  final String parameterName,
                                                  final Object parameterValue) {
        return CharsetHeaderValue.with(CharsetName.with(charset))
                .setParameters(this.parameters(parameterName, parameterValue));
    }

    private Map<CharsetHeaderValueParameterName<?>, Object> parameters(final String name,
                                                                       final Object value) {
        return this.parameters(CharsetHeaderValueParameterName.with(name), value);
    }

    private Map<CharsetHeaderValueParameterName<?>, Object> parameters(final CharsetHeaderValueParameterName<?> name,
                                                                       final Object value) {
        return Maps.one(name, value);
    }

    private Map<CharsetHeaderValueParameterName<?>, Object> parameters(
            final String parameterName1, final Object parameterValue1,
            final String parameterName2, final Object parameterValue2) {
        final Map<CharsetHeaderValueParameterName<?>, Object> parameters = Maps.ordered();
        parameters.put(CharsetHeaderValueParameterName.with(parameterName1), parameterValue1);
        parameters.put(CharsetHeaderValueParameterName.with(parameterName2), parameterValue2);
        return parameters;
    }

    @Override
    CharsetHeaderValueListHeaderParser createHeaderParser(final String text) {
        return new CharsetHeaderValueListHeaderParser(text);
    }

    @Override
    protected Class<CharsetHeaderValueListHeaderParser> type() {
        return CharsetHeaderValueListHeaderParser.class;
    }
}