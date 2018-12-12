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
import walkingkooka.predicate.character.CharPredicates;
import walkingkooka.test.PackagePrivateClassTestCase;

public final class StringHeaderValueConverterTest extends
        PackagePrivateClassTestCase<StringHeaderValueConverter> {

    private final static String TEXT = "abc123";

    @Test(expected = NullPointerException.class)
    public void testWithNullPredicateFails() {
        StringHeaderValueConverter.with(null);
    }

    @Test(expected = NullPointerException.class)
    public void testWithNullFeaturesFails() {
        StringHeaderValueConverter.with(CharPredicates.fake(), (StringHeaderValueConverterFeature[]) null);
    }

    @Override
    protected Class<StringHeaderValueConverter> type() {
        return StringHeaderValueConverter.class;
    }
}
