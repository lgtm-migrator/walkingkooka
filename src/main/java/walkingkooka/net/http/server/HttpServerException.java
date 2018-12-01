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

package walkingkooka.net.http.server;

import walkingkooka.net.http.HttpException;

/**
 * Base exception for all exceptions thrown in this package.
 */
public class HttpServerException extends HttpException {

    private final static long serialVersionUID = 1L;

    protected HttpServerException() {
        super();
    }

    public HttpServerException(final String message) {
        super(message);
    }

    public HttpServerException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
