package com.battleship.battleshipfpoe.model;

import java.io.*;

/**
 * Handles reading and writing operations for plain text files.
 * This class provides methods to write content to a file and read content from a file,
 * implementing the {@link IPlaneTextFileHandler} interface.
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
public class PlaneTextFileHandler implements IPlaneTextFileHandler{

    /**
     * Writes the specified content to a file with the given filename.
     * If the file already exists, its content will be overwritten.
     *
     * @param fileName the name of the file to write to.
     * @param content the content to write into the file.
     * @since 1.0
     * @throws IOException if an I/O error occurs while writing to the file.
     * @see BufferedWriter
     */
    @Override
    public void writeToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads content from a file with the given filename.
     * The method reads the file line by line, trims each line, and appends a comma (`,`) between them.
     * The resulting content is split by commas and returned as an array of strings.
     *
     * @param fileName the name of the file to read from.
     * @return an array of strings representing the lines of the file split by commas.
     * @since 1.0
     * @throws IOException if an I/O error occurs while reading the file.
     * @see BufferedReader
     */
    @Override
    public String[] readFromFile(String fileName) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line.trim()).append(",");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString().split(",");
    }
}
