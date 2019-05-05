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

package walkingkooka.tree.expression.function;

import walkingkooka.type.PublicStaticHelper;

/**
 * Collection of static factory methods for numerous {@link ExpressionFunction}.
 */
public final class ExpressionFunctions implements PublicStaticHelper {

    /**
     * {@see ExpressionBooleanFunction}
     */
    public static ExpressionFunction<Boolean> booleanFunction() {
        return ExpressionBooleanFunction.INSTANCE;
    }

    /**
     * {@see ExpressionConcatFunction}
     */
    public static ExpressionFunction<String> concat() {
        return ExpressionConcatFunction.INSTANCE;
    }

    /**
     * {@see ExpressionContainsFunction}
     */
    public static ExpressionFunction<Boolean> contains() {
        return ExpressionContainsFunction.INSTANCE;
    }

    /**
     * {@see ExpressionEndsWithFunction}
     */
    public static ExpressionFunction<Boolean> endsWith() {
        return ExpressionEndsWithFunction.INSTANCE;
    }

    /**
     * {@see ExpressionFalseFunction}
     */
    public static ExpressionFunction<Boolean> falseFunction() {
        return ExpressionFalseFunction.INSTANCE;
    }

    /**
     * {@see ExpressionNormalizeSpaceFunction}
     */
    public static ExpressionFunction<String> normalizeSpace() {
        return ExpressionNormalizeSpaceFunction.INSTANCE;
    }

    /**
     * {@see ExpressionNodeNameFunction}
     */
    public static ExpressionFunction<String> nodeName() {
        return ExpressionNodeNameFunction.INSTANCE;
    }

    /**
     * {@see ExpressionNotFunction}
     */
    public static ExpressionFunction<Boolean> not(final ExpressionFunction<?> function) {
        return ExpressionNotFunction.with(function);
    }

    /**
     * {@see ExpressionNumberFunction}
     */
    public static ExpressionFunction<Number> number() {
        return ExpressionNumberFunction.INSTANCE;
    }

    /**
     * {@see ExpressionStartsWithFunction}
     */
    public static ExpressionFunction<Boolean> startsWith() {
        return ExpressionStartsWithFunction.INSTANCE;
    }

    /**
     * {@see ExpressionStringLengthFunction}
     */
    public static ExpressionFunction<Number> stringLength() {
        return ExpressionStringLengthFunction.INSTANCE;
    }

    /**
     * {@see ExpressionSubstringFunction}
     */
    public static ExpressionFunction<String> substring(final int indexBias) {
        return ExpressionSubstringFunction.with(indexBias);
    }

    /**
     * {@see ExpressionSubstringAfterFunction}
     */
    public static ExpressionFunction<String> substringAfter() {
        return ExpressionSubstringAfterFunction.INSTANCE;
    }

    /**
     * {@see ExpressionSubstringBeforeFunction}
     */
    public static ExpressionFunction<String> substringBefore() {
        return ExpressionSubstringBeforeFunction.INSTANCE;
    }

    /**
     * {@see ExpressionTextFunction}
     */
    public static ExpressionFunction<String> text() {
        return ExpressionTextFunction.INSTANCE;
    }

    /**
     * {@see ExpressionTrueFunction}
     */
    public static ExpressionFunction<Boolean> trueFunction() {
        return ExpressionTrueFunction.INSTANCE;
    }

    /**
     * Stops creation
     */
    private ExpressionFunctions() {
        throw new UnsupportedOperationException();
    }
}
