package ch.heigvd.sym.labo2.comm;

import java.util.EventListener;

/**
 * Interface definition of an event listener
 *
 * @since 2019-11-08
 * @version 1.0
 */
public interface CommunicationEventListener extends EventListener {
    /**
     * Implement this method to handle the server's response
     *
     * @param response The server response
     * @return true if everything went well
     */
    boolean handleServerResponse(String response);
}
