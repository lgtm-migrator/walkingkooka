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

package walkingkooka.stream.push;

import org.junit.jupiter.api.Test;
import walkingkooka.Cast;
import walkingkooka.collect.iterator.IteratorTesting;
import walkingkooka.collect.list.Lists;
import walkingkooka.predicate.Predicates;
import walkingkooka.stream.StreamTesting;
import walkingkooka.test.HashCodeEqualsDefinedTesting;
import walkingkooka.test.ToStringTesting;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class PushableStreamStreamTest implements HashCodeEqualsDefinedTesting,
        StreamTesting<PushableStreamStream<String>, String>,
        ToStringTesting<PushableStreamStream<String>>,
        IteratorTesting {

    @Test
    public void testStreamNullPushableStreamConsumerFails() {
        assertThrows(NullPointerException.class, () -> {
            PushableStreamStream.with(null);
        });
    }

    @Test
    public void testStream() {
        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        this.checkPushableStreamStream(PushableStreamStream.with(starter),
                starter,
                CloseableCollection.empty());
    }

    // Limit............................................................................................................

    @Test
    public void testStreamLimit0() {
        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.limit(0);

        assertNotSame(stream, stream2);

        this.checkPushableStreamStream(stream2,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.limit(0));
    }

    @Test
    public void testStreamLimitLongMaxValue() {
        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.limit(Long.MAX_VALUE);

        assertSame(stream, stream);

        this.checkPushableStreamStream(stream2,
                starter,
                CloseableCollection.empty());
    }

    @Test
    public void testStreamLimit() {
        final long limit = 123;

        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.limit(limit);

        assertNotSame(stream, stream2);

        this.checkPushableStreamStream(stream2,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.limit(limit));
    }

    @Test
    public void testStreamLimitLimit0() {
        final long limit = 123;

        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.limit(limit);

        assertNotSame(stream2, stream2.limit(0));

        this.checkPushableStreamStream(stream2,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.limit(limit));
    }

    @Test
    public void testStreamLimitSkip0() {
        final long limit = 123;

        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.limit(limit);

        assertSame(stream2, stream2.skip(0));

        this.checkPushableStreamStream(stream2,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.limit(limit));
    }

    @Test
    public void testStreamLimitLimit() {
        final long limit1 = 1;

        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.limit(limit1);

        assertNotSame(stream, stream2);

        final long limit2 = 20;
        final Stream<String> stream3 = stream2.limit(limit2);

        assertNotSame(stream2, stream3);

        this.checkPushableStreamStream(stream3,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.limit(limit1));
    }

    // Skip............................................................................................................

    @Test
    public void testStreamSkip() {
        final long skip = 123;

        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.skip(skip);

        assertNotSame(stream, stream2);

        this.checkPushableStreamStream(stream2,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.skip(skip));
    }

    @Test
    public void testStreamSkipLimit() {
        final long skip = 123;

        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.skip(skip);

        final long limit = 456;
        final Stream<String> stream3 = stream2.limit(limit);
        assertNotSame(stream2, stream3);

        this.checkPushableStreamStream(stream3,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.skip(skip),
                PushableStreamStreamIntermediate.limit(limit));
    }

    @Test
    public void testStreamSkipSkip0() {
        final long skip = 123;

        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.skip(skip);

        assertSame(stream2, stream2.skip(0));

        this.checkPushableStreamStream(stream2,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.skip(skip));
    }

    @Test
    public void testStreamSkipSkip() {
        final long skip1 = 1;

        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.skip(skip1);

        assertNotSame(stream, stream2);

        final long skip2 = 20;
        final Stream<String> stream3 = stream2.skip(skip2);

        assertNotSame(stream2, stream3);

        this.checkPushableStreamStream(stream3,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.skip(skip1 + skip2));
    }

    // filter..........................................................................................................

    @Test
    public void testStreamFilter() {
        final Predicate<String> filter = Predicates.fake();

        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.filter(filter);

        assertNotSame(stream, stream2);

        this.checkPushableStreamStream(stream2,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.filter(filter));
    }

    @Test
    public void testStreamFilterFilter() {
        final Predicate<String> filter1 = Predicates.fake();

        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.filter(filter1);

        assertNotSame(stream, stream2);

        final Predicate<String> filter2 = Predicates.fake();
        final Stream<String> stream3 = stream2.filter(filter2);

        this.checkPushableStreamStream(stream3,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.filter(filter1),
                PushableStreamStreamIntermediate.filter(filter2));
    }

    @Test
    public void testStreamFilterLimit() {
        final Predicate<String> filter = Predicates.fake();

        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.filter(filter);

        assertNotSame(stream, stream2);

        final long limit = 123;
        final Stream<String> stream3 = stream2.limit(limit);
        assertNotSame(stream2, stream3);

        this.checkPushableStreamStream(stream3,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.filter(filter),
                PushableStreamStreamIntermediate.limit(limit));
    }

    // map..........................................................................................................

    @Test
    public void testStreamMap() {
        final Function<String, String> mapper = Function.identity();

        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.map(mapper);

        assertNotSame(stream, stream2);

        this.checkPushableStreamStream(stream2,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.map(mapper));
    }

    @Test
    public void testStreamMapMap() {
        final Function<String, String> mapper1 = Function.identity();

        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.map(mapper1);

        assertNotSame(stream, stream2);

        final Function<String, String> mapper2 = Function.identity();
        final Stream<String> stream3 = stream2.map(mapper2);

        this.checkPushableStreamStream(stream3,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.map(mapper1),
                PushableStreamStreamIntermediate.map(mapper2));
    }

    @Test
    public void testStreamMapLimit() {
        final Function<String, String> mapper = Function.identity();

        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.map(mapper);

        assertNotSame(stream, stream2);

        final long limit = 123;
        final Stream<String> stream3 = stream2.limit(limit);
        assertNotSame(stream2, stream3);

        this.checkPushableStreamStream(stream3,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.map(mapper),
                PushableStreamStreamIntermediate.limit(limit));
    }

    // mapToInt.........................................................................................................

    @Test
    public void testStreamMapToIntNoValues() {
        this.mapToIntAndCheck();
    }

    @Test
    public void testStreamMapToIntManyValues() {
        this.mapToIntAndCheck("1", "2", "3", "4");
    }

    private void mapToIntAndCheck(final String... values) {
        final Consumer<PushableStreamConsumer<String>> starter = this.starter(values);
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);

        assertArrayEquals(Lists.of(values).stream().mapToInt(Integer::parseInt).toArray(),
                stream.mapToInt(Integer::parseInt).toArray(),
                () -> stream + " values: " + Arrays.toString(values));
    }

    // mapToLong.........................................................................................................

    @Test
    public void testStreamMapToLongNoValues() {
        this.mapToLongAndCheck();
    }

    @Test
    public void testStreamMapToLongManyValues() {
        this.mapToLongAndCheck("1", "2", "3", "4");
    }

    private void mapToLongAndCheck(final String... values) {
        final Consumer<PushableStreamConsumer<String>> starter = this.starter(values);
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);

        assertArrayEquals(Lists.of(values).stream().mapToLong(Long::parseLong).toArray(),
                stream.mapToLong(Long::parseLong).toArray(),
                () -> stream + " values: " + Arrays.toString(values));
    }

    // mapToDouble.........................................................................................................

    @Test
    public void testStreamMapToDoubleNoValues() {
        this.mapToDoubleAndCheck();
    }

    @Test
    public void testStreamMapToDoubleManyValues() {
        this.mapToDoubleAndCheck("1", "2", "3", "4");
    }

    private void mapToDoubleAndCheck(final String... values) {
        final Consumer<PushableStreamConsumer<String>> starter = this.starter(values);
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);

        assertArrayEquals(Lists.of(values).stream().mapToDouble(Double::parseDouble).toArray(),
                stream.mapToDouble(Double::parseDouble).toArray(),
                () -> stream + " values: " + Arrays.toString(values));
    }

    // flatMap..........................................................................................................

    @Test
    public void testStreamFlatMap() {
        final Function<String, Stream<String>> mapper = (s) -> Stream.of(s);

        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.flatMap(mapper);

        assertNotSame(stream, stream2);

        this.checkPushableStreamStream(stream2,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.flatMap(Cast.to(mapper)));
    }

    @Test
    public void testStreamFlatMapCollect() {
        final Function<String, Stream<String>> mapper = (s) -> Stream.of(s);

        final Stream<String> stream2 = this.createStream("a1", "b2", "c3")
                .flatMap(mapper);

        this.collectAndCheck(stream2, "a1", "b2", "c3");
    }

    @Test
    public void testStreamFlatMapCollectIncludesNull() {
        final Function<String, Stream<String>> mapper = (s) -> Stream.of(s);

        final Stream<String> stream2 = this.createStream("a1", null, "c3")
                .flatMap(mapper);

        this.collectAndCheck(stream2, "a1", null, "c3");
    }

    @Test
    public void testStreamFlatMapCollectMultipleValues() {
        final Function<String, Stream<String>> mapper = (s) -> Arrays.stream(s.split(","));

        final Stream<String> stream2 = this.createStream("a1,a2,a3", "b", "c1,c2")
                .flatMap(mapper);

        this.collectAndCheck(stream2, "a1", "a2", "a3", "b", "c1", "c2");
    }

    // peek..........................................................................................................

    private final static Consumer<String> ACTION = (i) -> {
    };

    @Test
    public void testStreamPeek() {
        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.peek(ACTION);

        assertNotSame(stream, stream2);

        this.checkPushableStreamStream(stream2,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.peek(ACTION));
    }

    @Test
    public void testStreamPeekPeek() {
        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.peek(ACTION);

        assertNotSame(stream, stream2);

        final Consumer<String> action2 = (i) -> {
        };
        final Stream<String> stream3 = stream2.peek(action2);

        this.checkPushableStreamStream(stream3,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.peek(ACTION),
                PushableStreamStreamIntermediate.peek(action2));
    }

    @Test
    public void testStreamPeekLimit() {
        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.peek(ACTION);

        assertNotSame(stream, stream2);

        final long limit = 123;
        final Stream<String> stream3 = stream2.limit(limit);
        assertNotSame(stream2, stream3);

        this.checkPushableStreamStream(stream3,
                starter,
                CloseableCollection.empty(),
                PushableStreamStreamIntermediate.peek(ACTION),
                PushableStreamStreamIntermediate.limit(limit));
    }

    // reduce.......................................................................................................

    @Test
    public void testStreamReduce() {
        this.reduceAndCheck(this.createStream("a1", "b2", "c3"),
                (a, b) -> a + b,
                "a1", "b2", "c3");
    }

    @Test
    public void testStreamReduce2() {
        this.reduceAndCheck(this.createStream("a1", "b2", "c3"),
                (a, b) -> a.toUpperCase() + b,
                "a1", "b2", "c3");
    }

    // reduce initial...................................................................................................

    @Test
    public void testStreamReduceInitial() {
        this.reduceAndCheck(this.createStream("a1", "b2", "c3"),
                "@",
                (a, b) -> a + b,
                "a1", "b2", "c3");
    }

    @Test
    public void testStreamReduceInitial2() {
        this.reduceAndCheck(this.createStream("a1", "b2", "c3"),
                "@",
                (a, b) -> a.toUpperCase() + b,
                "a1", "b2", "c3");
    }

    // iterator.......................................................................................................

    @Test
    public void testStreamIterator() {
        final String[] values = new String[]{"a1", "b2", "c3"};
        final PushableStreamStream<String> stream = this.createStream(values);
        this.iterateAndCheck(stream.iterator(), values);
    }

    @Test
    public void testStreamIteratorEmpty() {
        final String[] values = new String[0];
        final PushableStreamStream<String> stream = this.createStream(values);
        this.iterateAndCheck(stream.iterator(), values);
    }

    // spliterator.......................................................................................................

    @Test
    public void testStreamSpliterator() {
        final String[] values = new String[]{"a1", "b2", "c3"};
        final PushableStreamStream<String> stream = this.createStream(values);

        this.iterateAndCheck(Spliterators.iterator(stream.spliterator()), values);
    }

    @Test
    public void testStreamSpliteratorCharacteristics() {
        final Spliterator<String> spliterator = this.createStream().spliterator();
        assertEquals(0, spliterator.characteristics(), () -> "characteristics " + spliterator);
    }

    @Test
    public void testStreamSpliteratorEstimatedSize() {
        final Spliterator<String> spliterator = this.createStream().spliterator();
        assertEquals(Long.MAX_VALUE, spliterator.estimateSize(), () -> "estimateSize " + spliterator);
    }

    @Test
    public void testStreamSpliteratorComparatorFails() {
        final Spliterator<String> spliterator = this.createStream().spliterator();

        assertThrows(IllegalStateException.class, () -> {
            spliterator.getComparator();
        });
    }

    @Test
    public void testStreamSpliteratorGetExactSizeIfKnown() {
        final Spliterator<String> spliterator = this.createStream().spliterator();

        assertEquals(-1, spliterator.getExactSizeIfKnown(), () -> "getExactSizeIfKnown " + spliterator);
    }

    // closeable.......................................................................................................

    @Test
    public void testStreamIntermediateCloseable() {
        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.peek(ACTION);

        assertNotSame(stream, stream2);

        final TestCloseableRunnable closeable1 = TestCloseableRunnable.with("closeable-1");
        final Stream<String> stream3 = stream2.onClose(closeable1);

        assertNotSame(stream2, stream3);

        this.checkPushableStreamStream(stream3,
                starter,
                CloseableCollection.empty().add(closeable1),
                PushableStreamStreamIntermediate.peek(ACTION));
    }

    @Test
    public void testStreamIntermediateCloseableCloseable() {
        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);

        final TestCloseableRunnable closeable1 = TestCloseableRunnable.with("closeable-1");
        final Stream<String> stream2 = stream.onClose(closeable1);
        assertNotSame(stream, stream2);

        final Stream<String> stream3 = stream2.peek(ACTION);
        assertNotSame(stream2, stream3);

        final TestCloseableRunnable closeable2 = TestCloseableRunnable.with("closeable-2");
        final Stream<String> stream4 = stream3.onClose(closeable2);
        assertNotSame(stream, stream4);

        this.checkPushableStreamStream(stream4,
                starter,
                CloseableCollection.empty().add(closeable1).add(closeable2),
                PushableStreamStreamIntermediate.peek(ACTION));
    }

    @Test
    public void testStreamCloseableIntermediateCloseable() {
        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final PushableStreamStream<String> stream = PushableStreamStream.with(starter);
        final Stream<String> stream2 = stream.peek(ACTION);

        assertNotSame(stream, stream2);

        final TestCloseableRunnable closeable1 = TestCloseableRunnable.with("closeable-1");
        final Stream<String> stream3 = stream2.onClose(closeable1);
        assertNotSame(stream2, stream3);

        final TestCloseableRunnable closeable2 = TestCloseableRunnable.with("closeable-2");
        final Stream<String> stream4 = stream3.onClose(closeable2);
        assertNotSame(stream3, stream4);

        this.checkPushableStreamStream(stream4,
                starter,
                CloseableCollection.empty().add(closeable1).add(closeable2),
                PushableStreamStreamIntermediate.peek(ACTION));
    }

    @Test
    public void testStreamCollectCloseableFired() {
        this.closeableFired = 0;

        final Stream<String> stream = this.createStream()
                .onClose(() -> {
                    this.closeableFired++;
                });
        this.collectAndCheck(stream, this.values());
        assertEquals(1, this.closeableFired, "Closeable not fired only once");
    }

    @Test
    public void testStreamCollectCloseableFired2() {
        this.closeableFired = 0;

        final Stream<String> stream = this.createStream()
                .onClose(() -> {
                    this.closeableFired++;
                })
                .onClose(() -> {
                    this.closeableFired += 10;
                });
        this.collectAndCheck(stream, this.values());
        assertEquals(11, this.closeableFired, "Both closeables not fired only once");
    }

    private int closeableFired;

    @Test
    public void testIsParallel() {
        assertEquals(false, this.createStream().isParallel());
    }

    @Test
    public void testParallel() {
        final Stream<String> stream = this.createStream();
        assertSame(stream, stream.parallel());
    }

    @Test
    public void testSequential() {
        final Stream<String> stream = this.createStream();
        assertSame(stream, stream.sequential());
    }

    @Test
    public void testUnordered() {
        final Stream<String> stream = this.createStream();
        assertSame(stream, stream.unordered());
    }

    // helpers..........................................................................................................

    private void checkPushableStreamStream(final Stream<String> pushable,
                                           final Consumer<PushableStreamConsumer<String>> starter,
                                           final CloseableCollection closeables,
                                           final PushableStreamStreamIntermediate... intermediates) {
        this.checkPushableStreamStream0(Cast.to(pushable),
                starter,
                closeables,
                intermediates);
    }

    private void checkPushableStreamStream0(final PushableStreamStream<String> pushable,
                                            final Consumer<PushableStreamConsumer<String>> starter,
                                            final CloseableCollection closeables,
                                            final PushableStreamStreamIntermediate... intermediates) {
        assertSame(starter, pushable.starter, "starter");
        assertEquals(Lists.of(intermediates), pushable.intermediates, "intermediates");
        assertEquals(closeables, pushable.closeables, "closeables");
    }

    // toString..........................................................................................................

    @Test
    public void testToString() {
        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        this.toStringAndCheck(PushableStreamStream.with(starter), starter.toString());
    }

    @Test
    public void testToStringFilter() {
        final Predicate<String> filter = Predicates.fake();

        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final Stream<String> stream = PushableStreamStream.with(starter)
                .filter(filter);

        this.toStringAndCheck(stream, starter + " " + PushableStreamStreamIntermediate.filter(filter));
    }

    @Test
    public void testToStringFilterFilter() {
        final Predicate<String> filter1 = Predicates.fake();
        final Predicate<String> filter2 = Predicates.fake();

        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final Stream<String> stream = PushableStreamStream.with(starter)
                .filter(filter1)
                .filter(filter2);

        this.toStringAndCheck(stream, starter + " filter " + filter1 + " filter " + filter2);
    }

    @Test
    public void testToStringLimit() {
        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final Stream<String> stream = PushableStreamStream.with(starter)
                .limit(123);

        this.toStringAndCheck(stream, starter + " limit 123");
    }

    @Test
    public void testToStringSkipLimit() {
        final Consumer<PushableStreamConsumer<String>> starter = this.starter();
        final Stream<String> stream = PushableStreamStream.with(starter)
                .limit(123)
                .skip(45);

        this.toStringAndCheck(stream, starter + " limit 123 skip 45");
    }

    @Override
    public PushableStreamStream<String> createStream() {
        return PushableStreamStream.with(this.starter);
    }

    private PushableStreamStream<String> createStream(final String...values) {
        return PushableStreamStream.with(this.starter(values));
    }

    @Override
    public PushableStreamStream<String> createObject() {
        return this.createStream();
    }

    private Consumer<PushableStreamConsumer<String>> starter = this.starter(this.values());

    private Consumer<PushableStreamConsumer<String>> starter(final String... values) {
        return this.starter(Lists.of(values));
    }

    private Consumer<PushableStreamConsumer<String>> starter(final List<String> values) {
        return (c) -> {
            final Iterator<String> i = values.iterator();
            for (; ; ) {
                if (c.isFinished() || false == i.hasNext()) {
                    break;
                }
                final String value = i.next();
                c.accept(value);
            }
        };
    }

    @Override
    public List<String> values() {
        return this.values("10,20,30,40,50,60,70,80");
    }

    private List<String> values(final String commaSeparated) {
        return commaSeparated.isEmpty() ?
                Lists.empty() :
                Arrays.asList(commaSeparated.split(","));
    }

    // ClassTesting....................................................................................................

    @Override
    public Class<PushableStreamStream<String>> type() {
        return Cast.to(PushableStreamStream.class);
    }
}
