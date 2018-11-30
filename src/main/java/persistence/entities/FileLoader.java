package persistence.entities;

import java.net.URL;
import java.nio.file.NoSuchFileException;

public interface FileLoader {
    default String getFilePath(String str) throws NoSuchFileException {
        ClassLoader cl = this.getClass().getClassLoader();
        URL url = cl.getResource(str);
        if (url == null) {
            throw new NoSuchFileException("can't find file: " + str);
        }
        else
            return url.getFile();
    }
}
