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

package walkingkooka.convert;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class FailConverterTest extends ConverterTestCase<FailConverter<String, Integer>>{

    @Test
    public void testWithNullSourceTypeFails() {
        assertThrows(NullPointerException.class, () -> {
            FailConverter.with(null, Integer.class);
        });
    }

    @Test
    public void testWithNullTargetTypeFails() {
        assertThrows(NullPointerException.class, () -> {
            FailConverter.with(String.class, null);
        });
    }

    @Test
    public void testConvertFails() {
        this.convertFails("*FAILS*", Integer.class);
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createConverter(), "String->Integer");
    }

    @Override
    protected FailConverter<String, Integer> createConverter() {
        return FailConverter.with(String.class, Integer.class);
    }

    @Override
    protected ConverterContext createContext() {
        return ConverterContexts.fake();
    }

    @Override
    public Class<FailConverter<String, Integer>> type() {
        return Cast.to(FailConverter.class);
    }
}
