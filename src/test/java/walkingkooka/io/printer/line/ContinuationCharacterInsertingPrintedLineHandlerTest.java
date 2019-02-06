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

package walkingkooka.io.printer.line;

import org.junit.jupiter.api.Test;
import walkingkooka.text.LineEnding;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final public class ContinuationCharacterInsertingPrintedLineHandlerTest
        extends PrintedLineHandlerTestCase<ContinuationCharacterInsertingPrintedLineHandler> {

    // constants

    private final static int WIDTH = 5;

    private final static char CONTINUATION = '*';

    // tests

    @Test
    public void testWithInvalidWidthFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            ContinuationCharacterInsertingPrintedLineHandler.with(0, CONTINUATION);
        });
    }

    @Test
    public void testWithCarriageReturnContinuationCharacterFails() {
        final IllegalArgumentException expected = assertThrows(IllegalArgumentException.class, () -> {
            ContinuationCharacterInsertingPrintedLineHandler.with(WIDTH, '\r');
        });
        assertEquals(
                ContinuationCharacterInsertingPrintedLineHandler.mustNotBeEndOfLineCharacter('\r'),
                expected.getMessage(),
                "message");
    }

    @Test
    public void testWithNewLineContinuationCharacterFails() {
        final IllegalArgumentException expected = assertThrows(IllegalArgumentException.class, () -> {
            ContinuationCharacterInsertingPrintedLineHandler.with(WIDTH, '\n');
        });
        assertEquals(ContinuationCharacterInsertingPrintedLineHandler.mustNotBeEndOfLineCharacter('\n'),
                expected.getMessage(),
                "message");
    }

    @Test
    public void testNotSplitBecauseNotLongEnough() {
        this.linePrintedAndCheck(10, "line", LineEnding.CR, "line@");
    }

    @Test
    public void testExactCr() {
        this.linePrintedAndCheck(3, "123", LineEnding.CR, "123@");
    }

    @Test
    public void testExactNl() {
        this.linePrintedAndCheck(3, "123", LineEnding.NL, "123@");
    }

    @Test
    public void testAlmost() {
        this.linePrintedAndCheck(3, "12", LineEnding.CR, "12@");
    }

    @Test
    public void testBrokenUpIntoTwoLinesCr() {
        this.linePrintedAndCheck(6, "12345678", LineEnding.CR, "12345*@678@");
    }

    @Test
    public void testBrokenUpIntoTwoLinesNl() {
        this.linePrintedAndCheck(6, "12345678", LineEnding.NL, "12345*@678@");
    }

    @Test
    public void testBrokenUpIntoTwoLinesCrNl() {
        this.linePrintedAndCheck(6, "12345678", LineEnding.CRNL, "12345*@678@");
    }

    @Test
    public void testBrokenUpIntoManyLinesCr() {
        this.linePrintedAndCheck(4, "123456789", LineEnding.CR, "123*@456*@789@");
    }

    @Test
    public void testBrokenUpIntoManyLinesNl() {
        this.linePrintedAndCheck(4, "123456789", LineEnding.NL, "123*@456*@789@");
    }

    @Test
    public void testBrokenUpIntoManyLinesCrNl() {
        this.linePrintedAndCheck(4, "123456789", LineEnding.CRNL, "123*@456*@789@");
    }

    @Test
    public void testBrokenUpIntoManyLinesCr2() {
        this.linePrintedAndCheck(4, "12345678", LineEnding.CR, "123*@456*@78@");
    }

    @Test
    public void testBrokenUpIntoManyLinesNl2() {
        this.linePrintedAndCheck(4, "12345678", LineEnding.NL, "123*@456*@78@");
    }

    @Test
    public void testBrokenUpIntoManyLinesCrNl2() {
        this.linePrintedAndCheck(4, "12345678", LineEnding.CRNL, "123*@456*@78@");
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createLineHandler(),
                "lines > " + WIDTH
                        + " continue w/ '"
                        + CONTINUATION + "'");
    }

    @Override
    protected ContinuationCharacterInsertingPrintedLineHandler createLineHandler() {
        return ContinuationCharacterInsertingPrintedLineHandler.with(
                WIDTH,
                CONTINUATION);
    }

    private void linePrintedAndCheck(final int width, final CharSequence line,
                                     final LineEnding lineEnding, final String expected) {
        this.linePrintedAndCheck(//
                ContinuationCharacterInsertingPrintedLineHandler.with(width,
                        CONTINUATION), //
                replacePlaceHolder(line,
                        lineEnding), //
                lineEnding, //
                replacePlaceHolder(expected,
                        lineEnding), //
                null);
    }

    private static String replacePlaceHolder(final CharSequence line, final LineEnding lineEnding) {
        return line.toString().replace("@", lineEnding);
    }

    @Override
    public Class<ContinuationCharacterInsertingPrintedLineHandler> type() {
        return ContinuationCharacterInsertingPrintedLineHandler.class;
    }
}
