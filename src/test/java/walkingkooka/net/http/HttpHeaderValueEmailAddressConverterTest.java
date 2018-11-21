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

package walkingkooka.net.http;

import org.junit.Test;
import walkingkooka.net.email.EmailAddress;

public final class HttpHeaderValueEmailAddressConverterTest extends
        HttpHeaderValueConverterTestCase<HttpHeaderValueEmailAddressConverter, EmailAddress> {

    @Test
    public void testFrom() {
        final String url = "user@example.com";
        this.parseAndCheck(url, EmailAddress.with(url));
    }

    @Override
    HttpHeaderName<EmailAddress> headerOrParameterName() {
        return HttpHeaderName.FROM;
    }

    @Override
    HttpHeaderValueEmailAddressConverter converter() {
        return HttpHeaderValueEmailAddressConverter.INSTANCE;
    }

    @Override
    String invalidHeaderValue() {
        return "/relative/url/must/fail";
    }

    @Override
    String converterToString() {
        return EmailAddress.class.getSimpleName();
    }

    @Override
    protected Class<HttpHeaderValueEmailAddressConverter> type() {
        return HttpHeaderValueEmailAddressConverter.class;
    }
}