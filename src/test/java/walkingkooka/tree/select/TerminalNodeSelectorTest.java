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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.naming.StringName;
import walkingkooka.tree.TestNode;

final public class TerminalNodeSelectorTest
        extends NodeSelectorTestCase4<TerminalNodeSelector<TestNode, StringName, StringName, Object>> {

    @Test
    public void testMap() {
        final TestNode parent = TestNode.with("parent",
                TestNode.with("child1"), TestNode.with("child2"), TestNode.with("child3"));

        TestNode.clear();

        this.acceptMapAndCheck(parent,
                TestNode.with("parent*0",
                        TestNode.with("child1"), TestNode.with("child2"), TestNode.with("child3")));
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createSelector(), "");
    }

    @Override
    TerminalNodeSelector<TestNode, StringName, StringName, Object> createSelector() {
        return TerminalNodeSelector.get();
    }

    @Override
    public Class<TerminalNodeSelector<TestNode, StringName, StringName, Object>> type() {
        return Cast.to(TerminalNodeSelector.class);
    }
}
