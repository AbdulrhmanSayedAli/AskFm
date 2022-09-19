package pages;

import pages.authentication.SignUp;
import pages.authentication.Login;
import engine.Engine;
import engine.Page;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;


public class Welcome extends Page {

    @Override
    public String getPageName() {
        return "welcome";
    }
    
    @Override
    public Map<Integer, Runnable> getDefaultActions() {
        Map<Integer, Runnable> events = new HashMap<>();
            events.put(KeyEvent.VK_1, () -> {
                Engine.changeCurrentPage(new Login());
            });
            events.put(KeyEvent.VK_2, () -> {
                 Engine.changeCurrentPage(new SignUp());
            });
            events.put(KeyEvent.VK_3, () -> {
                Engine.Exit();
            });
        return events;
    }
    
    
    @Override
    public String getDefaultContent() {
        return "<html><body><h2>what do you want to do?</h2> <p>you are not logged in yet</p>"+
                "<ol>"
                + "<li>Login</li>"
                + "<li>SignUp</li>"
                + "<li>Exit</li>"
                + "</ol>"
                + "<p style=\"color:blue\">click 1 to Login, 2 to SignUp, 3 to Exit.</p>"
                + "</body></html>";
    }
    
}
