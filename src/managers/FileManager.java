package managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileManager {
    
    private static final String FolderName = "data";
    

    public static Boolean Exists(String fileName){
        File file = new File(FolderName+"/"+fileName);
        return file.exists();
    }
    
    
    public static Scanner reader(String fileName , boolean tryAgain){
        try {
            return new Scanner(new File(FolderName+"/"+fileName));
        } catch (FileNotFoundException ex) {
            if(tryAgain){
                createFile(fileName,"");
                return reader(fileName, false);
            } else {
                ex.printStackTrace();
                return null;
            }
        }
    }
    
    
    
    public static Scanner reader(String fileName , boolean tryAgain , String defaultValue){
        try {
            return new Scanner(new File(FolderName+"/"+fileName));
        } catch (FileNotFoundException ex) {
            if(tryAgain){
                createFile(fileName,defaultValue);
                return reader(fileName, false);
            } else {
                ex.printStackTrace();
                return null;
            }
        }
    }
    
    
    public static FileWriter writer(String fileName , boolean tryAgain){
        try {
            return new FileWriter(FolderName+"/"+fileName);
        } catch (FileNotFoundException ex) {
            if(tryAgain){
                createFile(fileName,"");
                return writer(fileName,false);
            } else {
                ex.printStackTrace();
                return null;
            }
        } catch (IOException ex2){
              ex2.printStackTrace();
              return null;
        }
    }
    
    
    public static FileWriter writer(String fileName , boolean tryAgain , String defaultValue){
        try {
            return new FileWriter(FolderName+"/"+fileName);
        } catch (FileNotFoundException ex) {
            if(tryAgain){
                createFile(fileName,defaultValue);
                return writer(fileName,false);
            } else {
                ex.printStackTrace();
                return null;
            }
        } catch (IOException ex2){
              ex2.printStackTrace();
              return null;
        }
    }
    
    
    public static FileWriter appender(String fileName, boolean tryAgain) {
        try {
            return new FileWriter(FolderName + "/" + fileName, true);
        } catch (FileNotFoundException ex) {
            if (tryAgain) {
                createFile(fileName,"");
                return appender(fileName, false);
            } else {
                ex.printStackTrace();
                return null;
            }
        } catch (IOException ex2) {
            ex2.printStackTrace();
            return null;
        }
    }
    
    
    public static FileWriter appender(String fileName, boolean tryAgain , String defaultValue) {
        try {
            return new FileWriter(FolderName + "/" + fileName, true);
        } catch (FileNotFoundException ex) {
            if (tryAgain) {
                createFile(fileName,defaultValue);
                return appender(fileName, false);
            } else {
                ex.printStackTrace();
                return null;
            }
        } catch (IOException ex2) {
            ex2.printStackTrace();
            return null;
        }
    }
    
    
    private static void creatRootFolder(){
        File root = new File(FolderName);
        if (!root.exists()) {
            root.mkdirs();
        }
    }
            
    private static void createFile(String fileName,String defaultValue) {
        creatRootFolder();  
        try {
            File myObj = new File(FolderName + "/" + fileName);
            if(myObj.createNewFile()){
                FileWriter writer = new FileWriter(myObj);
                writer.write(defaultValue);
                writer.close();
            }
      
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
}
