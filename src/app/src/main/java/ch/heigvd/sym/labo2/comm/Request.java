package ch.heigvd.sym.labo2.comm;

import java.util.HashMap;

/**
 * Represents an HTTP request
 *
 * @author Jael Dubey, Loris Gilliand, Mateo Tutic, Luc Wachter
 * @version 1.0
 * @since 2019-11-08
 */
public class Request {
    private String url;         // Request's URL
    private String data;        // Request's data
    private HashMap<String, String> headers; // Request's headers


    /**
     * Constructor of the request
     *
     * @param url     request's URL
     * @param data    request's data
     * @param headers request's headers
     */
    public Request(String url, String data, HashMap headers) {
        this.url = url;
        this.data = data;
        this.headers = headers;
    }

    /**
     * Return the request's URL
     *
     * @return Request's URL
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * Return the request's data
     *
     * @return Request's data
     */
    public String getData() {
        return this.data;
    }

    /**
     * Return the request's headers
     *
     * @return Request's headers
     */
    public HashMap<String, String> getHeaders() {
        return this.headers;
    }
}
