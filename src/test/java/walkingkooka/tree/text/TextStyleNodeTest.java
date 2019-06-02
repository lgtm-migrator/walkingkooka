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

package walkingkooka.tree.text;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.map.Maps;
import walkingkooka.color.Color;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.visit.Visiting;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class TextStyleNodeTest extends TextParentNodeTestCase<TextStyleNode> {

    @Test
    public void testWithNullFails() {
        assertThrows(NullPointerException.class, () -> {
            TextStyleNode.with(null, TextStyleNode.NO_ATTRIBUTES_MAP);
        });
    }

    @Test
    public void testWith() {
        final TextNode child = TextNode.text("child1");
        final TextStyleNode properties = TextStyleNode.with(Lists.of(child), TextStyleNode.NO_ATTRIBUTES_MAP);
        this.childCountCheck(properties, child);
    }

    @Test
    public void testWithChildrenDefensiveCopy() {
        final TextNode child1 = TextNode.text("child1");
        final TextNode child2 = TextNode.text("child2");

        final List<TextNode> children = Lists.array();
        children.add(child1);
        children.add(child2);

        final TextStyleNode properties = TextStyleNode.with(children, TextStyleNode.NO_ATTRIBUTES_MAP);
        children.clear();
        this.childCountCheck(properties, child1, child2);
    }

    @Test
    public void testWithParent() {
        final TextNode child1 = this.text1();

        final TextStyleNode parent = textPropertiesNode(child1);
        final TextStyleNode grandParent = textPropertiesNode(parent);

        final TextNode parent2 = grandParent.children().get(0);
        this.checkWithParent(parent2);
        this.childCountCheck(parent2, child1);

        final TextNode grandParent2 = parent2.parentOrFail();
        this.childCountCheck(grandParent2, parent);
        this.checkWithoutParent(grandParent2);
    }

    @Test
    public void testSetDifferentChildren() {
        final TextStyleNode node = this.createTextNode();
        final List<TextNode> children = Lists.of(different());

        final TextStyleNode different = node.setChildren(children);
        assertNotSame(different, node);

        this.childCountCheck(different, different());
        this.childCountCheck(node, text1(), text2());
    }

    @Test
    public void testSetDifferentChildrenWithParent() {
        final TextNode child1 = this.text1();

        final TextStyleNode parent = textPropertiesNode(child1);
        final TextStyleNode grandParent = textPropertiesNode(parent);

        final TextNode child2 = this.text2();
        final TextNode different = grandParent.children().get(0).appendChild(child2);
        this.checkWithParent(different);
        this.childCountCheck(different, child1, child2);

        final TextNode grandParent2 = different.parentOrFail();
        this.childCountCheck(grandParent2, different);
        this.checkWithoutParent(grandParent2);
    }

    @Test
    public void testSetNoChildren() {
        final TextStyleNode node = this.createTextNode();
        final List<TextNode> children = TextNode.NO_CHILDREN;

        final TextStyleNode different = node.setChildren(children);
        assertNotSame(different, node);
        this.childCountCheck(different);

        this.childCountCheck(node, text1(), text2());
    }

    @Test
    public void testSetDifferentAttributes() {
        final TextStyleNode node = this.createTextNode();
        final Map<TextStylePropertyName<?>, Object> attributes = Maps.of(TextStylePropertyName.with("abc"), "xyz");

        final TextNode different = node.setAttributes(attributes);
        assertNotSame(different, node);
        checkAttributes(different, attributes);

        checkAttributes(node, TextStyleNode.NO_ATTRIBUTES);
    }

    @Test
    public void testSetDifferentAttributesTwice() {
        final TextStyleNode node = this.createTextNode();
        final Map<TextStylePropertyName<?>, Object> attributes = Maps.of(TextStylePropertyName.with("abc"), "xyz");

        final TextNode different = node.setAttributes(attributes);
        assertNotSame(different, node);

        final Map<TextStylePropertyName<?>, Object> attributes2 = Maps.of(TextStylePropertyName.with("def"), "qrs");
        final TextNode different2 = different.setAttributes(attributes2);
        assertNotSame(different, different2);

        checkAttributes(different, attributes);
        checkAttributes(different2, attributes2);
        checkAttributes(node, TextStyleNode.NO_ATTRIBUTES);
    }

    @Test
    public void testSetDifferentChildrenDifferentAttributes() {
        final TextStyleNode node = this.createTextNode();
        final List<TextNode> children = Lists.of(different());

        final TextStyleNode different = node.setChildren(children);
        assertNotSame(different, node);
        this.childCountCheck(different, different());

        final Map<TextStylePropertyName<?>, Object> attributes = Maps.of(TextStylePropertyName.with("abc"), "xyz");

        final TextNode different2 = node.setAttributes(attributes);
        assertNotSame(different2, different);
        checkAttributes(different2, attributes);

        this.childCountCheck(node, text1(), text2());
        checkAttributes(node, TextStyleNode.NO_ATTRIBUTES);
    }

    private static void checkAttributes(final TextNode node, final Map<TextStylePropertyName<?>, Object> attributes) {
        assertEquals(attributes, node.attributes(), "attributes");
    }

    // HasText..........................................................................................................

    @Test
    public void testText() {
        this.textAndCheck(TextNode.style(Lists.of(Text.with("a1"), Text.with("b2"))),
                "a1b2");
    }

    // HasTextOffset .....................................................................................................

    @Test
    public void testTextOffsetWithParent() {
        this.textOffsetAndCheck(TextNode.style(Lists.of(Text.with("a1"),
                TextNode.style(Lists.of(Text.with("b22")))))
                        .children().get(1),
                2);
    }

    // HasJsonNode .....................................................................................................

    @Test
    public void testToJsonNodeWithoutChildren() {
        this.toJsonNodeAndCheck(textPropertiesNode(), "{}");
    }

    @Test
    public void testToJsonNodeWithChildren() {
        this.toJsonNodeAndCheck(textPropertiesNode(TextNode.text("text123")), "{\"values\": [{\"type\": \"text\", \"value\": \"text123\"}]}");
    }

    @Test
    public void testToJsonNodeWithChildren2() {
        this.toJsonNodeAndCheck(textPropertiesNode(TextNode.text("text123"), TextNode.text("text456")),
                "{\"values\": [{\"type\": \"text\", \"value\": \"text123\"}, {\"type\": \"text\", \"value\": \"text456\"}]}");
    }

    @Test
    public void testToJsonNodeWithProperties() {
        this.toJsonNodeAndCheck(textPropertiesNode()
                .setAttributes(Maps.of(TextStylePropertyName.BACKGROUND_COLOR, Color.fromRgb(0x123456))),
                "{\"style\": {\"background-color\": \"#123456\"}}");
    }

    @Test
    public void testToJsonNodeWithPropertiesAndChildren() {
        this.toJsonNodeAndCheck(textPropertiesNode(TextNode.text("text123"))
                        .setAttributes(Maps.of(TextStylePropertyName.BACKGROUND_COLOR, Color.fromRgb(0x123456))),
                "{\"style\": {\"background-color\": \"#123456\"}, \"values\": [{\"type\": \"text\", \"value\": \"text123\"}]}");
    }
    @Test
    public void testFromJsonNodeWithoutChildren() {
        this.fromJsonNodeAndCheck("{}",
                textPropertiesNode());
    }

    @Test
    public void testFromJsonNodeWithChildren() {
        this.fromJsonNodeAndCheck("{\"values\": [{\"type\": \"text\", \"value\": \"text123\"}]}",
                textPropertiesNode(TextNode.text("text123")));
    }

    @Test
    public void testJsonRoundtrip() {
        this.toJsonNodeRoundTripTwiceAndCheck(textPropertiesNode(TextNode.text("text1"),
                TextNode.placeholder(TextPlaceholderName.with("placeholder2")),
                textPropertiesNode(TextNode.text("text3"))));
    }

    @Test
    public void testJsonRoundtrip2() {
        this.toJsonNodeRoundTripTwiceAndCheck(textPropertiesNode(
                TextNode.text("text1"),
                textPropertiesNode(TextNode.placeholder(TextPlaceholderName.with("placeholder2")),
                        textPropertiesNode(TextNode.text("text3")))));
    }

    @Test
    public void testJsonRoundtripWithProperties() {
        this.toJsonNodeRoundTripTwiceAndCheck(textPropertiesNode(
                TextNode.text("text1"),
                TextNode.placeholder(TextPlaceholderName.with("placeholder2")),
                textPropertiesNode(TextNode.text("text3")))
                .setAttributes(Maps.of(TextStylePropertyName.BACKGROUND_COLOR, Color.fromRgb(0x123456))));
    }

    @Test
    public void testJsonRoundtripWithProperties2() {
        this.toJsonNodeRoundTripTwiceAndCheck(textPropertiesNode(
                TextNode.text("text1"),
                TextNode.placeholder(TextPlaceholderName.with("placeholder2")),
                TextNode.style(Lists.of(TextNode.text("text3"))))
                .setAttributes(Maps.of(TextStylePropertyName.BACKGROUND_COLOR, Color.fromRgb(0x123456), TextStylePropertyName.TEXT_COLOR, Color.fromRgb(0x789abc))));
    }

    @Test
    public void testJsonRoundtripWithProperties3() {
        final Map<TextStylePropertyName<?>, Object> properties = Maps.ordered();
        properties.put(TextStylePropertyName.BACKGROUND_COLOR, Color.fromRgb(0x123456));
        properties.put(TextStylePropertyName.DIRECTION, Direction.LTR);
        properties.put(TextStylePropertyName.FONT_FAMILY_NAME, FontFamilyName.with("Antiqua"));
        properties.put(TextStylePropertyName.FONT_KERNING, FontKerning.NORMAL);
        properties.put(TextStylePropertyName.FONT_SIZE, FontSize.with(10));
        properties.put(TextStylePropertyName.FONT_STRETCH, FontStretch.CONDENSED);
        properties.put(TextStylePropertyName.FONT_STYLE, FontStyle.ITALIC);
        properties.put(TextStylePropertyName.FONT_VARIANT, FontVariant.SMALL_CAPS);
        properties.put(TextStylePropertyName.FONT_WEIGHT, FontWeight.with(1000));
        properties.put(TextStylePropertyName.HANGING_PUNCTUATION, HangingPunctuation.LAST);
        properties.put(TextStylePropertyName.HORIZONTAL_ALIGNMENT, HorizontalAlignment.LEFT);
        properties.put(TextStylePropertyName.HYPHENS, Hyphens.AUTO);
        properties.put(TextStylePropertyName.TEXT_ALIGNMENT, TextAlignment.LEFT);
        properties.put(TextStylePropertyName.TEXT_COLOR, Color.fromRgb(0x789abc));
        properties.put(TextStylePropertyName.TEXT_DECORATION, TextDecoration.UNDERLINE);
        properties.put(TextStylePropertyName.TEXT_DECORATION_COLOR, Color.fromRgb(0xabcdef));
        properties.put(TextStylePropertyName.TEXT_DECORATION_STYLE, TextDecorationStyle.DASHED);
        properties.put(TextStylePropertyName.TEXT_JUSTIFY, TextJustify.INTER_CHARACTER);
        properties.put(TextStylePropertyName.TEXT_TRANSFORM, TextTransform.CAPITALIZE);
        properties.put(TextStylePropertyName.TEXT_WRAPPING, TextWrapping.OVERFLOW);
        properties.put(TextStylePropertyName.VERTICAL_ALIGNMENT, VerticalAlignment.BOTTOM);
        properties.put(TextStylePropertyName.WHITE_SPACE, TextWhitespace.PRE);
        properties.put(TextStylePropertyName.WORD_BREAK, WordBreak.BREAK_WORD);
        properties.put(TextStylePropertyName.WORD_WRAP, WordWrap.BREAK_WORD);
        properties.put(TextStylePropertyName.WRITING_MODE, WritingMode.VERTICAL_LR);

        this.toJsonNodeRoundTripTwiceAndCheck(textPropertiesNode(
                TextNode.text("text1"),
                TextNode.placeholder(TextPlaceholderName.with("placeholder2")),
                TextNode.style(Lists.of(TextNode.text("text3"))))
                .setAttributes(properties));
    }

    // Visitor .........................................................................................................

    @Test
    public void testAccept() {
        final StringBuilder b = new StringBuilder();
        final List<TextNode> visited = Lists.array();

        final TextStyleNode properties = textPropertiesNode(TextNode.text("a1"), TextNode.text("b2"));
        final Text text1 = Cast.to(properties.children().get(0));
        final Text text2 = Cast.to(properties.children().get(1));

        new FakeTextNodeVisitor() {
            @Override
            protected Visiting startVisit(final TextNode n) {
                b.append("1");
                visited.add(n);
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final TextNode n) {
                b.append("2");
                visited.add(n);
            }

            @Override
            protected Visiting startVisit(final TextStyleNode t) {
                assertSame(properties, t);
                b.append("5");
                visited.add(t);
                return Visiting.CONTINUE;
            }

            @Override
            protected void endVisit(final TextStyleNode t) {
                assertSame(properties, t);
                b.append("6");
                visited.add(t);
            }

            @Override
            protected void visit(final Text t) {
                b.append("7");
                visited.add(t);
            }
        }.accept(properties);
        assertEquals("1517217262", b.toString());
        assertEquals(Lists.of(properties, properties,
                text1, text1, text1,
                text2, text2, text2,
                properties, properties),
                visited,
                "visited");
    }

    // toString........................................................................................................

    @Test
    public void testToStringEmpty() {
        this.toStringAndCheck(textPropertiesNode(), "[]");
    }

    @Test
    public void testToStringWithChild() {
        this.toStringAndCheck(textPropertiesNode(text1()), "[\"text-1a\"]");
    }

    @Test
    public void testToStringWithChildren() {
        this.toStringAndCheck(textPropertiesNode(text1(), text2()), "[\"text-1a\", \"text-2b\"]");
    }

    @Test
    public void testToStringWithAttributesWithoutChildren() {
        this.toStringAndCheck(textPropertiesNode().setAttributes(Maps.of(TextStylePropertyName.with("abc"), "123")), "{abc: \"123\"}[]");
    }

    @Test
    public void testToStringWithAttributes2() {
        this.toStringAndCheck(textPropertiesNode().setAttributes(Maps.of(TextStylePropertyName.with("abc"), "123", TextStylePropertyName.with("def"), "456")),
                "{abc: \"123\", def: \"456\"}[]");
    }

    @Test
    public void testToStringWithChildrenAndAttributes() {
        this.toStringAndCheck(textPropertiesNode(text1()).setAttributes(Maps.of(TextStylePropertyName.with("abc"), "123")), "{abc: \"123\"}[\"text-1a\"]");
    }

    @Test
    public void testToStringWithPropertiesWithChildren() {
        this.toStringAndCheck(textPropertiesNode(text1(), textPropertiesNode(text2())), "[\"text-1a\", [\"text-2b\"]]");
    }

    @Override
    TextStyleNode createTextNode() {
        return textPropertiesNode(text1(), text2());
    }

    private static TextStyleNode textPropertiesNode(final TextNode... children) {
        return textPropertiesNode(Lists.of(children));
    }

    private static TextStyleNode textPropertiesNode(final List<TextNode> children) {
        return TextStyleNode.with(children, TextStyleNode.NO_ATTRIBUTES_MAP);
    }

    @Override
    Class<TextStyleNode> textNodeType() {
        return TextStyleNode.class;
    }

    // JsonNodeTesting...................................................................................................

    @Override
    public final TextStyleNode fromJsonNode(final JsonNode from) {
        return TextStyleNode.fromJsonNode(from);
    }
}