package askfm.pages.authentication;

import askfm.managers.ProfileManager;
import askfm.engine.Engine;
import askfm.engine.Page;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import askfm.models.User;

public class AllUsers extends Page{

    @Override
    public String getPageName() {
        return "all users";
    }

    @Override
    public Map<Integer, Runnable> getDefaultActions() {
        Map<Integer, Runnable> events = new HashMap<>();
        events.put(KeyEvent.VK_3, () -> {
            Engine.restart();
        });
        Engine.addEventListener(events);
        return events;
    }

    @Override
    public String getDefaultContent() {
        String content = "<html><body><h2>all users</h2><ol>";
        for (User user : ProfileManager.getAllUsers()){
            content+="<li "+(user.getId()==ProfileManager.getCurrentUser().getId()?"style=\"background-color:white;\"":"")+"><span style=\"color:blue;\">"+user.getName()+" ("+user.getId()+")</span> -- email: "+user.getEmail();
            if(!user.isAllowAnonymousQ()) content += "<span style=\"color:red\"> (anonymous questions not allowed) </span>";
            content+="</li>";
        }      
        content += "</ol><p style=\"color:blue\">click 3 to exit.</p></html></body>";
        return content;
    }

    
    
}
