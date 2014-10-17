package joehttp2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is for processing the main
 * request.
 * 
 * @author Joe
 */
public class PreThread implements Runnable{
    
    private Socket user = null;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private boolean running = true;
    private PageProcessListener e = null;
    private String GetURL = "";
    private String requestheaders = "";
    private boolean request = false;
    
    /**
     * NOTE: This constructor is to NEVER be used outside
     * of the JoeHTTP2 backend.
     * 
     * @param   s   The socket in which to communicate through
     * @param   e   The listener in which to activate when ready to send the page.
     */
    PreThread(Socket s,PageProcessListener e) {
        user = s;
        this.e = e;
        try {
            in = new BufferedReader(new InputStreamReader(user.getInputStream()));
            out = new PrintWriter(user.getOutputStream(), true);
        } catch (IOException ex) {
            Logger.getLogger(PreThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("[CONNECTION ESTABLISHED: "+user.getInetAddress()+"] PORT "+user.getPort());
    }

    /**
     * This is the main running thread.
     * <p>
     * NOTE: This method is to NEVER be used outside
     * of the JoeHTTP2 backend!
     */
    public void run() {
        while(running) {
            String input = "";
            try {
                input = in.readLine();
            } catch (IOException ex) {
                System.out.println("[CONNECTION CLOSED: "+user.getInetAddress()+"] PORT "+user.getPort());
                running = false;
            }
            if(input == null) {
                System.out.println("[CONNECTION CLOSED: "+user.getInetAddress()+"] PORT "+user.getPort());
                running = false;
            } else if(input.equalsIgnoreCase("")) {
                request = false;
                e.onPageProcess(new PageProcessEvent(user,GetURL,requestheaders));
                try {
                    user.close();
                    in.close();
                    out.close();
                    System.out.println("[CONNECTION FINISHED: "+user.getInetAddress()+"] PORT "+user.getPort());
                    running = false;
                } catch (IOException ex) {
                    Logger.getLogger(PreThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if(input.startsWith("GET")) {
                GetURL = input.replaceAll(" HTTP/1.1", "").replaceAll("GET ", "");
                request = true;
            }
            if(request) {
                if(requestheaders.equalsIgnoreCase("")) {
                    requestheaders = input;
                } else {
                    requestheaders = requestheaders+"\n"+input;
                }
            }
        }
    }
}
