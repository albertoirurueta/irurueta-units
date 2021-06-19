package com.irurueta.units;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializationHelper {

    public static <T extends Serializable> byte[] serialize(final T object) throws IOException {
        try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            try (final ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                objectOutputStream.writeObject(object);
            }

            byteArrayOutputStream.flush();

            return byteArrayOutputStream.toByteArray();
        }
    }

    public static <T extends Serializable> T deserialize(final byte[] bytes)
            throws IOException, ClassNotFoundException {
        try (final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes)) {
            try (final ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
                //noinspection unchecked
                return (T) objectInputStream.readObject();
            }
        }
    }
}
