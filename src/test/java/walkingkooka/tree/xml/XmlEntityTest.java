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

import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import walkingkooka.Cast;
import walkingkooka.tree.search.SearchNodeName;

import javax.xml.parsers.DocumentBuilder;
import java.io.Reader;

import static org.junit.Assert.assertEquals;

public final class XmlEntityTest extends XmlParentNodeTestCase<XmlEntity> {

    @Test
    @Ignore
    public void testParentWithout() {
        // disable, because of how createNode works, our entities always have a parent.
    }

    @Test
    public void testParentWith() {
        // n/a
    }

    @Test
    public void testToString() {
        assertEquals("<!ENTITY file SYSTEM \"http://www.example.com/archive.zip\">", this.createNode().toString());
    }

    // helpers............................................................................................

    @Override
    XmlEntity createNode(final Document document) {
        try (Reader reader = this.resourceAsReader(this.getClass(), this.getClass().getSimpleName() + "/createNode.xml")) {
            final XmlDocument root = XmlNode.fromXml(this.documentBuilder(false, true), reader);
            final DocumentType documentType = Cast.to(root.node.getChildNodes().item(0));
            final Entity entity = Cast.to(documentType.getEntities().item(0));
            return XmlEntity.with(entity);
        } catch (final Exception rethrow) {
            throw new Error(rethrow);
        }
    }

    @Override
    XmlEntity createNode(final DocumentBuilder builder) {
        return this.createNode(builder.newDocument());
    }

    @Override
    String text() {
        return "";
    }

    @Override
    SearchNodeName searchNodeName() {
        return SearchNodeName.with("Entity");
    }

    @Override
    Class<XmlEntity> nodeType() {
        return XmlEntity.class;
    }
}