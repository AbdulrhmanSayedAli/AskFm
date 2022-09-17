package authentication;

import engine.Engine;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import models.User;


public class LoginPage {
   
    
    private static User searchForUser(String email , String password){
         ArrayList<User> users = ProfileManager.getAllUsers();
         for (User user : users){
             if(user.getEmail().equals(email)&&user.getPassword().equals(password))
                 return user;
         }
         
         return null;
    }
    
    private static String [] dataToEnter = {
       "email",
       "password"
    };
    
    private static void login(ArrayList<String> data){
        String message = "<html><body><h2>login</h2>";
        for (int i=0;i<data.size();i++)message+="<p>"+dataToEnter[i]+" : "+data.get(i)+"</p>";
        if(data.size()<2) message += "<p style=\"color:blue\">enter your "+dataToEnter[data.size()]+" (all spaces will be replaced with / )</p>";
        message+="</body></html>";
        Engine.changeText(message);
        
        
        if(data.size()<2)
        Engine.openTextField((String text) -> {
            data.add(text.replaceAll(" ", "/"));
            login(data);
        });
        else {
            User user = searchForUser(data.get(0),data.get(1));
           if(user!=null){
               ProfileManager.logIn(user);
               Engine.restart();
           } else {
                Engine.changeText("<html><body><h2>sign up</h2><p>user not found!!</p><p style=\"color:blue\">click 3 to go back.</p></body></html>");
                Engine.removeEventListeners();
                Map<Integer, Runnable> events = new HashMap<>();

                events.put(KeyEvent.VK_3, () -> {
                    Engine.restart();
                });

                Engine.addEventListener(events);
           }
        }
    }
      
    public static void openLoginPage() {
        Engine.removeEventListeners();
        Engine.changeText("login");
        login(new ArrayList<>());

    }
}
