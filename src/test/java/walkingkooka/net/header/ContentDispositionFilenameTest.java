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
import walkingkooka.test.PublicClassTestCase;

import static org.junit.Assert.assertEquals;

public final class ContentDispositionFilenameTest extends PublicClassTestCase<ContentDispositionFilename> {

    @Test(expected = NullPointerException.class)
    public void testWithNullFails() {
        ContentDispositionFilename.with(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithEmptyFails() {
        ContentDispositionFilename.with("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWithInvalidCharFails() {
        ContentDispositionFilename.with("\0");
    }

    @Test
    public void testWith() {
        final String text = "filename123";
        final ContentDispositionFilename filename = ContentDispositionFilename.with(text);
        assertEquals("value", text, filename.value());
    }

    @Test
    public void testToString() {
        final String filename = "filename123";
        assertEquals(filename, ContentDispositionFilename.with(filename).toString());
    }

    @Override
    protected Class<ContentDispositionFilename> type() {
        return ContentDispositionFilename.class;
    }
}