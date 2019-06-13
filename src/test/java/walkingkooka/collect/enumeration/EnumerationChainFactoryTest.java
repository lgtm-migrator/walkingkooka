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

package walkingkooka.collect.enumeration;

import walkingkooka.Cast;
import walkingkooka.build.chain.ChainFactoryTesting;
import walkingkooka.test.ClassTesting2;
import walkingkooka.type.JavaVisibility;

import java.util.Enumeration;

final public class EnumerationChainFactoryTest implements ClassTesting2<EnumerationChainFactory<Object>>,
        ChainFactoryTesting<EnumerationChainFactory<Object>, Enumeration<Object>> {

    @Override
    public EnumerationChainFactory<Object> createFactory() {
        return EnumerationChainFactory.instance();
    }

    @Override
    public Class<EnumerationChainFactory<Object>> type() {
        return Cast.to(EnumerationChainFactory.class);
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }
}
