package askfm.engine;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;


public abstract class Page {
    
    public abstract String getPageName();
    
    public void onStart(){
    }
    
    public void onExit(){
        //nothing
    }
    
    public String getDefaultContent(){
        return "<html><body><h2>"+getPageName()+"</h2></body></html>";
    }
    
    public Map<Integer,Runnable> getDefaultActions(){
        return new HashMap<>();
    }
    
    public void changeContent(String newContent){
        Engine.changeText(newContent);
    }
    
    public void changeActions(Map<Integer,Runnable> actions){
        Engine.removeEventListeners();
        Engine.addEventListener(actions);
    }
    
    public void setGoBackEvents(){
        Map<Integer, Runnable> actions = new HashMap<>();
        actions.put(KeyEvent.VK_3, () -> {
            Engine.restart();
        });

        changeActions(actions);
    }
    
}
