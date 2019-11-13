package ch.heigvd.sym.labo2.comm;

import java.util.HashMap;

public class Request {
    private String url;
    private String data;
    private HashMap<String, String> headers;

    public Request(String url, String data, HashMap headers) {
        this.url = url;
        this.data = data;
        this.headers = headers;
    }

    public String getUrl() {
        return this.url;
    }

    public String getData() {
        return this.data;
    }

    public HashMap<String, String> getHeaders() {
        return this.headers;
    }
}
