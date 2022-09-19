package managers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import models.User;


public class ProfileManager {
    
    private static final String Profile_FileName = "profile.txt";
    private static final String Users_FileName = "users.txt";
    
    public static void signIn(User user) {
        try {
            FileWriter myWriter = FileManager.writer(Profile_FileName, true, "-1");
            String line = user.getId() + " " + user.getName() + " " + user.getEmail() + " " + user.getPassword() + " " + (user.isAllowAnonymousQ()?"1":"0");
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
            String line = user.getId() + " " + user.getName() + " " + user.getEmail() + " " + user.getPassword() + " " + (user.isAllowAnonymousQ()?"1":"0");
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

        res = Integer.parseInt(myReader.nextLine().split(" ")[0]) > 0;
        myReader.close();

        return res;
    }
    
    public static User getCurrentUser(){
        if(!isLoggedIn()) return null;
        Scanner reader = FileManager.reader(Profile_FileName, true , "-1");
        String[] data = reader.nextLine().split(" ");
        
        return new User(Integer.parseInt(data[0]),data[1],data[2],data[3],data[4].equals("1"));
    }
    
    
    public static ArrayList<User> getAllUsers(){
        Scanner reader = FileManager.reader(Users_FileName, true);
        ArrayList<User> res = new ArrayList<>();
        
        while(reader.hasNext()){
            String[] data = reader.nextLine().split(" ");
            res.add(new User(Integer.parseInt(data[0]),data[1],data[2],data[3],data[4].equals("1")));
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
