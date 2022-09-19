
package pages;

import pages.authentication.AllUsers;
import managers.ProfileManager;
import engine.Engine;
import static engine.Engine.Exit;
import static engine.Engine.changeCurrentPage;
import engine.Page;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import pages.questioning.AnswerQuestion;
import pages.questioning.AskQuestion;
import pages.questioning.DeleteQuestion;
import pages.questioning.Feed;
import pages.questioning.QuestionsFromMe;
import pages.questioning.QuestionsToMe;

public class Home extends Page {

    @Override
    public String getPageName() {
        return "home";
    }

    @Override
    public Map<Integer, Runnable> getDefaultActions() {
        Map<Integer, Runnable> events = new HashMap<>();      
            events.put(KeyEvent.VK_1, () -> {
               changeCurrentPage(new QuestionsToMe());
            });        
            events.put(KeyEvent.VK_2, () -> {
               changeCurrentPage(new QuestionsFromMe());
            });          
            events.put(KeyEvent.VK_3, () -> {
               changeCurrentPage(new AnswerQuestion());
            });        
            events.put(KeyEvent.VK_4, () -> {
               changeCurrentPage(new DeleteQuestion());
            });         
            events.put(KeyEvent.VK_5, () -> {
               changeCurrentPage(new AskQuestion());
            });        
            events.put(KeyEvent.VK_6, () -> {
               changeCurrentPage(new AllUsers());
            });           
            events.put(KeyEvent.VK_7, () -> {
               changeCurrentPage(new Feed());
            });           
            events.put(KeyEvent.VK_8, () -> {
                ProfileManager.logOut();
                Engine.restart();
            });          
            events.put(KeyEvent.VK_9, () -> {
                Exit();
            });

            return events;
    }

    @Override
    public String getDefaultContent() {
        return "<html><body><h2>Hi <span style=\"color:blue\">"+ProfileManager.getCurrentUser().getName()+"</span> what do you want to do?</h2>"+
                "<ol>"
                + "<li>Show Questions To me</li>"
                + "<li>Show Questions From me</li>"
                + "<li>Answer a Question</li>"
                + "<li>Delete a Question</li>"
                + "<li>Ask a Question</li>"
                + "<li>Show all users</li>"
                + "<li>Feed</li>"
                + "<li>Logut</li>"
                + "<li>Exit</li>"
                + "</ol>"
                + "<p style=\"color:blue\">click 1 to Show Questions To me , 2 to Show Questions From me 3 to ...</p>"
                + "</body></html>";
    }
        
}
