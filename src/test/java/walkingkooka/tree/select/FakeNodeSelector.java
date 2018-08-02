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

package walkingkooka.tree.select;

import walkingkooka.naming.StringName;
import walkingkooka.test.Fake;

import java.util.Set;
import java.util.function.Consumer;

class FakeNodeSelector extends NodeSelector<TestFakeNode, StringName, StringName, Object> implements Fake {

    @Override
    NodeSelector<TestFakeNode, StringName, StringName, Object> append0(final NodeSelector<TestFakeNode, StringName, StringName, Object> selector){
        throw new UnsupportedOperationException();
    }

    @Override public Set<TestFakeNode> accept(final TestFakeNode node, final Consumer<TestFakeNode> observer) {
        throw new UnsupportedOperationException();
    }

    @Override
    void accept0(final TestFakeNode node, final NodeSelectorContext<TestFakeNode, StringName, StringName, Object> context) {
        throw new UnsupportedOperationException();
    }

    @Override
    void match(TestFakeNode node, NodeSelectorContext<TestFakeNode, StringName, StringName, Object> context) {
        throw new UnsupportedOperationException();
    }

    @Override
    void toString0(final StringBuilder b, final String separator){
        b.append(separator).append("Fake");
    }

    @Override
    void toStringNext(final StringBuilder b, final String separator) {
        throw new UnsupportedOperationException();
    }
}
