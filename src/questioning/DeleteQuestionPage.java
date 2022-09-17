
package questioning;

import authentication.ProfileManager;
import engine.Engine;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import models.Question;


public class DeleteQuestionPage {
    
    
    private static void goBackEvents(){
        Engine.removeEventListeners();
        Map<Integer, Runnable> events = new HashMap<>();

        events.put(KeyEvent.VK_3, () -> {
            Engine.restart();
        });

        Engine.addEventListener(events);
    }
    
    public static void OpenDeleteQuestionPage(){
        Engine.removeEventListeners();
        Engine.changeText("<html><body><h2>delete a question</h2><p>enter the question id</p></body></html>");
        
        Engine.openTextField((String id)->{
            
            try {
                Question question =  QuestionManager.getQuestion(Integer.parseInt(id));
                if(question==null) throw  new Exception();
                
                if(question.getSendeID()!=ProfileManager.getCurrentUser().getId()&&question.getReciverID()!=ProfileManager.getCurrentUser().getId()){
                    Engine.changeText("<html><body><h2>answer a question</h2><p>you can only delete questions to you or from you</p><p style=\"color:blue\">click 3 to exit.</p></body></html>");
                    goBackEvents();
                    return;
                }
                
                QuestionManager.deleteQuestion(Integer.parseInt(id));
                Engine.restart();
                
               
            } catch (Exception e) {
                Engine.changeText("<html><body><h2>answer a question</h2><p>question not found</p><p style=\"color:blue\">click 3 to exit.</p></body></html>");
                goBackEvents();
            }
            
            
        });
    }
    
}
