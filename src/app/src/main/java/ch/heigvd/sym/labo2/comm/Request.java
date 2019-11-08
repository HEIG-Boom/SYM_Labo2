package ch.heigvd.sym.labo2.comm;

public class Request {
    private String url;
    private String data;
    private String contentType;

    public Request(String url, String data, String contentType) {
        this.url = url;
        this.data = data;
        this.contentType = contentType;
    }

    public String getUrl() {
        return this.url;
    }

    public String getData() {
        return this.data;
    }

    public String getContentType() {
        return this.contentType;
    }
}
