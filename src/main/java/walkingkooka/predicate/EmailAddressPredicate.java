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

package walkingkooka.predicate;

import walkingkooka.net.email.EmailAddress;

import java.util.function.Predicate;

/**
 * A {@link Predicate} that returns true if the {@link String} is an email.
 */
final class EmailAddressPredicate implements Predicate<String> {

    /**
     * Singleton
     */
    final static EmailAddressPredicate INSTANCE = new EmailAddressPredicate();

    /**
     * Private ctor use singleton
     */
    private EmailAddressPredicate() {
        super();
    }

    @Override
    public boolean test(final String email) {
        return EmailAddress.tryParse(email).isPresent();
    }

    @Override
    public String toString() {
        return EmailAddress.class.getSimpleName();
    }
}
