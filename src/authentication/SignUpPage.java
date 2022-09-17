
package authentication;

import engine.Engine;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import models.User;

public class SignUpPage {   
    
    private static Boolean isEmailUsed(String email){
        ArrayList<User> users = ProfileManager.getAllUsers();
        for (User user : users){
            System.out.println(user.getEmail());
            if(user.getEmail().equals(email)) return true;
        }
        return false;
    }
    
    private static String [] dataToEnter = {
       "user name",
       "email",
       "password",
       "allow anonymous questions? (0 or 1)"
    };
    
    private static void signUp (ArrayList<String> data){
        String message = "<html><body><h2>sign up</h2>";
        for (int i=0;i<data.size();i++)message+="<p>"+dataToEnter[i]+" : "+data.get(i)+"</p>";
        if(data.size()<4) message+="<p style=\"color:blue\">enter your "+dataToEnter[data.size()]+" (all spaces will be replaced with / )</p>";
        message+="</body></html>";
        Engine.changeText(message);
        
        
        if(data.size()<4)
        Engine.openTextField((String text) -> {
            if(data.size()<4||text.equals("0")||text.equals("1")){
                 data.add(text.replaceAll(" ", "/"));
            }
           signUp(data);
        });
        else {
            
            if(isEmailUsed(data.get(1))){
                Engine.changeText("<html><body><h2>sign up</h2><p>your email is already in use</p><p style=\"color:blue\">click 3 to go back.</p></body></html>");
                Engine.removeEventListeners();
                Map<Integer, Runnable> events = new HashMap<>();

                events.put(KeyEvent.VK_3, () -> {
                    Engine.restart();
                });

                Engine.addEventListener(events);
            }else {
                User newUser = User.CreateUser(data.get(0), data.get(1), data.get(2),data.get(3).equals("1"));
                ProfileManager.signIn(newUser);
                Engine.restart();
            }
           
        }
    }   
    
    public static void openSignUpPage() {
        Engine.removeEventListeners();
        signUp(new ArrayList<>());
    }
    
}
