/*
 * Copyright (c) 2021 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.predicate;

import java.util.Objects;

/**
 *
 * @author sunye
 * @since 1.1.0
 */
public class StringPredicate extends Predicate {
    private final String value;


    public StringPredicate(PredicateContext context, String value) {
        super(context);
        this.value = value;
    }

    public StringPredicate isNull() {
        if (!Objects.isNull(value)) {
            context.send("\nExpecting value to be null");
        }
        return this;
    }

    public StringPredicate isNotNull() {
        if (!Objects.isNull(value)) {
            context.send("\nExpecting value to be non null");
        }
        return this;
    }

    public StringPredicate contains(String substr) {
        if (!value.contains(substr)) {
            context.send("");
        }
        return this;
    }
}

