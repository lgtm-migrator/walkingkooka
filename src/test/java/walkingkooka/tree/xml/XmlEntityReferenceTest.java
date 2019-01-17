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

package walkingkooka.tree.xml;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import walkingkooka.Cast;
import walkingkooka.tree.search.SearchNodeName;

import javax.xml.parsers.DocumentBuilder;

import static org.junit.Assert.assertEquals;

public final class XmlEntityReferenceTest extends XmlParentNodeTestCase<XmlEntityReference> {

    private final static String REFERENCE = "kooka";

    @Test
    public void testToString() {
        assertEquals("&kooka;", this.createNode().toString());
    }

    @Test
    public void testToStringWithinDocument() throws Exception {
        final Document document = Cast.to(XmlNode.fromXml(
                this.documentBuilder(false, false),
                this.resource())
                .node);
        final Element root = document.getDocumentElement();
        final EntityReference reference = Cast.to(root.getFirstChild());
        assertEquals("&kooka;", XmlEntityReference.with(reference).toString());
    }

    @Test
    public void testW3cEntityReference() {
        final Document document = this.documentBuilder().newDocument();
        final EntityReference reference = document.createEntityReference(REFERENCE);
        assertEquals("name", REFERENCE, reference.getNodeName());
        assertEquals("text", "", reference.getTextContent());
    }

    // helpers............................................................................................

    @Override
    XmlEntityReference createNode(final Document document) {
        return XmlEntityReference.with(document.createEntityReference(REFERENCE));
    }

    @Override
    XmlEntityReference createNode(final DocumentBuilder builder) {
        return this.createNode(builder.newDocument());
    }

    @Override
    String text() {
        return "";
    }

    @Override
    SearchNodeName searchNodeName() {
        return SearchNodeName.with("EntityReference");
    }

    @Override
    Class<XmlEntityReference> nodeType() {
        return XmlEntityReference.class;
    }
}