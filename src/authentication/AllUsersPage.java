package authentication;

import engine.Engine;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import models.User;

public class AllUsersPage {
    
    public static void OpenAllUsersPage(){
        Engine.removeEventListeners();
        
        String content = "<html><body><h2>all users</h2><ol>";
        for (User user : ProfileManager.getAllUsers()){
            content+="<li "+(user.getId()==ProfileManager.getCurrentUser().getId()?"style=\"background-color:white;\"":"")+"><span style=\"color:blue;\">"+user.getName()+" ("+user.getId()+")</span> -- email: "+user.getEmail();
            if(!user.isAllowAnonymousQ()) content += "<span style=\"color:red\"> (anonymous questions not allowed) </span>";
            content+="</li>";
        }      
        content += "</ol><p style=\"color:blue\">click 3 to exit.</p></html></body>";
        Engine.changeText(content);
        Map<Integer, Runnable> events = new HashMap<>();

        events.put(KeyEvent.VK_3, () -> {
            Engine.restart();
        });

        Engine.addEventListener(events);
        
               
    }
    
}
