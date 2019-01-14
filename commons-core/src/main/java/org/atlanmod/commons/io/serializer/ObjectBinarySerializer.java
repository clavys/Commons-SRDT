/*
 * Copyright (c) 2017 Atlanmod.
 *
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v2.0 which accompanies
 * this distribution, and is available at https://www.eclipse.org/legal/epl-2.0/
 */

package org.atlanmod.commons.io.serializer;

import org.atlanmod.commons.Preconditions;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStream;
import java.io.Serializable;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.WillNotClose;

import static org.atlanmod.commons.Preconditions.checkInstanceOf;

/**
 * A {@link BinarySerializer} for any object, using FST serialization.
 *
 * @param <T> the type of (de)serialized objects
 */
@ParametersAreNonnullByDefault
final class ObjectBinarySerializer<T> extends AbstractBinarySerializer<T> {

    @SuppressWarnings("JavaDoc")
    private static final long serialVersionUID = 1069734448451637293L;

    @Nonnull
    @Override
    public byte[] serialize(T t) {
        return FST.asByteArray(t);
    }

    @Nonnull
    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(byte[] data) {
        return (T) FST.asObject(data);
    }

    @Override
    public void serialize(T t, @WillNotClose DataOutput out) throws IOException {
        Preconditions.checkInstanceOf(t, Serializable.class, "Requires a Serializable payload but received an object of type %s", t.getClass().getName());

        if (ObjectOutput.class.isInstance(out)) {
            serialize(t, ObjectOutput.class.cast(out));
        }
        else if (OutputStream.class.isInstance(out)) {
            serialize(t, OutputStream.class.cast(out));
        }
        else {
            throw new IllegalArgumentException(String.format("Unknown stream of type %s", out.getClass().getName()));
        }
    }

    @Nonnull
    @Override
    public T deserialize(@WillNotClose DataInput in) throws IOException {
        if (ObjectInput.class.isInstance(in)) {
            return deserialize(ObjectInput.class.cast(in));
        }
        else if (InputStream.class.isInstance(in)) {
            return deserialize(InputStream.class.cast(in));
        }
        else {
            throw new IllegalArgumentException(String.format("Unknown stream of type %s", in.getClass().getName()));
        }
    }

    /**
     * Write an object of type {@code T} to the given {@code out}.
     * <p>
     * <b>Note:</b> The {@code value} <b>must</b> implement {@link Serializable}.
     *
     * @param t   the object to serialize
     * @param out the output out
     *
     * @throws IOException if an I/O error occurs during the serialization
     */
    private void serialize(T t, @WillNotClose ObjectOutput out) throws IOException {
        out.writeObject(t);
    }

    /**
     * Reads and assembles an object of type {@code T} from the given {@code in}.
     *
     * @param in the input in
     *
     * @return the deserialized object
     *
     * @throws IOException if an I/O error occurs during the deserialization
     */
    @Nonnull
    @SuppressWarnings("unchecked")
    private T deserialize(@WillNotClose ObjectInput in) throws IOException {
        try {
            return (T) in.readObject();
        }
        catch (ClassNotFoundException e) {
            throw new IOException(e);
        }
    }
}
