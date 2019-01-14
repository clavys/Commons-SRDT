/*
 * Copyright (c) 2017-2020 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.reflect;

import org.atlanmod.commons.AbstractTest;

import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.RandomAccess;
import java.util.Set;

import javax.annotation.ParametersAreNonnullByDefault;

import static org.assertj.core.api.Assertions.assertThat;

@ParametersAreNonnullByDefault
class MoreReflectionTest extends AbstractTest {

    @Test
    void getAllInterfaces() {
        assertImplements(Object.class);
        assertImplements(String.class, CharSequence.class, Comparable.class, Serializable.class);
        assertImplements(ArrayList.class, List.class, Collection.class, Iterable.class, RandomAccess.class, Cloneable.class, Serializable.class);
    }

    private void assertImplements(Class<?> type, Class<?>... interfaces) {
        Set<Class<?>> set = MoreReflection.getAllInterfaces(type);
        assertThat(set).containsExactlyInAnyOrder(interfaces);
    }
}