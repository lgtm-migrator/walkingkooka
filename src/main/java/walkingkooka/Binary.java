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

package walkingkooka;

import walkingkooka.compare.Range;
import walkingkooka.test.HashCodeEqualsDefined;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.zip.GZIPOutputStream;

/**
 * A {@link Value} that holds a byte array.
 */
public final class Binary implements HashCodeEqualsDefined, Value<byte[]> {

    /**
     * A {@link Binary} with zero bytes.
     */
    public final static Binary EMPTY = new Binary(new byte[0]);

    public static Binary with(final byte[] value) {
        Objects.requireNonNull(value, "value");

        return value.length == 0 ? EMPTY : new Binary(value.clone());
    }

    private Binary(final byte[] value) {
        super();
        this.value = value;
    }

    @Override
    public byte[] value() {
        return value.clone();
    }

    // BinaryRangeVisitor
    final byte[] value;

    /**
     * The size or number of bytes in this {@link Binary}
     */
    public int size() {
        return this.value.length;
    }

    /**
     * Extracts a {@link Binary} that matches the given {@link Range}.
     */
    public Binary extract(final Range<Long> range) {
        return BinaryRangeVisitor.extract(this, range);
    }

    /**
     * Extracts and returns a {@link Binary} that matches the given bounds, returning this if possible.
     */
    // BinaryRangeVisitor
    Binary extract0(final int lower, final int upper, final Range<Long> range) {
        final int size = this.size();
        if (lower < 0 || upper > size) {
            throw new IllegalArgumentException("Range out of bounds " + range + " for binary with size: " + size);
        }
        return 0 == lower && size == upper ?
                this :
                new Binary(Arrays.copyOfRange(this.value, lower, upper));
    }

    /**
     * Returns a {@link Binary} with its contents gzipped.
     */
    public Binary gzip() throws IOException {
        try (final ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            try (final GZIPOutputStream gzip = new GZIPOutputStream(outputStream)) {
                gzip.write(this.value);
                gzip.flush();
            }
            return new Binary(outputStream.toByteArray());
        }
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.value);
    }

    public boolean equals(final Object other) {
        return this == other || other instanceof Binary && this.equals0(Binary.class.cast(other));
    }

    private boolean equals0(final Binary other) {
        return Arrays.equals(this.value, other.value);
    }

    @Override
    public String toString() {
        return Arrays.toString(this.value);
    }
}