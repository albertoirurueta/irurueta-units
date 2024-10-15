package com.irurueta.units;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializationHelper {

    public static <T extends Serializable> byte[] serialize(final T object) throws IOException {
        try (final var byteArrayOutputStream = new ByteArrayOutputStream()) {
            try (final var objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                objectOutputStream.writeObject(object);
            }

            byteArrayOutputStream.flush();

            return byteArrayOutputStream.toByteArray();
        }
    }

    public static <T extends Serializable> T deserialize(final byte[] bytes)
            throws IOException, ClassNotFoundException {
        try (final var byteArrayInputStream = new ByteArrayInputStream(bytes)) {
            try (final var objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
                //noinspection unchecked
                return (T) objectInputStream.readObject();
            }
        }
    }
}
