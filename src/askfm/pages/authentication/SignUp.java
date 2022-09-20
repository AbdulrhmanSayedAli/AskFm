
package askfm.pages.authentication;

import askfm.managers.ProfileManager;
import askfm.engine.Engine;
import askfm.engine.Page;
import askfm.managers.EmailManager;
import java.util.ArrayList;
import askfm.models.User;

public class SignUp extends Page {   
    
    @Override
    public String getPageName() {
        return "sign up";
    }
    
    
    private Boolean isEmailUsed(String email){
        ArrayList<User> users = ProfileManager.getAllUsers();
        for (User user : users){
            System.out.println(user.getEmail());
            if(user.getEmail().equals(email)) return true;
        }
        return false;
    }
    
    private String [] dataToEnter = {
       "user name",
       "email",
       "password",
       "allow anonymous questions? (0 or 1)"
    };
    
    private void signUp (ArrayList<String> data){
        String message = "<html><body><h2>sign up</h2>";
        for (int i=0;i<data.size();i++)message+="<p>"+dataToEnter[i]+" : "+data.get(i)+"</p>";
        if(data.size()<4) message+="<p style=\"color:blue\">enter your "+dataToEnter[data.size()]+" (all spaces will be replaced with / )</p>";
        message+="</body></html>";
        changeContent(message);
        
        
        if(data.size()<4)
        Engine.openTextField((String text) -> {
            if(data.size()<4||text.equals("0")||text.equals("1")){
                 data.add(text.replaceAll(" ", "/"));
            }
           signUp(data);
        });
        else {
            
            if(isEmailUsed(data.get(1))){
                changeContent("<html><body><h2>sign up</h2><p>your email is already in use</p><p style=\"color:blue\">click 3 to go back.</p></body></html>");
                setGoBackEvents();
            }else if (!EmailManager.validate(data.get(1))) {
                changeContent("<html><body><h2>sign up</h2><p>your email is not valid please enter a valid email</p><p style=\"color:blue\">click 3 to go back.</p></body></html>");
                setGoBackEvents();
            } else {
                User newUser = User.CreateUser(data.get(0), data.get(1), data.get(2),data.get(3).equals("1"));
                ProfileManager.signIn(newUser);
                Engine.restart();
            }
           
        }
    }   

    @Override
    public void onStart() {
       signUp(new ArrayList<>());
    }
    

    
    
}
