package joehttp2;

import java.net.Socket;

/**
 *
 * @author aweso_000
 */
public class PageProcessEvent {
    private Socket s = null;
    private String headers = "";
    private String requested = "";
    private String data = "";
    PageProcessEvent(Socket s, String requested) {
        this.s = s;
    }
    public String getInetAdress() {
        return s.getInetAddress().toString();
    }
    public int getPort() {
        return s.getPort();
    }
}
