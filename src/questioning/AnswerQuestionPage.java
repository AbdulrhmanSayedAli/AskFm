package questioning;

import authentication.ProfileManager;
import engine.Engine;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import models.Question;

public class AnswerQuestionPage {
    
     private static void goBackEvents(){
        Engine.removeEventListeners();
        Map<Integer, Runnable> events = new HashMap<>();

        events.put(KeyEvent.VK_3, () -> {
            Engine.restart();
        });

        Engine.addEventListener(events);
    }
    
    
    
    public static void OpenAnswerQuestionPage(){
        Engine.removeEventListeners();
        Engine.changeText("<html><body><h2>answer a question</h2><p>enter the question id</p></body></html>");
        
        Engine.openTextField((String id)->{
            
            try {
                Question question =  QuestionManager.getQuestion(Integer.parseInt(id));
                if(question==null) throw  new Exception();
                
                if(question.getReciverID()!=ProfileManager.getCurrentUser().getId()){
                    Engine.changeText("<html><body><h2>answer a question</h2><p>you can only answer questions to you</p><p style=\"color:blue\">click 3 to exit.</p></body></html>");
                    goBackEvents();
                    return;
                }
                
                
                Engine.changeText("<html><body><h2>answer a question</h2><p>enter the answer</p></body></html>");
                Engine.openTextField((String answer)->{
                    QuestionManager.answerQuestion(Integer.parseInt(id), answer);
                    Engine.restart();
                });
                
                
            } catch (Exception e) {
                Engine.changeText("<html><body><h2>answer a question</h2><p>question not found</p><p style=\"color:blue\">click 3 to exit.</p></body></html>");
                goBackEvents();
            }
            
            
        });
    }
    
    
    
    
}
