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
import walkingkooka.naming.Names;
import walkingkooka.naming.StringName;
import walkingkooka.tree.TestNode;
import walkingkooka.tree.expression.ExpressionNode;
import walkingkooka.tree.expression.ExpressionNodeName;

import static org.junit.jupiter.api.Assertions.assertThrows;


final public class ExpressionNodeSelectorTest extends
        NonTerminalNodeSelectorTestCase<ExpressionNodeSelector<TestNode, StringName, StringName, Object>> {

    @Test
    public void testWithNullPredicateFails() {
        assertThrows(NullPointerException.class, () -> {
            ExpressionNodeSelector.with(null);
        });
    }

    @Test
    public void testAppendExpressionTrue() {
        final NodeSelector<TestNode, StringName, StringName, Object> selector = TestNode.relativeNodeSelector()
                .children()
                .expression(ExpressionNode.booleanNode(true));
        this.checkEquals(selector, TestNode.relativeNodeSelector()
                .children()
                .setToString("child::*[true()]"));
    }

    @Test
    public void testExpressionSelfSelected() {
        final TestNode self = TestNode.with("self");
        this.acceptAndCheck(self, self);
    }

    @Test
    public void testExpressionIgnoresNonSelfNodes() {
        final TestNode siblingBefore = TestNode.with("siblingBefore");
        final TestNode self = TestNode.with("self", TestNode.with("child"));
        final TestNode siblingAfter = TestNode.with("siblingAfter");
        final TestNode parent = TestNode.with("parent", siblingBefore, self, siblingAfter);

        this.acceptAndCheck(parent.child(1), self);
    }

    @Test
    public void testExpressionFunctionBooleanChildrenSelectorAppended() {
        final TestNode child1 = TestNode.with("child1");
        final TestNode child2 = TestNode.with("child2");
        final TestNode parent = TestNode.with("self", child1, child2);

        this.acceptAndCheck(ExpressionNodeSelector.<TestNode, StringName, StringName, Object>with(ExpressionNode.booleanNode(true)).children(),
                parent,
                child1, child2);
    }

    @Test
    public void testChildrenExpressionNumberPosition1() {
        this.childrenExpressionNumberPositionAndCheck(ExpressionNode.longNode(NodeSelector.INDEX_BIAS + 0), 0);
    }

    @Test
    public void testChildrenExpressionNumberPosition2() {
        this.childrenExpressionNumberPositionAndCheck(ExpressionNode.longNode(NodeSelector.INDEX_BIAS + 1), 1);
    }

    @Test
    public void testChildrenExpressionNumberPosition3() {
        this.childrenExpressionNumberPositionAndCheck(ExpressionNode.longNode(NodeSelector.INDEX_BIAS + 2), 2);
    }

    @Test
    public void testChildrenExpressionNumberPositionInvalid() {
        this.childrenExpressionNumberPositionAndCheck(ExpressionNode.longNode(0), -1);
    }

    @Test
    public void testChildrenExpressionNumberPositionOutOfBounds() {
        this.childrenExpressionNumberPositionAndCheck(ExpressionNode.longNode(99), -1);
    }

    private void childrenExpressionNumberPositionAndCheck(final ExpressionNode expression,
                                                          final int childIndex) {
        final TestNode parent = TestNode.with("parent",
                TestNode.with("child1", TestNode.with("grandChild1")),
                TestNode.with("child2", TestNode.with("grandChild2")),
                TestNode.with("child3", TestNode.with("grandChild3")));

        this.acceptAndCheck(TestNode.relativeNodeSelector()
                        .children()
                        .expression(expression),
                parent,
                childIndex < 0 ? new TestNode[0] : new TestNode[]{parent.child(childIndex)});
    }

    @Test
    public void testChildrenExpressionNumberPositionAttribute() {
        TestNode.disableUniqueNameChecks();

        final TestNode parent = TestNode.with("parent",
                nodeWithAttributes("child1", "A1", "V1"),
                nodeWithAttributes("child2", "B2", "V2"),
                nodeWithAttributes("child3", "B2", "V3"));

        this.acceptAndCheck(TestNode.relativeNodeSelector()
                        .children()
                        .expression(ExpressionNode.longNode(2))
                        .attributeValueStartsWith(Names.string("B2"), "V"),
                parent,
                parent.child(1));
    }

    @Test
    public void testExpressionFalseMap() {
        final TestNode node = TestNode.with("node");

        this.acceptMapAndCheck(ExpressionNodeSelector.with(ExpressionNode.booleanNode(false)),
                node);
    }

    @Test
    public void testExpressionTrueMap() {
        this.acceptMapAndCheck(ExpressionNodeSelector.with(ExpressionNode.booleanNode(true)),
                TestNode.with("node"),
                TestNode.with("node*0"));
    }

    @Test
    public void testExpressionTrue2Map() {
        final TestNode parent = TestNode.with("parent", TestNode.with("child"));

        TestNode.clear();

        this.acceptMapAndCheck(ExpressionNodeSelector.with(ExpressionNode.booleanNode(true)),
                parent.child(0),
                TestNode.with("parent", TestNode.with("child*0")).child(0));
    }

    @Test
    public void testExpressionTrueIgnoresChildrenMap() {
        final TestNode parent = TestNode.with("parent",
                TestNode.with("child",
                        TestNode.with("grand-child1"), TestNode.with("grand-child2")));

        TestNode.clear();

        this.acceptMapAndCheck(ExpressionNodeSelector.with(ExpressionNode.booleanNode(true)),
                parent.child(0),
                TestNode.with("parent",
                        TestNode.with("child*0",
                                TestNode.with("grand-child1"), TestNode.with("grand-child2")))
                        .child(0));
    }

    @Test
    public void testExpressionNumberNegativeMap() {
        this.acceptMapAndCheck(ExpressionNodeSelector.with(ExpressionNode.longNode(-2)),
                TestNode.with("node"));
    }

    @Test
    public void testExpressionNumberOutOfRangeMap() {
        this.acceptMapAndCheck(ExpressionNodeSelector.with(ExpressionNode.longNode(999)),
                TestNode.with("node"));
    }

    @Test
    public void testChildrenExpressionNumberInvalidMap() {
        final TestNode parent = TestNode.with("parent",
                TestNode.with("child1"),
                TestNode.with("child2"),
                TestNode.with("child3"));

        TestNode.clear();

        this.acceptMapAndCheck(TestNode.relativeNodeSelector()
                        .children()
                        .expression(ExpressionNode.longNode(99)),
                parent,
                parent);
    }

    @Test
    public void testChildrenExpressionNumberMap() {
        final TestNode parent = TestNode.with("parent",
                TestNode.with("child1"),
                TestNode.with("child2"),
                TestNode.with("child3"));

        TestNode.clear();

        this.acceptMapAndCheck(TestNode.relativeNodeSelector()
                .children()
                .expression(ExpressionNode.longNode(NodeSelector.INDEX_BIAS + 0)),
                parent,
                TestNode.with("parent", TestNode.with("child1*0"), TestNode.with("child2"), TestNode.with("child3")));
    }

    @Test
    public void testChildrenExpressionNumberMap2() {
        final TestNode parent = TestNode.with("parent",
                TestNode.with("child1"),
                TestNode.with("child2"),
                TestNode.with("child3"));

        TestNode.clear();

        this.acceptMapAndCheck(TestNode.relativeNodeSelector()
                        .children()
                        .expression(ExpressionNode.longNode(NodeSelector.INDEX_BIAS + 1)),
                parent,
                TestNode.with("parent", TestNode.with("child1"), TestNode.with("child2*0"), TestNode.with("child3")));
    }

    @Test
    public void testChildrenExpressionNumberMap3() {
        final TestNode parent = TestNode.with("parent",
                TestNode.with("child1"),
                TestNode.with("child2"),
                TestNode.with("child3"));

        TestNode.clear();

        this.acceptMapAndCheck(TestNode.relativeNodeSelector()
                        .children()
                        .expression(ExpressionNode.longNode(NodeSelector.INDEX_BIAS + 2)),
                parent,
                TestNode.with("parent", TestNode.with("child1"), TestNode.with("child2"), TestNode.with("child3*0")));
    }

    @Test
    public void testChildrenExpressionTrueMap() {
        final TestNode parent = TestNode.with("parent",
                TestNode.with("child1", TestNode.with("grandChildren1")),
                TestNode.with("child2", TestNode.with("grandChildren2")),
                TestNode.with("child3"));

        TestNode.clear();

        this.acceptMapAndCheck(TestNode.relativeNodeSelector().children().expression(ExpressionNode.booleanNode(true)),
                parent,
                TestNode.with("parent",
                        TestNode.with("child1*0", TestNode.with("grandChildren1")),
                        TestNode.with("child2*1", TestNode.with("grandChildren2")),
                        TestNode.with("child3*2")));
    }

    // /tr[1]/td[1]
    @Test
    public void testChildrenNamedExpressionNumberChildrenNamedExpressionNumberMap() {
        TestNode.disableUniqueNameChecks();

        final TestNode parent = TestNode.with("tbody",
                TestNode.with("skip"),
                TestNode.with("tr",
                        TestNode.with("td", TestNode.with("div1"))
                ),
                TestNode.with("tr",
                        TestNode.with("td", TestNode.with("div2"))
                ));

        this.acceptMapAndCheck(TestNode.relativeNodeSelector()
                        .children()
                        .named(Names.string("tr"))
                        .expression(ExpressionNode.longNode(1))
                        .children()
                        .named(Names.string("td"))
                        .expression(ExpressionNode.longNode(1)),
                parent,
                TestNode.with("tbody",
                        TestNode.with("skip"),
                        TestNode.with("tr",
                                TestNode.with("td*0", TestNode.with("div1"))
                        ),
                        TestNode.with("tr",
                                TestNode.with("td", TestNode.with("div2"))
                        )));
    }

    // /tr[1]/[2]
    @Test
    public void testChildrenExpressionNumberChildrenExpressionNumberMap() {
        TestNode.disableUniqueNameChecks();

        final TestNode parent = TestNode.with("tbody",
                TestNode.with("tr",
                        TestNode.with("td1"),
                        TestNode.with("td2")
                ),
                TestNode.with("tr",
                        TestNode.with("td3"),
                        TestNode.with("td4")
                ));

        this.acceptMapAndCheck(TestNode.relativeNodeSelector()
                        .children()
                        .named(Names.string("tr"))
                        .expression(ExpressionNode.longNode(1))
                        .children()
                        .expression(ExpressionNode.longNode(2)),
                parent,
                TestNode.with("tbody",
                        TestNode.with("tr",
                                TestNode.with("td1"),
                                TestNode.with("td2*0")
                        ),
                        TestNode.with("tr",
                                TestNode.with("td3"),
                                TestNode.with("td4")
                        )));
    }

    // /tr[1]/*[2]
    @Test
    public void testChildrenNamedExpressionNumberChildrenNamedExpressionNumberMap2() {
        TestNode.disableUniqueNameChecks();

        final TestNode parent = TestNode.with("tbody",
                TestNode.with("tr",
                        TestNode.with("td", TestNode.with("div1")),
                        TestNode.with("td", TestNode.with("div2"))
                ),
                TestNode.with("tr",
                        TestNode.with("td", TestNode.with("div3")),
                        TestNode.with("td", TestNode.with("div4"))
                ));

        this.acceptMapAndCheck(TestNode.relativeNodeSelector()
                        .children()
                        .named(Names.string("tr"))
                        .expression(ExpressionNode.longNode(2))
                        .children()
                        .expression(ExpressionNode.longNode(2)),
                parent,
                TestNode.with("tbody",
                        TestNode.with("tr",
                                TestNode.with("td", TestNode.with("div1")),
                                TestNode.with("td", TestNode.with("div2"))
                        ),
                        TestNode.with("tr",
                                TestNode.with("td", TestNode.with("div3")),
                                TestNode.with("td*0", TestNode.with("div4"))
                        )));
    }

    @Test
    public void testToString() {
        this.toStringAndCheck(this.createSelector(), "*[" + expression() + "]");
    }

    @Test
    public void testToStringChildrenExpressionTrue() {
        this.toStringAndCheck(TestNode.relativeNodeSelector()
                        .children()
                        .expression(ExpressionNode.booleanNode(true)),
                "child::*[true()]");
    }

    @Test
    public void testToStringChildrenExpressionFalse() {
        this.toStringAndCheck(TestNode.relativeNodeSelector()
                        .children()
                        .expression(ExpressionNode.booleanNode(false)),
                "child::*[false()]");
    }

    @Test
    public void testToStringChildrenNamedExpression() {
        this.toStringAndCheck(TestNode.relativeNodeSelector()
                        .children()
                        .named(Names.string("ABC"))
                        .expression(ExpressionNode.booleanNode(true)),
                "child::ABC[true()]");
    }

    @Test
    public void testToStringExpressionChildren() {
        this.toStringAndCheck(TestNode.relativeNodeSelector()
                        .expression(ExpressionNode.longNode(123))
                        .firstChild(),
                "*[123]/first-child::*");
    }

    @Override
    ExpressionNodeSelector<TestNode, StringName, StringName, Object> createSelector() {
        return ExpressionNodeSelector.with(expression());
    }

    private ExpressionNode expression() {
        return ExpressionNode.equalsNode(
                ExpressionNode.function(ExpressionNodeName.with("name"), ExpressionNode.NO_CHILDREN),
                ExpressionNode.text("self")
        );
    }

    @Override
    public Class<ExpressionNodeSelector<TestNode, StringName, StringName, Object>> type() {
        return Cast.to(ExpressionNodeSelector.class);
    }
}
