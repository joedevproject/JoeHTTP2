/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package joehttp2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aweso_000
 */
public class HTTPPage {
    private File f;
    private String errormessage = "";
    public HTTPPage(File f) {
        this.f = f;
    }
    String getLines() {
        String lines = "";
        try {
            BufferedReader r = new BufferedReader(new FileReader(f));
            String line = "";
            while((line = r.readLine()) != null) {
                if(lines.equalsIgnoreCase("")) {
                    lines = line;
                } else {
                    lines = lines+"\n"+line;
                }
            }
            return lines;
        } catch (FileNotFoundException ex) {
            errormessage = ex.getMessage();
            Logger.getLogger(HTTPPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            errormessage = ex.getMessage();
            Logger.getLogger(HTTPPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sendErrorPage();
    }
    String sendErrorPage() {
        String lines = "";
        try {
            BufferedReader r = new BufferedReader(new FileReader(f));
            String line = "";
            while((line = r.readLine()) != null) {
                if(lines.equalsIgnoreCase("")) {
                    line.replaceAll("[errormessage]", errormessage);
                    lines = line;
                } else {
                    line.replaceAll("[errormessage]", errormessage);
                    lines = lines+"\n"+line;
                }
            }
            return lines;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HTTPPage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HTTPPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
}
