package askfm.managers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import askfm.models.User;


public class ProfileManager {
    
    private static final String Profile_FileName = "profile.txt";
    private static final String Users_FileName = "users.txt";
    
    public static void signIn(User user) {
        try {
            FileWriter myWriter = FileManager.writer(Profile_FileName, true, "-1");
            String line = SerializationManager.toString(user);
            myWriter.write(line);
            myWriter.close();

            FileWriter users = FileManager.appender(Users_FileName, true);
            users.write(line + "\n");
            users.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public static void logIn(User user){
        try {
            FileWriter myWriter = FileManager.writer(Profile_FileName, true, "-1");
            String line = SerializationManager.toString(user);
            myWriter.write(line);
            myWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public static void logOut() {
        try {
            FileWriter myWriter = FileManager.writer(Profile_FileName, true , "-1");
            myWriter.write("-1");
            myWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }
    

    public static boolean isLoggedIn() {
        boolean res = false;
        Scanner myReader = FileManager.reader(Profile_FileName, true , "-1");
        if(!myReader.nextLine().equals("-1")) res = true;
        myReader.close();
        return res;
    }
    
    public static User getCurrentUser(){
        if(!isLoggedIn()) return null;
        Scanner reader = FileManager.reader(Profile_FileName, true , "-1");
        
        return (User)SerializationManager.fromString(reader.nextLine());
    }
    
    
    public static ArrayList<User> getAllUsers(){
        Scanner reader = FileManager.reader(Users_FileName, true);
        ArrayList<User> res = new ArrayList<>();
        
        while(reader.hasNext()){
            String line = reader.nextLine();
            if(line.equals(""))continue;
            res.add((User)SerializationManager.fromString(line));
        }
        
        return res;
    }
    
    public static User getUser (int id){
        for(User user : getAllUsers()){
            if(user.getId()==id)return user;
        }
        return null;
    }
    
}
