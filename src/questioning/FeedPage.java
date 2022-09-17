
package questioning;

import engine.Engine;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import models.Question;


public class FeedPage {
    
    private static void goBackEvents(){
        Engine.removeEventListeners();
        Map<Integer, Runnable> events = new HashMap<>();

        events.put(KeyEvent.VK_3, () -> {
            Engine.restart();
        });

        Engine.addEventListener(events);
    }
    
    private static String getThread(int id){
        String content = "<ul>";
        int k = 0;
        for(Question q : QuestionManager.getQuestionThread(id)){
            content += QuestionParse(q, false);
            k++;
        }
        content+="</ul>";
        
        return k==0 ? "" : content;
    }
    
    private static String QuestionParse(Question q,boolean thread){
        String content="";
        content+="<li> <span style=\"color:blue\">("+q.getId()+")</span> ---- From : "+(q.IsAnonyumous()?"<span style=\"color:red\">ANONYMOUS</span>":q.getSenderName())+" ---- to : <span style=\"color:green\">"+q.getReciverName()+"</span><br/>";
        content+="question: "+q.getQuestion()+"<br/>";
        content+="answer: "+(q.getAnswer().trim().isEmpty()?"<span style=\"color:red\">not answered</span>":q.getAnswer())+"<br/>";
        if(thread)content+=getThread(q.getId());
        content+="</li>";
        
        return content;
    }
    
    public static void OpenFeedPage(){
        Engine.removeEventListeners();
        goBackEvents();
        
        String content ="<html><body><h2>feed</h2><ol>";
        
        for(Question q : QuestionManager.getAllQuestions())if(q.getHeadQuestionID()==-1){
            content+=QuestionParse(q,true);
        }
        
        content+="<p style=\"color:blue\">click 3 to exit</p></ol></html></body>";
        
        Engine.changeText(content);
    }
    
}
