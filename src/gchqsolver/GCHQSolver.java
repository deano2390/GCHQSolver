/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gchqsolver;

import com.google.gson.Gson;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Ï
 *
 * @author deanwild
 */
public class GCHQSolver {

    public static void main(String[] args) {

        try {
            String json = readFile("//Users/deanwild/Documents/gchq.json", Charset.defaultCharset());
                    
            Gson gson = new Gson();
            Grid grid = gson.fromJson(json, Grid.class);
            
            grid.solve();
            
        } catch (IOException ex) {
            Logger.getLogger(GCHQSolver.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}
