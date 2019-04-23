package me.kingtux.tuxmvc.core.request;

public enum MimeType {

    TEXT("text/plain"),
    HTML("text/html", "html"),
    CSS("text/css", "css"),
    JPEG("image/jpeg", "jpg", "jpeg"),
    PNG("image/png", "png"),
    GIF("image/gif", "gif"),
    BMP("image/bmp"),
    WEBP("image/webp"),
    MPEG("audio/mpeg", "mp3"),
    OGG("audio/ogg"),
    MIDI("audio/midi"),
    WEBM("audio/webm"),
    WAV("audio/wav"),
    MP4("video/mp4", "mp4"),
    JSON("application/json"),
    JAVASCRIPT("application/javascript", "js"),
    ECMASCRIPT("application/ecmascript"),
    OCTETSTREAM("application/octet-stream"),
    XML("application/xml", "xml"),
    XHTMLXML("application/xhtml+xml"),
    VNDMSPowerPoint("application/vnd.mspowerpoint"),
    PDF("application/pdf"),
    SVG("image/svg+xml");

    private String mimeType;
    private String[] extensions;

    MimeType(String mimeType, String... extensions) {
        this.mimeType = mimeType;
        this.extensions = extensions;
    }

    MimeType(String mimeType) {
        this.mimeType = mimeType;
        extensions = "".split("");
    }

    public String getMimeType() {
        return mimeType;
    }

    public static MimeType getMimeTypeFromExtension(String extension) {
        for (MimeType type : values()) {
            for (String s : type.extensions) {
                if (s.equalsIgnoreCase(extension)) {
                    return type;
                }

            }
        }
        return TEXT;
    }
}