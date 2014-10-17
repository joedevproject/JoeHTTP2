package joehttp2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is used for the event in which the
 * web browser is ready to accept data.
 * 
 * @author Joe
 */
public class PageProcessEvent {
    private Socket s = null;
    private String headers = "HTTP/1.1 200 OK\nContent-type: text/html\nLanguage: en-US";
    private String requested = "";
    private String data = "";
    private PrintWriter out = null;
    private String requestheaders = "";
    /**
     * This object is created by JoeHTTP2 and is not
     * meant to be created by anything other than
     * the JoeHTTP2 Backend.
     * 
     * @param   s   The socket in which to communicate with
     * @param   requested   The page that was requested
     * @param   requestheaders  The headers that the page was requested with
     */
    PageProcessEvent(Socket s, String requested, String requestheaders) {
        this.s = s;
        this.requested = requested;
        try {
            out = new PrintWriter(s.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(PageProcessEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.requestheaders = requestheaders;
    }
    /**
     * This method is used for getting the IP address of the requester.
     * 
     * @return The IP address that requested the page
     */
    public String getInetAdress() {
        return s.getInetAddress().toString();
    }
    /**
     * This method is used for getting the port that the browser is communicating
     * through.
     * 
     * @return The port in which the browser opened to communicate.
     */
    public int getPort() {
        return s.getPort();
    }
    /**
     * This method is used to set what to send (In String Format)
     * 
     * @param   HTML    The HTML to send
     */
    public void setHTML(String HTML) {
        data = HTML;
    }
    /**
     * This method is used to set what to send (In HTTPPage Format)
     * 
     * @param   p   The HTTPPage object
     * @see HTTPPage
     */
    public void setHTML(HTTPPage p) {
        data = p.getLines();
    }
    /**
     * This method sends the webpage to the browser.
     */
    public void sendToBrowser() {
        out.println(headers);
        out.println();
        out.println(data);
        try {
            out.close();
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(PageProcessEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(headers);
    }
    /**
     * This method is used for getting the URL that was requested.
     * 
     * @return The requested URL
     */
    public String getURL() {
        return requested;
    }
    /**
     * This method sets the JotHTTP2 backend to send a 404 Not Found
     * to the browser.
     */
    public void send404NotFound() {
        headers = "HTTP/1.1 404 NOT FOUND\nContent-type: text/html\nLanguage: en-US";
    }
    /**
     * This method prepares the JoeHTTP2 backend to send a redirect.
     * 
     * @param location The URL in which to send the browser.
     */
    public void sendRedirect(String location) {
        headers = "HTTP/1.1 302 REDIRECT\nLocation: "+location;
    }
    /**
     * This method sets the JotHTTP2 backend to send a 403 Forbidden
     * to the browser.
     */
    public void send403Forbidden() {
        headers = "HTTP/1.1 403 FORBIDDEN\nContent-type: text/html\nLanguage: en-US";
    }
    /**
     * Gets the request headers the browser sent to get the page
     * (Includes the "GET" or "POST" portion of the request.
     * 
     * @return The request headers
     */
    public String getRequestHeaders() {
        return requestheaders;
    }
    /**
     * This method sets the headers to send to the browser
     * (the "HTTP/1.1" part is not automatically put in)
     * 
     * @param newheaders The headers to send to the web browser
     */
    public void setResponseHeaders(String newheaders) {
        headers = newheaders;
    }
}
