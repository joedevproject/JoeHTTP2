package joehttp2;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is what starts the server.
 * 
 * @author Joe
 */
public class JoeHTTP2 {

    private ServerSocket s = null;
    private PageProcessListener l = null;
    
    /**
     * This constructor defines a new server.
     * 
     * @param port The port to start listening on
     */
    public JoeHTTP2(int port) {
        try {
            s = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(JoeHTTP2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Starts the process of listening
     * 
     * @param l The listener in which to use.
     */
    public void startListening(PageProcessListener l) {
        this.l = l;
        while(true) {
            try {
                Thread t = new Thread(new PreThread(s.accept(),l));
                t.start();
            } catch (IOException ex) {
                Logger.getLogger(JoeHTTP2.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
