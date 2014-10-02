package joehttp2;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aweso_000
 */
public class PageProcessEvent {
    private Socket s = null;
    private String headers = "HTTP/1.1 200 OK\nContent-type=text/html\nLanguage: en-US";
    private String requested = "";
    private String data = "";
    private PrintWriter out = null;
    PageProcessEvent(Socket s, String requested) {
        this.s = s;
        this.requested = requested;
        try {
            out = new PrintWriter(s.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(PageProcessEvent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String getInetAdress() {
        return s.getInetAddress().toString();
    }
    public int getPort() {
        return s.getPort();
    }
    public void setHTML(String HTML) {
        data = HTML;
    }
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
    }
    public String getURL() {
        return requested;
    }
}
