/*
 * Copyright 2019 Miroslav Pokorny (github.com/mP1)
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

package walkingkooka.tree;

import walkingkooka.reflect.JavaVisibility;
import walkingkooka.test.StandardThrowableTesting;

public final class NodeExceptionTest implements StandardThrowableTesting<NodeException> {

    @Override
    public NodeException createThrowable(final String message) {
        return new NodeException(message);
    }

    @Override
    public NodeException createThrowable(final String message, final Throwable cause) {
        return new NodeException(message, cause);
    }
    
    @Override
    public Class<NodeException> type() {
        return NodeException.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
