package engine;

import authentication.AllUsersPage;
import authentication.LoginPage;
import authentication.ProfileManager;
import authentication.SignUpPage;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import questioning.AnswerQuestionPage;
import questioning.AskQuestionPage;
import questioning.DeleteQuestionPage;
import questioning.FeedPage;
import questioning.QuestionsFromMe;
import questioning.QuestionsToMePage;

public class Engine {
    
    private static final JLabel Label = new JLabel();
    private static final JFrame Root = new JFrame();
    private static final JPanel Panel = new JPanel();
    
    public static void main(String[] args){
        initialize();
        restart();
        
    }
    
    
    public static void restart(){
        removeEventListeners();
        if(!ProfileManager.isLoggedIn()){
            changeText(loginText());

            Map<Integer, Runnable> events = new HashMap<>();

            events.put(KeyEvent.VK_1, () -> {
                LoginPage.openLoginPage();
            });

            events.put(KeyEvent.VK_2, () -> {
                SignUpPage.openSignUpPage();
            });

            events.put(KeyEvent.VK_3, () -> {
                Exit();
            });

            addEventListener(events);
        } else {
            changeText(mainProgramText());
            
            Map<Integer, Runnable> events = new HashMap<>();

            
            events.put(KeyEvent.VK_1, () -> {
                QuestionsToMePage.OpenQuestionsToMePage();
            });
            
            events.put(KeyEvent.VK_2, () -> {
                QuestionsFromMe.OpenQuestionsFromMe();
            });
            
            events.put(KeyEvent.VK_3, () -> {
                AnswerQuestionPage.OpenAnswerQuestionPage();
            });
            
            events.put(KeyEvent.VK_4, () -> {
                DeleteQuestionPage.OpenDeleteQuestionPage();
            });
            
            events.put(KeyEvent.VK_5, () -> {
                AskQuestionPage.OpenAskQuestionPage();
            });
            
            events.put(KeyEvent.VK_6, () -> {
                AllUsersPage.OpenAllUsersPage();
            });
            
            events.put(KeyEvent.VK_7, () -> {
                FeedPage.OpenFeedPage();
            });
            
            events.put(KeyEvent.VK_8, () -> {
                ProfileManager.logOut();
                restart();
            });
            
            events.put(KeyEvent.VK_9, () -> {
                Exit();
            });

            addEventListener(events);
    }
    
   }
    
    
    
    private static Boolean isShown = false;
    
    
    public static Boolean isTextFieldShown() {
        return isShown;
    }
    
    public static void openTextField (OnTextFieldSubmitted onSubmit){
        if(isTextFieldShown()) return;
        
        isShown = true;
        JTextField field = new JTextField(11);
        JButton button = new JButton("submit");
        Root.setFocusable(false);
        button.addActionListener((ActionEvent e) -> {
            hideTextField();
            onSubmit.onSubmit(field.getText());
        });
        Panel.add(field);
        Panel.add(button);
        Panel.updateUI();
    }
    
    public static void hideTextField(){
        if(!isTextFieldShown())return;

        isShown = false;
        Panel.remove(2);
        Panel.remove(1);
        Panel.updateUI(); 
        Root.setFocusable(true);
        Root.requestFocus();
    }
    
    
    
       
     public static void addEventListener (Map<Integer,Runnable> events){         
        Root.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                
                for (int key : events.keySet()){
                    if(keyCode==key)events.get(key).run();
                }
            }
        });
    }
    
    
    
    
    public static void removeEventListeners(){
        for (KeyListener listener : Root.getKeyListeners()){
            Root.removeKeyListener(listener);
        } 
    }
    
    
    
   
    
    

   private static void initialize() {       
        Root.setTitle("Ask me");
        Root.setLocation(60,60);
        Root.setPreferredSize(new Dimension(500,500));
        Root.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Panel.add(Label);
        Root.add(Panel);
        Root.pack();
        Root.setVisible(true);
    }
    
   public static void Exit(){
       Root.dispatchEvent(new WindowEvent(Root, WindowEvent.WINDOW_CLOSING));
   }

    

    
    public static void changeText(String text){
        Label.setText(text);
    }
    
    
    
    
   

    private static String loginText (){
        return "<html><body><h2>what do you want to do?</h2> <p>you are not logged in yet</p>"+
                "<ol>"
                + "<li>Login</li>"
                + "<li>SignUp</li>"
                + "<li>Exit</li>"
                + "</ol>"
                + "<p style=\"color:blue\">click 1 to Login, 2 to SignUp, 3 to Exit.</p>"
                + "</body></html>";
    }
    

    private static String mainProgramText() {
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
