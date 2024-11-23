package com.battleship.battleshipfpoe.model;

import java.io.*;

/**
 * Handles serialization and deserialization of objects to and from files.
 * This class implements the {@link ISerializableFileHandler} interface and provides
 * methods to save objects as serialized files and retrieve them back.
 * It is used to persist the state of objects during the game.
 *
 * @author Maycol Andres Taquez Carlosama
 * @code 2375000
 * @author Santiago Valencia Aguiño
 * @code 2343334
 * @author Joel Andres Ochoa Sará
 * @code 2341100
 * @version 1.0
 * @since 1.0
 */
public class SerializableFileHandler implements ISerializableFileHandler{

    /**
     * Deserializes an object from a file.
     * This method reads a serialized object from the specified file and reconstructs it.
     * If the file does not exist or contains invalid data, it returns {@code null}.
     *
     * @param fileName the name of the file to read from.
     * @return the deserialized object, or {@code null} if deserialization fails.
     * @since 1.0
     * @throws IOException if an I/O error occurs during the read operation.
     * @throws ClassNotFoundException if the class of the serialized object cannot be found.
     * @see ObjectInputStream
     * @see FileInputStream
     */
    @Override
    public void serialize(String fileName, Object element) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(element);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserializes an object from a file.
     * This method reads a serialized object from the specified file and reconstructs it.
     * If the file does not exist or contains invalid data, it returns {@code null}.
     *
     * @param fileName the name of the file to read from.
     * @return the deserialized object, or {@code null} if deserialization fails.
     * @since 1.0
     * @throws IOException if an I/O error occurs during the read operation.
     * @throws ClassNotFoundException if the class of the serialized object cannot be found.
     * @see ObjectInputStream
     * @see FileInputStream
     */
    @Override
    public Object deserialize(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Object) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
