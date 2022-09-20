package askfm.models;

import askfm.managers.IDGenerator;

public class User{
    private int Id;
    private String name;
    private String email;
    private String password;
    private boolean allowAnonymousQ;

    public User(int id , String name, String email, String password , boolean allowAnonymousQ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.Id = id;
        this.allowAnonymousQ = allowAnonymousQ;
    }
    
    public static User CreateUser(String name, String email, String password , boolean allowAnonymousQ){
        return new User(IDGenerator.generateID(IDGenerator.User_ID), name, email, password, allowAnonymousQ);
    }
    
    
        
    public int getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAllowAnonymousQ() {
        return allowAnonymousQ;
    }

    public void setAllowAnonymousQ(boolean allowAnonymousQ) {
        this.allowAnonymousQ = allowAnonymousQ;
    }
    
    
}
