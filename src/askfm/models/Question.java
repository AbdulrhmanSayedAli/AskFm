package askfm.models;

import askfm.managers.IDGenerator;
import java.io.Serializable;


public class Question implements Serializable{
   private int id;
   private String question;
   private String answer;
   private String senderName;
   private String reciverName;
   private int senderID;
   private int reciverID;
   private boolean isAnonyumous;
   private int headQuestionID;
   
    public Question(int id, String question, String senderName, String reciverName, int reciverID, int senderID, boolean isAnonyumous, int headQuestionID,String answer) {
        this.id = id;
        this.question = question;
        this.senderName = senderName;
        this.reciverName = reciverName;
        this.reciverID = reciverID;
        this.senderID = senderID;
        this.isAnonyumous = isAnonyumous;
        this.headQuestionID = headQuestionID;
        this.answer = answer;
    }
   
   
    public static Question createQuestion( String question, String senderName, String reciverName, int reciverID,int senderID, boolean isAnonyumous, int headQuestionID,String answer) {
        return new Question(IDGenerator.generateID(IDGenerator.Question_ID), question, senderName, reciverName, reciverID,senderID, isAnonyumous, headQuestionID,answer);
    }
   

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReciverName() {
        return reciverName;
    }

    public void setReciverName(String reciverName) {
        this.reciverName = reciverName;
    }

    public int getSendeID() {
        return senderID;
    }

    public void setSendeID(int sendeID) {
        this.senderID = sendeID;
    }

    public int getReciverID() {
        return reciverID;
    }

    public void setReciverID(int reciverID) {
        this.reciverID = reciverID;
    }

    public boolean IsAnonyumous() {
        return isAnonyumous;
    }

    public void setIsAnonyumous(boolean isAnonyumous) {
        this.isAnonyumous = isAnonyumous;
    }

    public int getHeadQuestionID() {
        return headQuestionID;
    }

    public void setHeadQuestionID(int headQuestionID) {
        this.headQuestionID = headQuestionID;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
   
   
   
}
