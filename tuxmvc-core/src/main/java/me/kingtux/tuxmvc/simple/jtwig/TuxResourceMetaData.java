package me.kingtux.tuxmvc.simple.jtwig;

import com.google.common.base.Optional;
import org.jtwig.resource.metadata.ResourceMetadata;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TuxResourceMetaData implements ResourceMetadata {
    private String value;

    public TuxResourceMetaData(String value) {
        this.value = value;
    }

    @Override
    public boolean exists() {
        return true;
    }

    @Override
    public InputStream load() {
        if(value==null){
            System.out.println("Value Is Null!");
            return null;
        }
        return new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public Optional<Charset> getCharset() {
        return Optional.of(Charset.forName("UTF-8"));
    }

    @Override
    public Optional<URL> toUrl() {
        return Optional.absent();
    }
}
