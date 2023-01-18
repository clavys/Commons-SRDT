/*
 * Copyright (c) 2023 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.predicate;

import java.time.LocalDate;

/**
 *
 * @author sunye
 * @since 1.1.1
 */
public class LocalDatePredicate extends ObjectPredicate<LocalDatePredicate, LocalDate> {
    public LocalDatePredicate(PredicateContext context, LocalDate value) {
        super(context, value);
    }

    public LocalDatePredicate isAfter(LocalDate other) {
        boolean expected = this.value.isAfter(other);
        if (!expected) {
            context.send("\nExpecting date (%s) to be after (%s)", value, other);
        }
        return this;
    }

    public LocalDatePredicate isBefore(LocalDate other) {
        boolean expected = this.value.isBefore(other);
        if (!expected) {
            context.send("\nExpecting date (%s) to be before (%s)", value, other);
        }
        return this;
    }
}
