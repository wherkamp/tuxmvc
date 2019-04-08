package me.kingtux.tuxmvc.core.request;

public enum MimeType {

    TEXT("text/plain"),
    HTML("text/html"),
    CSS("text/css"),
    JPEG("image/jpeg"),
    PNG("image/png"),
    GIF("image/gif"),
    BMP("image/bmp"),
    WEBP("image/webp"),
    MPEG("audio/mpeg"),
    OGG("audio/ogg"),
    MIDI("audio/midi"),
    WEBM("audio/webm"),
    WAV("audio/wav"),
    MP4("video/mp4"),
    JSON("application/json"),
    JAVASCRIPT("application/javascript"),
    ECMASCRIPT("application/ecmascript"),
    OCTETSTREAM("application/octet-stream"),
    XML("application/xml"),
    XHTMLXML("application/xhtml+xml"),
    VNDMSPowerPoint("application/vnd.mspowerpoint"),
    PDF("application/pdf"),
    SVG("image/svg+xml");

    private String mimeType;

    MimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return mimeType;
    }
}