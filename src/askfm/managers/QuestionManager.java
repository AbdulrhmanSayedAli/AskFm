package askfm.managers;

import askfm.managers.ProfileManager;
import askfm.managers.FileManager;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import askfm.models.*;

public class QuestionManager {
    private static final String Questions_FileName = "questions.txt"; 
    
    
    public static void writeQuestion(User sender , User reciver , boolean isAnonymous ,String text , int head){
        try {
            Question question = Question.createQuestion(text, sender.getName(), reciver.getName(), reciver.getId(), sender.getId(), isAnonymous,head," ");
            FileWriter appender = FileManager.appender(Questions_FileName, true);
            String line = SerializationManager.toString(question);
            appender.write(line+"\n");
            appender.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public static void answerQuestion(int id , String answer){    
        try {
            String res = "";
            Scanner reader = FileManager.reader(Questions_FileName, true);

            while (reader.hasNext()) {
                Question question = (Question)SerializationManager.fromString(reader.nextLine());
                if (question.getId() == id) {
                   question.setAnswer(answer);
                }
                res+=SerializationManager.toString(question)+"\n";
            }    
            
            reader.close();
            FileWriter writer = FileManager.writer(Questions_FileName, true);
            writer.write(res);
            writer.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
    
    public static void deleteQuestion(int id){    
        try {
            String res = "";
            Scanner reader = FileManager.reader(Questions_FileName, true);

            while (reader.hasNext()) {
                Question question = (Question)SerializationManager.fromString(reader.nextLine());
                if (question.getId() != id && question.getHeadQuestionID() != id) {
                    res += SerializationManager.toString(question) + "\n";
                }
            }    
            
            reader.close();
            FileWriter writer = FileManager.writer(Questions_FileName, true);
            writer.write(res);
            writer.close();
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
    
    
    public static ArrayList<Question> getAllQuestions(){
        Scanner reader = FileManager.reader(Questions_FileName, true);
        ArrayList<Question> res = new ArrayList<>();
        
        while(reader.hasNext()){
            Question question = (Question)SerializationManager.fromString(reader.nextLine());
            res.add(question);
        }
        return res;
    }
    
    
    
    public static Question getQuestion(int id){
        for(Question q : getAllQuestions()){
            if(q.getId()==id) return q;
        }
        
        return null;
    }
    
    
    public static ArrayList<Question> getQuestionsToMe(){
         ArrayList<Question> res = new ArrayList<>();
         for (Question q : QuestionManager.getAllQuestions()) {
            if(q.getReciverID()==ProfileManager.getCurrentUser().getId()&&q.getHeadQuestionID()==-1)res.add(q);
        }
         return res;
    }
    
    
    public static ArrayList<Question> getQuestionsFromMe(){
         ArrayList<Question> res = new ArrayList<>();
         for (Question q : QuestionManager.getAllQuestions()) {
            if(q.getSendeID()==ProfileManager.getCurrentUser().getId()&&q.getHeadQuestionID()==-1)res.add(q);
        }
         return res;
    }
    
    
    public static ArrayList<Question> getQuestionThread(int questionID){
         ArrayList<Question> res = new ArrayList<>();
         for (Question q : QuestionManager.getAllQuestions()) {
            if(q.getHeadQuestionID()==questionID)res.add(q);
        }
         return res;
    }
    
}
