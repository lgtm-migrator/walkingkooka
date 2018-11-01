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

package walkingkooka.tree.expression;

/**
 * This exception is thrown whenever a reference lookup fails.
 */
public class ExpressionEvaluationReferenceException extends ExpressionException {

    private static final long serialVersionUID = 1;

    protected ExpressionEvaluationReferenceException() {
        super();
    }

    public ExpressionEvaluationReferenceException(final String message) {
        super(message);
    }

    public ExpressionEvaluationReferenceException(final String message, final Throwable cause) {
        super(message, cause);
    }
}