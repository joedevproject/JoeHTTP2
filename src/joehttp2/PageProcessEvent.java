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
    private String aheaders = "Content-type: text/html\nLanguage: en-US";
    private String bheaders = "HTTP/1.1 200 OK";
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
        out.println(bheaders);
        out.println(aheaders);
        out.println();
        out.println(data);
        try {
            out.close();
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(PageProcessEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        bheaders = "HTTP/1.1 404 NOT FOUND";
    }
    /**
     * This method prepares the JoeHTTP2 backend to send a redirect.
     * <p>
     * Do not use the setResponseHeaders(String newheaders) method
     * when using this method.
     * 
     * @param location The URL in which to send the browser.
     */
    public void sendRedirect(String location) {
        bheaders = "HTTP/1.1 302 REDIRECT";
        aheaders = "Location: "+location;
    }
    /**
     * This method sets the JotHTTP2 backend to send a 403 Forbidden
     * to the browser.
     */
    public void send403Forbidden() {
        bheaders = "HTTP/1.1 403 FORBIDDEN";
    }
    /**
     * Gets the request headers the browser sent to get the page
     * 
     * @return The request headers
     */
    public String getRequestHeaders() {
        String headers = "";
        String[] headerlines = requestheaders.split("\n");
        for(int i=0;i<headerlines.length;i++) {
            if(!headerlines[i].startsWith("GET")) {
                if(headers.equalsIgnoreCase("")) {
                    headers = headerlines[i];
                } else {
                    headers = headers+"\n"+headerlines[i];
                }
            }
        }
        return requestheaders;
    }
    /**
     * This method sets the headers to send to the browser
     * 
     * @param newheaders The headers to send to the web browser
     */
    public void setResponseHeaders(String newheaders) {
        bheaders = newheaders;
    }
}
