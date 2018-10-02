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

package walkingkooka.text.spreadsheetformat;

import org.junit.Test;
import walkingkooka.Cast;
import walkingkooka.color.Color;
import walkingkooka.text.CharSequences;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public final class LocalDateTimeSpreadsheetTextFormatterTest extends SpreadsheetTextFormatterTestCase<LocalDateTimeSpreadsheetTextFormatter, LocalDateTime> {

    private final static String GENERAL_FORMATTED = "GeneralFormatted";

    @Test(expected = NullPointerException.class)
    public void testWithNullGeneralTextFormatterFails() {
        LocalDateTimeSpreadsheetTextFormatter.with("A", null);
    }

    @Test
    public void testWithGeneral() {
        this.parseFormatAndCheck("General", this.text(), GENERAL_FORMATTED);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPatternWithConditionFails() {
        this.createFormatter("[<100]@");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyPatternParseFails() {
        this.createFormatter("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithDecimalPointFails() {
        this.createFormatter(".");
    }

    // year.............................................................................................

    @Test
    public void testYear2000() {
        this.parseFormatAndCheck("y", this.text(), "00");
    }

    @Test
    public void testYear1999() {
        this.parseFormatAndCheck("y", "1999-12-31T15:58:59", "99");
    }

    @Test
    public void testYearYear2000() {
        this.parseFormatAndCheck("yy", this.text(), "00");
    }

    @Test
    public void testYearYear1999() {
        this.parseFormatAndCheck("yy", "1999-12-31T15:58:59", "99");
    }

    @Test
    public void testYearYearYear000() {
        this.parseFormatAndCheck("yyy", this.text(), "2000");
    }

    @Test
    public void testYearYearYear1999() {
        this.parseFormatAndCheck("yyy", "1999-12-31T15:58:59", "1999");
    }

    @Test
    public void testYearYearYearYear2000() {
        this.parseFormatAndCheck("yyyy", this.text(), "2000");
    }

    @Test
    public void testYearYearYearYear1999() {
        this.parseFormatAndCheck("yyyy", "1999-12-31T15:58:59", "1999");
    }

    @Test
    public void testYearYearYearYear789() {
        this.parseFormatAndCheck("yyyy", "0789-12-31T15:58:59", "789");
    }

    // month.............................................................................................

    @Test
    public void testMonthDecember() {
        this.parseFormatAndCheck("m", this.text(), "12");
    }

    @Test
    public void testMonthJanuary() {
        this.parseFormatAndCheck("m", "1999-01-31T15:58:59", "1");
    }

    @Test
    public void testMonthMonthDecember() {
        this.parseFormatAndCheck("mm", this.text(), "12");
    }

    @Test
    public void testMonthMonthJanuary() {
        this.parseFormatAndCheck("mm", "1999-01-31T15:58:59", "01");
    }

    @Test
    public void testMonthMonthMonthDecember() {
        this.parseFormatAndCheckMMM("2000-12-31T15:58:59", 12, "Dec!");
    }

    @Test
    public void testMonthMonthMonthJanuary() {
        this.parseFormatAndCheckMMM("1999-01-31T15:58:59", 1, "Jan!");
    }

    private void parseFormatAndCheckMMM(final String date, final int monthNumber, final String monthName) {
        this.parseFormatAndCheck("mmm",
                date,
                new FakeSpreadsheetTextFormatContext() {
                    @Override
                    public String monthNameAbbreviation(final int m) {
                        assertEquals("month", monthNumber, m);
                        return monthName;
                    }
                },
                monthName);
    }

    @Test
    public void testMonthMonthMonthMonthDecember() {
        this.parseFormatAndCheckMMMM("2000-12-31T15:58:59", 12, "December!");
    }

    @Test
    public void testMonthMonthMonthMonthJanuary() {
        this.parseFormatAndCheckMMMM("1999-01-31T15:58:59", 1, "January!");
    }

    private void parseFormatAndCheckMMMM(final String dateTime, final int monthNumber, final String monthName) {
        this.parseFormatAndCheck("mmmm",
                dateTime,
                new FakeSpreadsheetTextFormatContext() {
                    @Override
                    public String monthName(final int m) {
                        assertEquals("month", monthNumber, m);
                        return monthName;
                    }
                },
                monthName);
    }

    // day.............................................................................................

    @Test
    public void testDay31() {
        this.parseFormatAndCheck("d", this.text(), "31");
    }

    @Test
    public void testDay1() {
        this.parseFormatAndCheck("d", "1999-12-01T15:58:59", "1");
    }

    @Test
    public void testDayDay31() {
        this.parseFormatAndCheck("dd", this.text(), "31");
    }

    @Test
    public void testDayDay1() {
        this.parseFormatAndCheck("dd", "2000-12-01T15:58:59", "01");
    }

    @Test
    public void testDayDayDay31() {
        this.parseFormatAndCheckDDD("2000-12-31T15:58:59", 31, "Mon!");
    }

    @Test
    public void testDayDayDay1() {
        this.parseFormatAndCheckDDD("1999-12-01T15:58:59", 1, "Mon!");
    }

    private void parseFormatAndCheckDDD(final String dateTime, final int dayNumber, final String dayName) {
        this.parseFormatAndCheck("ddd",
                dateTime,
                new FakeSpreadsheetTextFormatContext() {
                    @Override
                    public String weekDayNameAbbreviation(final int d) {
                        assertEquals("day", dayNumber, d);
                        return dayName;
                    }
                },
                dayName);
    }

    @Test
    public void testDayDayDayDay31() {
        this.parseFormatAndCheckDDDD("2000-12-31T15:58:59", 31, "Monday!");
    }

    @Test
    public void testDayDayDayDay1() {
        this.parseFormatAndCheckDDDD("1999-12-01T15:58:59", 1, "Monday!");
    }

    private void parseFormatAndCheckDDDD(final String date, final int dayNumber, final String dayName) {
        this.parseFormatAndCheck("dddd",
                date,
                new FakeSpreadsheetTextFormatContext() {
                    @Override
                    public String weekDayName(final int d) {
                        assertEquals("day", dayNumber, d);
                        return dayName;
                    }
                },
                dayName);
    }

    // day month year.............................................................................................

    @Test
    public void testYearYearMonthDay() {
        this.parseFormatAndCheck("yymd", "1999-12-31T01:58:59", "991231");
    }

    @Test
    public void testMonthDayYearYear() {
        this.parseFormatAndCheck("mdyy", "1999-12-31T01:58:59", "123199");
    }

    // hour.............................................................................................

    @Test
    public void testHour12() {
        this.parseFormatAndCheck("h", "2000-06-29T12:58:59", "12");
    }

    @Test
    public void testHour1() {
        this.parseFormatAndCheck("h", "2000-06-29T01:58:59", "1");
    }

    @Test
    public void testHour15() {
        this.parseFormatAndCheck("h", "2000-06-01T15:58:59", "15");
    }

    @Test
    public void testHour1Ampm() {
        this.parseHourFormatAndCheck("hAM/PM", "2000-06-29T01:58:59", 1, "AM!", "1AM!");
    }

    @Test
    public void testHour12Ampm() {
        this.parseHourFormatAndCheck("hAM/PM", "2000-06-29T12:58:59", 12, "AM!", "12AM!");
    }

    @Test
    public void testHour15Ampm() {
        this.parseHourFormatAndCheck("hAM/PM", "2000-06-01T15:58:59", 15, "PM!", "3PM!");
    }

    @Test
    public void testHour23Ampm() {
        this.parseHourFormatAndCheck("hAM/PM", "2000-06-01T23:58:59", 23, "PM!", "11PM!");
    }

    @Test
    public void testHourHour1Ampm() {
        this.parseHourFormatAndCheck("hhAM/PM", "2000-06-29T01:58:59", 1, "AM!", "01AM!");
    }

    @Test
    public void testHourHour12Ampm() {
        this.parseHourFormatAndCheck("hhAM/PM", "2000-06-29T12:58:59", 12, "AM!", "12AM!");
    }

    @Test
    public void testHourHour15Ampm() {
        this.parseHourFormatAndCheck("hhAM/PM", "2000-06-01T15:58:59", 15, "PM!", "03PM!");
    }

    @Test
    public void testHourHour23Ampm() {
        this.parseHourFormatAndCheck("hhAM/PM", "2000-06-01T23:58:59", 23, "PM!", "11PM!");
    }

    @Test
    public void testHourHourHour1() {
        this.parseFormatAndCheck("hhh", "2000-06-29T01:58:59", "01");
    }

    @Test
    public void testHourHourHour15() {
        this.parseFormatAndCheck("hhh", "2000-06-01T15:58:59", "15");
    }

    @Test
    public void testHourHourHour1Ampm() {
        this.parseHourFormatAndCheck("hhhAM/PM", "2000-06-29T01:58:59", 1, "AM!", "01AM!");
    }

    @Test
    public void testHourHourHour12Ampm() {
        this.parseHourFormatAndCheck("hhhAM/PM", "2000-06-29T12:58:59", 12, "AM!", "12AM!");
    }

    // minute.............................................................................................

    @Test
    public void testHourMinute1() {
        this.parseFormatAndCheck("hm", "2000-06-29T12:01:59", "121");
    }

    @Test
    public void testHourMinute58() {
        this.parseFormatAndCheck("hm", "2000-06-30T12:58:59", "1258");
    }

    @Test
    public void testHourMinuteMinute1() {
        this.parseFormatAndCheck("hmm", "2000-06-29T12:01:59", "1201");
    }

    @Test
    public void testHourMinuteMinute58() {
        this.parseFormatAndCheck("hmm", "2000-06-30T12:58:59", "1258");
    }

    @Test
    public void testHourMinuteMinuteMinute1() {
        this.parseFormatAndCheck("hmmm", "2000-06-29T12:01:59", "1201");
    }

    @Test
    public void testHourMinuteMinuteMinute58() {
        this.parseFormatAndCheck("hmmm", "2000-06-30T12:58:59", "1258");
    }

    // second.............................................................................................

    @Test
    public void testSecond1() {
        this.parseFormatAndCheck("s", "2000-06-29T12:58:01", "1");
    }

    @Test
    public void testSecond59() {
        this.parseFormatAndCheck("s", "2000-06-30T12:58:59", "59");
    }

    @Test
    public void testSecondSecond1() {
        this.parseFormatAndCheck("ss", "2000-06-29T12:58:01", "01");
    }

    @Test
    public void testSecondSecond59() {
        this.parseFormatAndCheck("ss", "2000-06-30T12:58:59", "59");
    }

    @Test
    public void testSecondSecondSecond1() {
        this.parseFormatAndCheck("sss", "2000-06-29T12:58:01", "01");
    }

    @Test
    public void testSecondSecondSecond59() {
        this.parseFormatAndCheck("sss", "2000-06-30T12:58:59", "59");
    }

    // hour minute second.................................................................................

    @Test
    public void testHourHourMinuteMinuteSecondSecond125801() {
        this.parseHourFormatAndCheck("hhmmss", "2000-06-29T12:58:01", 12, "AM!", "125801");
    }

    @Test
    public void testHourHourMinuteMinuteSecondSecondAm125801() {
        this.parseHourFormatAndCheck("hhmmssAM/PM", "2000-06-29T12:58:01", 12, "AM!", "125801AM!");
    }

    @Test
    public void testHourHourMinuteMinuteSecondSecondDayDayMonthMonthYearYearYearYear() {
        this.parseHourFormatAndCheck("hhmmssAM/PMddmmyyyy", "2000-06-29T12:58:01", 12, "AM!", "125801AM!29062000");
    }

    @Test
    public void testHourHourMinuteMinuteSecondSecondDayDayMonthMonthYearYearYearYearAmPm() {
        this.parseHourFormatAndCheck("hhmmssAM/PMddmmyyyy", "2000-06-29T15:58:01", 15, "AM!", "035801AM!29062000");
    }

    private void parseHourFormatAndCheck(final String pattern,
                                         final String dateTime,
                                         final int hour,
                                         final String ampm,
                                         final String text) {
        this.parseFormatAndCheck(pattern,
                dateTime,
                new FakeSpreadsheetTextFormatContext() {
                    @Override
                    public String ampm(final int h) {
                        assertEquals("hour", hour, h);
                        return ampm;
                    }
                },
                text);
    }
    // literals..........................................................................................................

    @Test
    public void testEscaped() {
        this.parseEscapedOrLiteralFormatAndCheck("\\A", "A");
    }

    @Test
    public void testCurrency() {
        this.parseEscapedOrLiteralFormatAndCheck("$");
    }

    @Test
    public void testMinus() {
        this.parseEscapedOrLiteralFormatAndCheck("-");
    }

    @Test
    public void testPlus() {
        this.parseEscapedOrLiteralFormatAndCheck("-");
    }

    @Test
    public void testSlash() {
        this.parseEscapedOrLiteralFormatAndCheck("/");
    }

    @Test
    public void testOpenParens() {
        this.parseEscapedOrLiteralFormatAndCheck("(");
    }

    @Test
    public void testCloseParens() {
        this.parseEscapedOrLiteralFormatAndCheck(")");
    }

    @Test
    public void testColon() {
        this.parseEscapedOrLiteralFormatAndCheck(":");
    }

    @Test
    public void testSpace() {
        this.parseEscapedOrLiteralFormatAndCheck(" ");
    }

    private void parseEscapedOrLiteralFormatAndCheck(final String pattern) {
        this.parseEscapedOrLiteralFormatAndCheck(pattern, pattern);
    }

    private void parseEscapedOrLiteralFormatAndCheck(final String pattern, final String text) {
        this.parseFormatAndCheck(pattern, this.text(), this.createContext(), text);
    }

    @Test
    public void testQuotedText() {
        this.parseFormatAndCheck("\"Hello\"", this.text(), "Hello");
    }

    // color ......................................................................................................

    public void testColorDayMonthYear() {
        final Color red = Color.fromRgb(0x0FF);
        assertEquals(SpreadsheetFormattedText.with(Optional.of(red), "31/12/2000"),
                this.createFormatter("[RED]d/m/y")
                        .format(this.parse("2000-12-31T15:58:59"), new FakeSpreadsheetTextFormatContext() {
                            @Override
                            public Color colorName(final String name) {
                                assertEquals("color name", "RED", name);
                                return red;
                            }
                        }));
    }

    // mixed.......................................................................................................

    @Test
    public void testDaySlashMonthSayYearSpaceHourColonMinuteColonSecond() {
        this.parseFormatAndCheck("d/m/yyyy h:m:s",
                "2000-12-31T15:58:59",
                "31/12/2000 15:58:59");
    }

    @Test
    public void testDaySlashMonthSayYearLiteralTHourColonMinuteColonSecond() {
        this.parseFormatAndCheck("d/m/yyyy\\Th:m:s",
                "2000-12-31T15:58:59",
                "31/12/2000T15:58:59");
    }

    // helpers..........................................................................................................

    private void parseFormatAndCheck(final String pattern,
                                     final String value,
                                     final String text) {
        this.parseFormatAndCheck(pattern, value, this.createContext(), text);
    }

    private void parseFormatAndCheck(final String pattern,
                                     final String value,
                                     final SpreadsheetTextFormatContext context,
                                     final String text) {
        this.parseFormatAndCheck0(pattern, value, context, SpreadsheetFormattedText.with(SpreadsheetFormattedText.WITHOUT_COLOR, text));
    }

    private void parseFormatAndCheck(final String pattern,
                                     final String value,
                                     final SpreadsheetTextFormatContext context,
                                     final String text,
                                     final Color color) {
        this.parseFormatAndCheck0(pattern, value, context, SpreadsheetFormattedText.with(Optional.of(color), text));
    }

    private void parseFormatAndCheck0(final String pattern,
                                      final String value,
                                      final SpreadsheetTextFormatContext context,
                                      final SpreadsheetFormattedText text) {
        assertEquals("Pattern=" + CharSequences.quote(pattern) + " dateTime=" + value,
                Optional.of(text),
                this.createFormatter(pattern).format(this.parse(value), context));
    }


    @Override
    protected LocalDateTimeSpreadsheetTextFormatter createFormatter() {
        return Cast.to(this.createFormatter("YYYYMMDDHHMMSS"));
    }

    private SpreadsheetTextFormatter<LocalDateTime> createFormatter(final String pattern) {
        return LocalDateTimeSpreadsheetTextFormatter.with(pattern, generalTextFormatter());
    }

    @Override
    protected LocalDateTime value() {
        return this.parse(this.text());
    }

    private String text() {
        return "2000-12-31T15:58:59";
    }

    private LocalDateTime parse(final String value) {
        return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    private SpreadsheetTextFormatter<LocalDateTime> generalTextFormatter() {
        return (dateTime, context) -> Optional.of(general());
    }

    private SpreadsheetFormattedText general() {
        return SpreadsheetFormattedText.with(SpreadsheetFormattedText.WITHOUT_COLOR, GENERAL_FORMATTED);
    }

    @Override
    protected SpreadsheetTextFormatContext createContext() {
        return SpreadsheetTextFormatContexts.fake();
    }

    @Override
    protected Class<LocalDateTimeSpreadsheetTextFormatter> type() {
        return LocalDateTimeSpreadsheetTextFormatter.class;
    }
}
