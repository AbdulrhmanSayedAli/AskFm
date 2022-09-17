package engine;


import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class IDGenerator {
    public static final int User_ID = 0;
    public static final int Question_ID = 1;
    
    
    private static final String ID_FileName = "IDs.txt";
    private static final String DeafultValue = "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0";
    
    
    private static void increaseID(int Column){ 
        try {
            Scanner myReader = FileManager.reader(ID_FileName, true ,DeafultValue);
            String[] last = myReader.nextLine().split(" ");
            last[Column] = "" + (Integer.parseInt(last[Column]) + 1);

            FileWriter writer = FileManager.writer(ID_FileName, true ,DeafultValue);
            for (int i = 0; i < last.length; i++) {
                writer.write(last[i]);
                if (i != last.length - 1) {
                    writer.write(" ");
                }
            }
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
          
    }
    

    public static int generateID(int Column){ 
        Scanner myReader = FileManager.reader(ID_FileName, true ,DeafultValue);
        String[] last = myReader.nextLine().split(" ");
        myReader.close();
        int res = Integer.parseInt(last[Column])+1; 
        increaseID(Column);
        return res;
    }
}
