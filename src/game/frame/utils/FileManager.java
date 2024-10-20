package game.frame.utils;

import game.frame.Dashboard;
import game.frame.NewUser;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {
    public static void writeTheFile(String content, String root) {
        File file = new File(root);
        try {
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(content);

            bw.flush();
            fw.flush();
            bw.close();
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String readTheFile(String root) {
        String string = "";
        try {
            File file = new File(root);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String temp;
            while((temp=br.readLine()) != null){
                string += temp;
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex){
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return string;
    }
}
