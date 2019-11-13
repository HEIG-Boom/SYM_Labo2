package ch.heigvd.sym.labo2.comm;

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
    private String contentType; // Request's content type

    /**
     * Constructor of the request
     *
     * @param url         request's URL
     * @param data        request's data
     * @param contentType request's content type
     */
    public Request(String url, String data, String contentType) {
        this.url = url;
        this.data = data;
        this.contentType = contentType;
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
     * Return the request's content type
     *
     * @return Request's content type
     */
    public String getContentType() {
        return this.contentType;
    }
}
