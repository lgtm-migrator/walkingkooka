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

import org.junit.Test;
import walkingkooka.Cast;
import walkingkooka.naming.StringName;
import walkingkooka.tree.expression.ExpressionNode;
import walkingkooka.tree.expression.ExpressionNodeName;

import static org.junit.Assert.assertEquals;


final public class ExpressionNodeSelectorTest extends
        NonLogicalNodeSelectorTestCase<ExpressionNodeSelector<TestFakeNode, StringName, StringName, Object>> {

    @Test(expected = NullPointerException.class)
    public void testWithNullPredicateFails() {
        ExpressionNodeSelector.with(null);
    }

    @Test
    public void testSelfSelected() {
        final TestFakeNode self = TestFakeNode.node("self");
        this.acceptAndCheck(self, self);
    }

    @Test
    public void testIgnoresNonSelfNodes() {
        final TestFakeNode siblingBefore = TestFakeNode.node("siblingBefore");
        final TestFakeNode self = TestFakeNode.node("self", TestFakeNode.node("child"));
        final TestFakeNode siblingAfter = TestFakeNode.node("siblingAfter");
        final TestFakeNode parent = TestFakeNode.node("parent", siblingBefore, self, siblingAfter);

        this.acceptAndCheck(parent.child(1), self);
    }

    @Test
    public void testToString() {
        assertEquals("*[" + expression() + "]", this.createSelector().toString());
    }

    @Override
    protected ExpressionNodeSelector<TestFakeNode, StringName, StringName, Object> createSelector() {
        return ExpressionNodeSelector.with(expression());
    }

    private ExpressionNode expression() {
        return ExpressionNode.equalsNode(
                ExpressionNode.function(ExpressionNodeName.with("name"), ExpressionNode.NO_CHILDREN),
                ExpressionNode.text("self")
        );
    }

    @Override
    protected Class<ExpressionNodeSelector<TestFakeNode, StringName, StringName, Object>> type() {
        return Cast.to(ExpressionNodeSelector.class);
    }
}
