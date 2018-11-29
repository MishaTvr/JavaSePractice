package persistence.entities;

import java.net.URL;

public interface FileLoader {
    default String getFilePath(String str) {
        ClassLoader cl = this.getClass().getClassLoader();
        URL url = cl.getResource(str);
        if (url == null) {
            return "";
        }
        else
            return url.getFile();
    }
}
