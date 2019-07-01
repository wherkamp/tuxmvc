package me.kingtux.tuxmvc.simple.impl;

import me.kingtux.tuxmvc.core.request.UploadedFile;

import java.io.InputStream;

public class SimpleUploadedFile implements UploadedFile {
    private io.javalin.http.UploadedFile jUF;

    public SimpleUploadedFile(io.javalin.http.UploadedFile jUF) {
        this.jUF = jUF;

    }


    @Override
    public String contentType() {
        return jUF.getContentType();
    }

    @Override
    public String name() {
        return jUF.getFilename();
    }

    @Override
    public String extension() {
        return jUF.getExtension();
    }

    @Override
    public InputStream content() {
        return jUF.getContent();
    }
}
