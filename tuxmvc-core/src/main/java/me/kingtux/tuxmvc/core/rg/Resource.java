package me.kingtux.tuxmvc.core.rg;

import java.net.URL;
import java.util.Optional;

public class Resource {

    private byte[] value;
    private URL url;
    private Optional<String> fileType;

    public Resource(byte[] value, URL url, String fileType) {
        this.value = value;
        this.url = url;
        this.fileType = Optional.ofNullable(fileType);
    }

    public byte[] getValue() {
        return value;
    }

    public URL getUrl() {
        return url;
    }

    public Optional<String> getFileType() {
        return fileType;
    }
}
