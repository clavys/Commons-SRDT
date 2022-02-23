/*
 * Copyright (c) 2022 Naomod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */
package org.atlanmod.testing;


public interface Generator <T>  {

    /**
     * Returns a new instance of
     * @return
     */
    T generate();

    /**
     * Returns an array containing all the classes supported by the generator.
     *
     * @return an array containing all generator types
     */
    Class<T>[] types();

}
