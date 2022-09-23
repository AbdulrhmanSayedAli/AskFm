package askfm.pages.questioning;

import askfm.managers.ProfileManager;
import askfm.engine.Engine;
import askfm.engine.Page;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import askfm.models.Question;
import askfm.models.User;
import askfm.managers.QuestionManager;


public class AskQuestion extends Page {   

    @Override
    public String getPageName() {
       return "ask a question";
    }
    
    
    
    private User getUserByEmail(String email){
        for(User user : ProfileManager.getAllUsers()){
            if(user.getEmail().equals(email)){
                return user;
            }
        }
        
        return new User(-1,"","","",false);
    }
    
    private void AskNormal(boolean anonymous,User reciver){
        String content = "<html><body><h2>ask a qustion</h2><p>write your question " + (reciver.isAllowAnonymousQ() ? "" : "<span style=\"color:red\"> (you can't ask anonymously) </span>") + "</p>";
        content += "</html></body>";
        Engine.changeText(content);
        
        Engine.openTextField((String question)->{            
             QuestionManager.writeQuestion(ProfileManager.getCurrentUser(), reciver, anonymous, question.replaceAll("\n", " "), -1);
             Engine.restart();       
        });
    }
    
    private void AskThread(Question head  , boolean anonymous ,boolean userAllowAnony){
        String content = "<html><body><h2>ask a qustion</h2><p>write your question " + (userAllowAnony ? "" : "<span style=\"color:red\"> (you can't ask anonymously) </span>") + "</p>";
        content += "</html></body>";
        Engine.changeText(content);
        
        Engine.openTextField((String question)->{            
             QuestionManager.writeQuestion(ProfileManager.getCurrentUser(), ProfileManager.getUser(head.getReciverID()), anonymous, question.replaceAll("\n", " "), head.getId());
             Engine.restart();       
        });
    }
    
    
    
    private void normalEvent(){
        changeContent("<html><body><h2>ask a qustion</h2><p style=\"color:blue\">enter the reciver's email</body></html>");
        Engine.openTextField((String email)->{
            if(email.equals(ProfileManager.getCurrentUser().getEmail())){
                Engine.changeText("<html><body><h2>ask a qustion</h2><p>you cant send your self a question</p><p style=\"color:blue\">click 3 to exit.</p></html></body>");
                setGoBackEvents();
                return;
            }
            User reciver = getUserByEmail(email);
            if(reciver.getId()==-1){
                changeContent("<html><body><h2>ask a qustion</h2><p>user not found!!</p><p style=\"color:blue\">click 3 to exit.</p></html></body>");
                setGoBackEvents();
            } else {     
                if(reciver.isAllowAnonymousQ()){        
                    String content = "<html><body><h2>ask a qustion</h2><p>do you want to ask anonymously (0 or 1)</p>";
                    content += "</html></body>";
                    Engine.changeText(content);
                    
                    Engine.openTextField((String anonymous)->{
                        if(anonymous.equals("1")){
                            AskNormal(true , reciver);
                        }else if(anonymous.equals("0")){
                            AskNormal(false , reciver);
                        } else {
                            changeContent("<html><body><h2>ask a qustion</h2><p>not a valid value</p><p style=\"color:blue\">click 3 to exit.</p></html></body>");
                            setGoBackEvents();
                        }
                    });      
                    
                } else {
                    AskNormal(false,reciver);
                }
                
            }
        });
    }  
    
    private void threadEvent(){
        changeContent("<html><body><h2>ask a qustion</h2><p style=\"color:blue\">enter the head id</body></html>");
        Engine.openTextField((String headID)->{
            
            try {
                Question head = QuestionManager.getQuestion(Integer.parseInt(headID));
                if(head==null||head.getHeadQuestionID()!=-1) throw new Exception();
                if(!ProfileManager.getUser(head.getReciverID()).isAllowAnonymousQ()){
                    AskThread(head,false,false);
                    return;
                }
                if(head.getReciverID()==ProfileManager.getCurrentUser().getId()){
                    changeContent("<html><body><h2>ask a qustion</h2><p>you cant send your self a question</p><p style=\"color:blue\">click 3 to exit.</p></html></body>");
                    setGoBackEvents();
                    return;
                }
                
                changeContent("<html><body><h2>ask a qustion</h2><p style=\"color:blue\">do you want to ask anonymously (0 or 1)</body></html>");
                Engine.openTextField((String anony)->{
                    if(anony.equals("1")){
                        AskThread(head,true,true);
                    } else if(anony.equals("0")) {
                         AskThread(head,false,true);
                    } else {
                        changeContent("<html><body><h2>ask a qustion</h2><p>not a valid value</p><p style=\"color:blue\">click 3 to exit.</p></html></body>");
                        setGoBackEvents();
                    }
                });
                
            } catch (Exception e) {
                changeContent("<html><body><h2>ask a qustion</h2><p>not a valid id or question doesnt exists anymore</p><p style=\"color:blue\">click 3 to exit.</p></html></body>");
                setGoBackEvents();
            }
        });
    }

    @Override
    public Map<Integer, Runnable> getDefaultActions() {
         Map<Integer, Runnable> events = new HashMap<>();
        events.put(KeyEvent.VK_1, () -> {
            normalEvent();
        });      
        events.put(KeyEvent.VK_2, () -> {
            threadEvent();
        });
        return events;
    }

    @Override
    public String getDefaultContent() {
        return "<html><body><h2>ask a qustion</h2><ol>"
                + "<li>ask question to user(without thread)</li>"
                + "<li>ask a thread question</li>"
                + "</ol></body></html>";
    }

    
}
