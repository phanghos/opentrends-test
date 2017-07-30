package org.taitasciore.android.model;

import java.io.Serializable;

/**
 * Created by roberto on 10/05/17.
 */

public class Image implements Serializable {

    private String path;
    private String extension;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
