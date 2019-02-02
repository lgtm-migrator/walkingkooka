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

package walkingkooka.text.cursor.parser.spreadsheet;

import org.junit.jupiter.api.Test;
import walkingkooka.compare.ComparableTesting;
import walkingkooka.compare.LowerOrUpperTesting;
import walkingkooka.test.ClassTestCase;
import walkingkooka.tree.json.HasJsonNodeTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.type.MemberVisibility;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class SpreadsheetColumnOrRowReferenceTestCase<V extends SpreadsheetColumnOrRowReference<V>> extends ClassTestCase<V>
        implements ComparableTesting<V>,
        LowerOrUpperTesting<V>,
        HasJsonNodeTesting<V> {

    final static int VALUE = 123;
    final static SpreadsheetReferenceKind REFERENCE_KIND = SpreadsheetReferenceKind.ABSOLUTE;

    @Test
    public final void testWithNegativeValueFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.createReference(-1, SpreadsheetReferenceKind.RELATIVE);
        });
    }

    @Test
    public final void testWithInvalidValueFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.createReference(this.maxValue(), SpreadsheetReferenceKind.RELATIVE);
        });
    }

    @Test
    public final void testWithNullKindFails() {
        assertThrows(NullPointerException.class, () -> {
            this.createReference(0, null);
        });
    }

    @Test
    public final void testWithAbsolute() {
        this.withAndCacheCheck(0, SpreadsheetReferenceKind.RELATIVE);
    }

    @Test
    public final void testWithAbsolute2() {
        this.withAndCacheCheck(SpreadsheetColumnOrRowReference.CACHE_SIZE-1, SpreadsheetReferenceKind.RELATIVE);
    }

    @Test
    public final void testWithRelative() {
        this.withAndCacheCheck(0, SpreadsheetReferenceKind.RELATIVE);
    }

    @Test
    public final void testWithRelative2() {
        this.withAndCacheCheck(SpreadsheetColumnOrRowReference.CACHE_SIZE-1, SpreadsheetReferenceKind.RELATIVE);
    }

    private void withAndCacheCheck(final int value, final SpreadsheetReferenceKind kind) {
        final V reference = this.createReference(value, kind);
        this.checkValue(reference, value);
        this.checkKind(reference, kind);

        assertSame(reference, this.createReference(value, kind));
    }

    @Test
    public final void testWithNotCached() {
        final int value = SpreadsheetColumnOrRowReference.CACHE_SIZE;
        final V reference = this.createReference(value);
        this.checkValue(reference, value);
        this.checkKind(reference, REFERENCE_KIND);

        assertNotSame(value,this.createReference(value));
    }

    @Test
    public final void testSetValueInvalidFails() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.createReference().setValue(-1);
        });
    }

    @Test
    public final void testSetValueSame() {
        final V reference = this.createReference();
        assertSame(reference, reference.setValue(VALUE));
    }

    @Test
    public final void testSetValueDifferent() {
        final V reference = this.createReference();
        final Integer differentValue = 999;
        final SpreadsheetColumnOrRowReference<?> different = reference.setValue(differentValue);
        assertNotSame(reference, different);
        this.checkValue(different, differentValue);
        this.checkKind(different, REFERENCE_KIND);
    }

    @Test
    public final void testSetValueDifferent2() {
        final SpreadsheetReferenceKind kind = SpreadsheetReferenceKind.RELATIVE;
        final V reference = this.createReference(VALUE, kind);
        final Integer differentValue = 999;
        final SpreadsheetColumnOrRowReference<?> different = reference.setValue(differentValue);
        assertNotSame(reference, different);
        this.checkValue(different, differentValue);
        this.checkKind(different, kind);
        this.checkType(different);
    }

    @Test
    public final void testAddZero() {
        final V reference = this.createReference();
        assertSame(reference, reference.add(0));
    }

    @Test
    public final void testAddNonZeroPositive() {
        final V reference = this.createReference();
        final SpreadsheetColumnOrRowReference<?> different = reference.add(100);
        assertNotSame(reference, different);
        this.checkValue(different, VALUE + 100);
        this.checkType(different);
    }

    @Test
    public final void testAddNonZeroNegative() {
        final V reference = this.createReference();
        final SpreadsheetColumnOrRowReference<?> different = reference.add(-100);
        assertNotSame(reference, different);
        this.checkValue(different, VALUE - 100);
        this.checkKind(different, SpreadsheetReferenceKind.ABSOLUTE);
        this.checkType(different);
    }

    // lower..........................................................................................

    @Test
    public void testLowerOtherLess() {
        final V reference = this.createReference();
        final V lower = this.createReference(VALUE - 99);
        assertSame(lower, reference.lower(lower));
        assertSame(lower, lower.lower(reference));
    }

    @Test
    public void testLowerOtherGreater() {
        final V reference = this.createReference();
        final V higher = this.createReference(VALUE + 99);
        assertSame(reference, reference.lower(higher));
        assertSame(reference, higher.lower(reference));
    }

    // upper..........................................................................................

    @Test
    public void testUpperOtherLess() {
        final V reference = this.createReference();
        final V lower = this.createReference(VALUE - 99);
        assertSame(reference, reference.upper(lower));
        assertSame(reference, lower.upper(reference));
    }

    @Test
    public void testUpperOtherGreater() {
        final V reference = this.createReference();
        final V higher = this.createReference(VALUE + 99);
        assertSame(higher, reference.upper(higher));
        assertSame(higher, higher.upper(reference));
    }

    // HasJsonNode.......................................................................................

    @Test
    public final void testToJsonNode() {
        final V reference = this.createReference();
        this.toJsonNodeAndCheck(reference, JsonNode.string(reference.toString()));
    }

    // helper......................................................................................

    final V createReference() {
        return this.createReference(VALUE, REFERENCE_KIND);
    }

    final V createReference(final int value) {
        return this.createReference(value, REFERENCE_KIND);
    }

    abstract V createReference(final int value, final SpreadsheetReferenceKind kind);

    private void checkValue(final SpreadsheetColumnOrRowReference<?> reference, final Integer value) {
        assertEquals(value, reference.value(), "value");
    }

    private void checkKind(final SpreadsheetColumnOrRowReference<?> reference, final SpreadsheetReferenceKind kind) {
        assertSame(kind, reference.referenceKind(), "referenceKind");
    }

    private void checkType(final SpreadsheetColumnOrRowReference<?> reference) {
        assertEquals(this.type(), reference.getClass(), "same type");
    }

    final void checkToString(final int value, final SpreadsheetReferenceKind kind, final String toString) {
        assertEquals(toString, this.createReference(value, kind).toString());
    }

    abstract int maxValue();

    @Override
    protected final MemberVisibility typeVisibility() {
        return MemberVisibility.PUBLIC;
    }

    @Override
    public final V createComparable() {
        return this.createReference();
    }

    @Override
    public final boolean compareAndEqualsMatch() {
        return false;
    }

    @Override
    public final V createLowerOrUpper() {
        return this.createComparable();
    }
}
