package askfm.engine;

import askfm.managers.ProfileManager;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import askfm.pages.Home;
import askfm.pages.Welcome;

public class Engine {
    
    private static final JLabel Label = new JLabel();
    private static final JFrame Root = new JFrame();
    private static final JPanel Panel = new JPanel();
 
    private static Page currentPage;
    
    public static void changeText(String text){
        Label.setText(text);
    }
    
    public static void changeCurrentPage(Page page){
        if(currentPage!=null)currentPage.onExit();
        currentPage = page;
        changeText(page.getDefaultContent());
        removeEventListeners();
        addEventListener(page.getDefaultActions());
        currentPage.onStart();
    }
    
    
    public static void restart(){
        if (!ProfileManager.isLoggedIn()) {
            changeCurrentPage(new Welcome());
        } else {
            changeCurrentPage(new Home());
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
   
   
   
   public static void main(String[] args){
        initialize();
        restart();
        
    }
   
    
   public static void Exit(){
       Root.dispatchEvent(new WindowEvent(Root, WindowEvent.WINDOW_CLOSING));
   }

}
