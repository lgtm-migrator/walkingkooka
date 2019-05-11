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

public final class LimitPushableStreamStreamIntermediateTest extends LimitOrSkipPushableStreamStreamIntermediateTestCase<LimitPushableStreamStreamIntermediate> {

    @Override
    LimitPushableStreamStreamIntermediate createPushableStreamStreamIntermediate(final long skipOrLimitValue) {
        return LimitPushableStreamStreamIntermediate.with(skipOrLimitValue);
    }

    @Override
    public Class<LimitPushableStreamStreamIntermediate> type() {
        return LimitPushableStreamStreamIntermediate.class;
    }
}
