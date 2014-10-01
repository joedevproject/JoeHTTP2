package joehttp2;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aweso_000
 */
public class JoeHTTP2 {

    private ServerSocket s = null;
    private PageProcessListener l = null;
    
    public JoeHTTP2(int port) {
        try {
            s = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(JoeHTTP2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
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
