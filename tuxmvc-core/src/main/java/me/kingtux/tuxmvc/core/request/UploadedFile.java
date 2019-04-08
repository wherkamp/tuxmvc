package me.kingtux.tuxmvc.core.request;

import java.io.*;

/**
 * This represents an uploaded file.
 *
 * @author KingTux
 */
public interface UploadedFile {
    /**
     * The content type
     *
     * @return the content type
     */
    String contentType();


    /**
     * The file name
     *
     * @return the name
     */
    String name();

    /**
     * The file extension
     *
     * @return the extension
     */
    String extension();

    InputStream content();

    /**
     * Copies the file to a location
     *
     * @param file the destination file
     */
    default void copy(File file) {
        try (OutputStream output = new FileOutputStream(file)) {
            InputStream input = content();
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    default void copy(String path) {
        copy(new File(path));
    }
}
