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

package walkingkooka.routing;

import walkingkooka.build.Builder;
import walkingkooka.build.BuilderException;
import walkingkooka.collect.map.Maps;
import walkingkooka.naming.Name;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * A {@link Builder} that requires one or more {@link Routing routes} and returns a {@link RouterBuilderRouter}.
 * The {@link RouterBuilderRouter} may then be used to match a single Routing given parameters which satisfy ALL conditions of the route.
 */
public final class RouterBuilder<T> implements Builder<Router<T>> {

    public static <T> RouterBuilder<T> create() {
        return new RouterBuilder<T>();
    }

    private RouterBuilder() {
        super();
    }

    public RouterBuilder<T> add(final Routing<T> route) {
        Objects.requireNonNull(route, "route");

        final Map<Name, Predicate<Object>> remaining = Maps.ordered();
        remaining.putAll(route.nameToCondition);

        this.root = this.root.add(route.target, remaining);
        return this;
    }

    @Override
    public Router<T> build() throws BuilderException {
        return this.root.build();
    }

    private RouterBuilderRouter<T> root = RouterBuilderRouterNull.get();

    @Override
    public String toString() {
        return this.root.toString();
    }
}
