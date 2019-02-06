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
package walkingkooka.tree.pojo;

import org.junit.jupiter.api.Test;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.test.ClassTestCase;
import walkingkooka.tree.select.NodeSelector;
import walkingkooka.tree.select.NodeSelectorTesting;
import walkingkooka.type.MemberVisibility;

import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class PojoNodeTest extends ClassTestCase<PojoNode>
        implements NodeSelectorTesting<PojoNode, PojoName, PojoNodeAttributeName, Object> {

    @Test
    public void testSelectorNodeByClassName() {
        final TestBean bean = new TestBean("1", "2", 99, "3");

        final NodeSelector<PojoNode, PojoName, PojoNodeAttributeName, Object> selector = PojoNode.PATH_SEPARATOR.absoluteNodeSelectorBuilder(PojoNode.class)
                .descendant()
                .attributeValueEquals(PojoNodeAttributeName.CLASS, String.class.getName())
                .build();

        final PojoNode node = PojoNode.wrap(PojoName.property("TestBean"),
                bean,
                new ReflectionPojoNodeContext());
        final List<PojoNode> selected = this.selectorAcceptAndCollect(node, selector);
        assertEquals(Sets.of("1", "2", "3"),
                selected.stream()
                        .map(n -> n.value())
                        .collect(Collectors.toCollection(TreeSet::new)));
    }

    @Override
    public PojoNode createNode() {
        return PojoNode.wrap(PojoName.property("TestBean"),
                new TestBean("1", "2", 99, "3"),
                new ReflectionPojoNodeContext());
    }

    @Override
    public Class<PojoNode> type() {
        return PojoNode.class;
    }

    @Override
    protected MemberVisibility typeVisibility() {
        return MemberVisibility.PUBLIC;
    }

    private class TestBean {

        TestBean(final Object x, final Object... list){
            this.x = x;
            this.list = Lists.of(list);
        }

        Object x;
        public Object getX(){
            return x;
        }

        List<Object> list;
        public Object getList() {
            return this.list;
        }

        public String toString() {
            return this.getX().toString();
        }
    }
}
