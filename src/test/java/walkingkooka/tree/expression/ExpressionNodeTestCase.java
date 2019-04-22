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

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.convert.ConversionException;
import walkingkooka.convert.Converter;
import walkingkooka.convert.ConverterContext;
import walkingkooka.convert.ConverterContexts;
import walkingkooka.convert.Converters;
import walkingkooka.naming.Name;
import walkingkooka.test.ClassTesting2;
import walkingkooka.test.IsMethodTesting;
import walkingkooka.test.PublicStaticFactoryTesting;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.ParserContexts;
import walkingkooka.text.cursor.parser.Parsers;
import walkingkooka.tree.Node;
import walkingkooka.tree.NodeTesting;
import walkingkooka.tree.json.HasJsonNodeTesting;
import walkingkooka.type.MemberVisibility;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class ExpressionNodeTestCase<N extends ExpressionNode> implements ClassTesting2<ExpressionNode>,
        HasJsonNodeTesting<ExpressionNode>,
        IsMethodTesting<N>,
        NodeTesting<ExpressionNode, ExpressionNodeName, Name, Object> {

    ExpressionNodeTestCase() {
        super();
    }

    @Test
    public final void testPublicStaticFactoryMethod() {
        PublicStaticFactoryTesting.check(ExpressionNode.class,
                "Expression",
                Node.class,
                this.type());
    }

    @Override
    public final void testSetSameAttributes() {
        // Ignored
    }

    @Override
    public ExpressionNode createNode() {
        return this.createExpressionNode();
    }

    abstract N createExpressionNode();

    @Override
    public final Class<ExpressionNode> type() {
        return Cast.to(this.expressionNodeType());
    }

    abstract Class<N> expressionNodeType();

    // evaluation........................................................................................................

    final ExpressionBooleanNode booleanValue(final boolean value) {
        return ExpressionNode.booleanNode(value);
    }

    final ExpressionBigDecimalNode bigDecimal(final double value) {
        return ExpressionNode.bigDecimal(BigDecimal.valueOf(value));
    }

    final ExpressionBigIntegerNode bigInteger(final long value) {
        return ExpressionNode.bigInteger(BigInteger.valueOf(value));
    }

    final ExpressionDoubleNode doubleValue(final double value) {
        return ExpressionNode.doubleNode(value);
    }

    final LocalDate localDateValue(final long value) {
        return Converters.numberLocalDate(Converters.JAVA_EPOCH_OFFSET)
                .convert(value, LocalDate.class, this.converterContext());
    }

    final ExpressionLocalDateNode localDate(final long value) {
        return ExpressionNode.localDate(localDateValue(value));
    }

    final LocalDateTime localDateTimeValue(final double value) {
        return Converters.numberLocalDateTime(Converters.JAVA_EPOCH_OFFSET)
                .convert(value, LocalDateTime.class, this.converterContext());
    }

    final ExpressionLocalDateTimeNode localDateTime(final double value) {
        return ExpressionNode.localDateTime(localDateTimeValue(value));
    }

    final LocalTime localTimeValue(final long value) {
        return Converters.numberLocalTime()
                .convert(value, LocalTime.class, this.converterContext());
    }

    final ExpressionLocalTimeNode localTime(final long value) {
        return ExpressionNode.localTime(localTimeValue(value));
    }

    final ExpressionLongNode longValue(final long value) {
        return ExpressionNode.longNode(value);
    }

    final ExpressionTextNode text(final String value) {
        return ExpressionNode.text(value);
    }

    final ExpressionTextNode text(final Object value) {
        return text(context().convert(value, String.class));
    }

    final String textText(final ExpressionValueNode value) {
        return value.toText(context());
    }

    final void evaluateAndCheckBoolean(final ExpressionNode node, final boolean expected) {
        this.evaluateAndCheckBoolean(node, context(), expected);
    }

    final void evaluateAndCheckBoolean(final ExpressionNode node, final ExpressionEvaluationContext context, final boolean expected) {
        this.checkEquals("toBoolean of " + node + " failed", expected, node.toBoolean(context));
    }

    final void evaluateAndCheckBigDecimal(final ExpressionNode node, final double expected) {
        this.evaluateAndCheckBigDecimal(node, BigDecimal.valueOf(expected));
    }

    final void evaluateAndCheckBigDecimal(final ExpressionNode node, final BigDecimal expected) {
        this.evaluateAndCheckBigDecimal(node, context(), expected);
    }

    final void evaluateAndCheckBigDecimal(final ExpressionNode node, final ExpressionEvaluationContext context, final double expected) {
        this.evaluateAndCheckBigDecimal(node, context, BigDecimal.valueOf(expected));
    }

    final void evaluateAndCheckBigDecimal(final ExpressionNode node, final ExpressionEvaluationContext context, final BigDecimal expected) {
        this.checkEquals("toBigDecimal of " + node + " failed", expected, node.toBigDecimal(context));
    }

    final void evaluateAndCheckBigInteger(final ExpressionNode node, final long expected) {
        this.evaluateAndCheckBigInteger(node, BigInteger.valueOf(expected));
    }

    final void evaluateAndCheckBigInteger(final ExpressionNode node, final BigInteger expected) {
        this.evaluateAndCheckBigInteger(node, context(), expected);
    }

    final void evaluateAndCheckBigInteger(final ExpressionNode node, final ExpressionEvaluationContext context, final long expected) {
        this.evaluateAndCheckBigInteger(node, context, BigInteger.valueOf(expected));
    }

    final void evaluateAndCheckBigInteger(final ExpressionNode node, final ExpressionEvaluationContext context, final BigInteger expected) {
        this.checkEquals("toBigInteger of " + node + " failed", expected, node.toBigInteger(context));
    }

    final void evaluateAndCheckDouble(final ExpressionNode node, final double expected) {
        this.evaluateAndCheckDouble(node, context(), expected);
    }

    final void evaluateAndCheckDouble(final ExpressionNode node, final ExpressionEvaluationContext context, final double expected) {
        this.checkEquals("toDouble of " + node + " failed", expected, node.toDouble(context));
    }

    final void evaluateAndCheckLocalDate(final ExpressionNode node, final long expected) {
        this.evaluateAndCheckLocalDate(node, this.localDateValue(expected));
    }

    final void evaluateAndCheckLocalDate(final ExpressionNode node, final LocalDate expected) {
        this.evaluateAndCheckLocalDate(node, context(), expected);
    }

    final void evaluateAndCheckLocalDate(final ExpressionNode node, final ExpressionEvaluationContext context, final long expected) {
        this.evaluateAndCheckLocalDate(node, context, this.localDateValue(expected));
    }

    final void evaluateAndCheckLocalDate(final ExpressionNode node, final ExpressionEvaluationContext context, final LocalDate expected) {
        this.checkEquals("toLocalDate of " + node + " failed", expected, node.toLocalDate(context));
    }

    final void evaluateAndCheckLocalDateTime(final ExpressionNode node, final double expected) {
        this.evaluateAndCheckLocalDateTime(node, localDateTimeValue(expected));
    }

    final void evaluateAndCheckLocalDateTime(final ExpressionNode node, final LocalDateTime expected) {
        this.evaluateAndCheckLocalDateTime(node, context(), expected);
    }

    final void evaluateAndCheckLocalDateTime(final ExpressionNode node, final ExpressionEvaluationContext context, final double expected) {
        this.evaluateAndCheckLocalDateTime(node, context, localDateTimeValue(expected));
    }

    final void evaluateAndCheckLocalDateTime(final ExpressionNode node, final ExpressionEvaluationContext context, final LocalDateTime expected) {
        this.checkEquals("toLocalDateTime of " + node + " failed", expected, node.toLocalDateTime(context));
    }

    final void evaluateAndCheckLocalTime(final ExpressionNode node, final long expected) {
        this.evaluateAndCheckLocalTime(node, this.localTimeValue(expected));
    }

    final void evaluateAndCheckLocalTime(final ExpressionNode node, final LocalTime expected) {
        this.evaluateAndCheckLocalTime(node, context(), expected);
    }

    final void evaluateAndCheckLocalTime(final ExpressionNode node, final ExpressionEvaluationContext context, final long expected) {
        this.evaluateAndCheckLocalTime(node, context, this.localTimeValue(expected));
    }

    final void evaluateAndCheckLocalTime(final ExpressionNode node, final ExpressionEvaluationContext context, final LocalTime expected) {
        this.checkEquals("toLocalTime of " + node + " failed", expected, node.toLocalTime(context));
    }

    final void evaluateAndCheckLong(final ExpressionNode node, final long expected) {
        this.evaluateAndCheckLong(node, context(), expected);
    }

    final void evaluateAndCheckLong(final ExpressionNode node, final ExpressionEvaluationContext context, final long expected) {
        this.checkEquals("toLong of " + node + " failed", expected, node.toLong(context));
    }

    final void evaluateAndCheckNumberBigDecimal(final ExpressionNode node, final ExpressionEvaluationContext context, final double expected) {
        this.evaluateAndCheckNumber(node, context, BigDecimal.valueOf(expected));
    }

    final void evaluateAndCheckNumberBigDecimal(final ExpressionNode node, final double expected) {
        this.evaluateAndCheckNumberBigDecimal(node, BigDecimal.valueOf(expected));
    }

    final void evaluateAndCheckNumberBigDecimal(final ExpressionNode node, final BigDecimal expected) {
        this.evaluateAndCheckNumber(node, expected);
    }

    final void evaluateAndCheckNumberBigInteger(final ExpressionNode node, final long expected) {
        this.evaluateAndCheckNumberBigInteger(node, BigInteger.valueOf(expected));
    }

    final void evaluateAndCheckNumberBigInteger(final ExpressionNode node, final BigInteger expected) {
        this.evaluateAndCheckNumber(node, expected);
    }

    final void evaluateAndCheckNumberDouble(final ExpressionNode node, final double expected) {
        this.evaluateAndCheckNumber(node, expected);
    }

    final void evaluateAndCheckNumberLong(final ExpressionNode node, final long expected) {
        this.evaluateAndCheckNumber(node, expected);
    }

    final void evaluateAndCheckNumber(final ExpressionNode node, final Number expected) {
        this.evaluateAndCheckNumber(node, context(), expected);
    }

    final void evaluateAndCheckNumber(final ExpressionNode node, final ExpressionEvaluationContext context, final Number expected) {
        this.checkEquals("toNumber of " + node + " failed", Cast.to(expected), Cast.to(node.toNumber(context)));
    }

    final void evaluateAndCheckText(final ExpressionNode node, final String expected) {
        this.evaluateAndCheckText(node, context(), expected);
    }

    final void evaluateAndCheckText(final ExpressionNode node, final ExpressionEvaluationContext context, final String expected) {
        this.checkEquals("toText of " + node + " failed", expected, node.toText(context));
    }

    final void evaluateAndCheckValue(final ExpressionNode node, final Object expected) {
        this.evaluateAndCheckValue(node, context(), expected);
    }

    final void evaluateAndCheckValue(final ExpressionNode node, final ExpressionEvaluationContext context, final Object expected) {
        final Object value = node.toValue(context);
        if (expected instanceof Comparable && value instanceof Comparable) {
            this.checkEquals("toValue of " + node + " failed", Cast.to(expected), Cast.to(value));
        } else {
            assertEquals(expected, value, () -> "toValue of " + node + " failed");
        }
    }

    private <T extends Comparable<T>> void checkEquals(final String message, final T expected, final T actual) {
        // necessary because BigDecimals of different precisions (extra zeros) will not be equal.
        if (expected.getClass() != actual.getClass() || 0 != expected.compareTo(actual)) {
            assertEquals(expected, actual, message);
        }
    }

    private ConverterContext converterContext() {
        return ConverterContexts.fake();
    }

    static ExpressionEvaluationContext context() {
        final Function<ConverterContext, ParserContext> parserContext = (c) -> ParserContexts.basic(c);

        final Converter stringBigDecimal = Converters.parser(BigDecimal.class,
                Parsers.bigDecimal(MathContext.DECIMAL32),
                parserContext);
        final Converter stringNumber = Converters.parser(Number.class,
                Cast.to(Parsers.bigDecimal(MathContext.DECIMAL32)),
                parserContext);
        final Converter stringBigInteger = Converters.parser(BigInteger.class,
                Parsers.bigInteger(10),
                parserContext);
        final Converter stringDouble = Converters.parser(Double.class,
                Parsers.doubleParser(),
                parserContext);
        final Converter stringLocalDate = Converters.parser(LocalDate.class,
                Parsers.localDate(DateTimeFormatter.ISO_LOCAL_DATE, "yyyy-MM-dd"),
                parserContext);
        final Converter stringLocalDateTime = Converters.parser(LocalDateTime.class,
                Parsers.localDateTime(DateTimeFormatter.ISO_LOCAL_DATE_TIME, "yyyy-MM-dd'T'hh:mm"),
                parserContext);
        final Converter stringLocalTime = Converters.parser(LocalTime.class,
                Parsers.localTime(DateTimeFormatter.ISO_LOCAL_TIME, "hh:mm"),
                parserContext);
        final Converter stringLong = Converters.parser(Long.class,
                Parsers.longParser(10),
                parserContext);

        final Converter converters = Converters.collection(Lists.of(
                Converters.simple(),
                // localDate ->
                toBoolean(LocalDate.class, LocalDate.ofEpochDay(0)),
                Converters.localDateBigDecimal(Converters.JAVA_EPOCH_OFFSET),
                Converters.localDateBigInteger(Converters.JAVA_EPOCH_OFFSET),
                Converters.localDateDouble(Converters.JAVA_EPOCH_OFFSET),
                Converters.localDateLocalDateTime(),
                Converters.fail(LocalDate.class, LocalTime.class),
                Converters.localDateLong(Converters.JAVA_EPOCH_OFFSET),
                Converters.forward(Converters.localDateLong(Converters.JAVA_EPOCH_OFFSET), Number.class, Long.class).setToString("LocalDate->Long"),
                Converters.localDateString(DateTimeFormatter.ISO_LOCAL_DATE),
                // localDateTime ->
                toBoolean(LocalDateTime.class, LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC)),
                Converters.localDateTimeBigDecimal(Converters.JAVA_EPOCH_OFFSET),
                Converters.localDateTimeBigInteger(Converters.JAVA_EPOCH_OFFSET),
                Converters.localDateTimeDouble(Converters.JAVA_EPOCH_OFFSET),
                Converters.localDateTimeLocalDate(Converters.JAVA_EPOCH_OFFSET),
                Converters.localDateTimeLocalTime(),
                Converters.localDateTimeLong(Converters.JAVA_EPOCH_OFFSET),
                Converters.forward(Converters.localDateTimeDouble(Converters.JAVA_EPOCH_OFFSET), Number.class, Double.class).setToString("LocalDateTime->Number"),
                Converters.localDateTimeString(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                // localTime
                toBoolean(LocalTime.class, LocalTime.ofNanoOfDay(0)),
                Converters.localTimeBigDecimal(),
                Converters.localTimeBigInteger(),
                Converters.localTimeDouble(),
                Converters.fail(LocalTime.class, LocalDate.class),
                Converters.localTimeLocalDateTime(),
                Converters.localTimeLong(),
                Converters.forward(Converters.localTimeLong(), Number.class, Long.class).setToString("LocalTime->Long"),
                Converters.localTimeString(DateTimeFormatter.ISO_LOCAL_TIME),
                // number ->
                Converters.numberBigDecimal(),
                Converters.numberBigInteger(),
                Converters.truthyNumberBoolean(),
                Converters.numberDouble(),
                Converters.numberLocalDate(Converters.JAVA_EPOCH_OFFSET),
                Converters.numberLocalDateTime(Converters.JAVA_EPOCH_OFFSET),
                Converters.numberLocalTime(),
                Converters.numberLong(),
                Converters.decimalFormatString("#.###"),
                // string ->
                stringBigDecimal,
                stringBigInteger,
                Converters.stringBoolean(),
                stringDouble,
                stringLocalDate,
                stringLocalDateTime,
                stringLocalTime,
                stringLong,
                stringNumber,
                Converters.string(),
                // boolean ->
                fromBoolean(BigDecimal.class, Converters.numberBigDecimal()),
                fromBoolean(BigInteger.class, Converters.numberBigInteger()),
                fromBoolean(Double.class, Converters.numberDouble()),
                fromBoolean(LocalDate.class, Converters.numberLocalDate(Converters.JAVA_EPOCH_OFFSET)),
                fromBoolean(LocalDateTime.class, Converters.numberLocalDateTime(Converters.JAVA_EPOCH_OFFSET)),
                fromBoolean(LocalTime.class, Converters.numberLocalTime()),
                fromBoolean(Long.class, Converters.numberLong())));

        return new FakeExpressionEvaluationContext() {

            @Override
            public String currencySymbol() {
                return "$";
            }

            @Override
            public char decimalPoint() {
                return '.';
            }

            @Override
            public char exponentSymbol() {
                return 'E';
            }

            @Override
            public char groupingSeparator() {
                return ',';
            }

            @Override
            public char minusSign() {
                return '-';
            }

            @Override
            public char percentageSymbol() {
                return '%';
            }

            @Override
            public char plusSign() {
                return '+';
            }

            @Override
            public MathContext mathContext() {
                return this.matchContext;
            }

            private final MathContext matchContext = MathContext.DECIMAL64;

            @Override
            public <T> T convert(final Object value, final Class<T> target) {
                try {
                    if (!(value instanceof Boolean ||
                            value instanceof LocalDate ||
                            value instanceof LocalDateTime ||
                            value instanceof LocalTime ||
                            value instanceof Number ||
                            value instanceof String)) {
                        fail("Cannot convert expects only Boolean | LocalDate | LocalDateTime, LocalTime | Number | String " + value.getClass().getName() + "=" + value);
                    }
                    return converters.convert(value, target, ConverterContexts.basic(this));
                } catch (final ConversionException fail) {
                    throw new ExpressionEvaluationConversionException(fail.getMessage(), fail);
                }
            }
        };
    }

    private static <T> Converter fromBoolean(final Class<T> targetType, final Converter trueOrFalse) {
        final ConverterContext context = ConverterContexts.fake();
        return Converters.booleanConverter(Boolean.class,
                Boolean.FALSE,
                targetType,
                trueOrFalse.convert(1L, targetType, context),
                trueOrFalse.convert(0L, targetType, context));
    }

    private static <S> Converter toBoolean(final Class<S> sourceType, final S falseValue) {
        return Converters.booleanConverter(sourceType,
                falseValue,
                Boolean.class,
                Boolean.TRUE,
                Boolean.FALSE);
    }

    @Override
    public final String typeNamePrefix() {
        return "Expression";
    }

    // IsMethodTesting.................................................................................................

    @Override
    public final N createIsMethodObject() {
        return Cast.to(this.createNode());
    }

    @Override
    public final String isMethodTypeNamePrefix() {
        return "Expression";
    }

    @Override
    public final String isMethodTypeNameSuffix() {
        return Node.class.getSimpleName();
    }

    @Override
    public final Predicate<String> isMethodIgnoreMethodFilter() {
        return (m) -> m.equals("isRoot");
    }

    // ClassTestCase.........................................................................................

    @Override
    public final MemberVisibility typeVisibility() {
        return MemberVisibility.PUBLIC;
    }

    // HasJsonNodeTesting...........................................................................................

    @Override
    public final ExpressionNode createHasJsonNode() {
        return this.createExpressionNode();
    }
}
