package questioning;

import authentication.ProfileManager;
import engine.FileManager;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import models.Question;
import models.User;

public class QuestionManager {
    private static final String Questions_FileName = "questions.txt"; 
    
    
    public static void writeQuestion(User sender , User reciver , boolean isAnonymous ,String text , int head){
        try {
            Question question = Question.createQuestion(text, sender.getName(), reciver.getName(), reciver.getId(), sender.getId(), isAnonymous,head," ");
            FileWriter appender = FileManager.appender(Questions_FileName, true);
            String line = question.getId()+" "+question.getSendeID() + " " + question.getSenderName() +" " + question.getReciverID()+ " " + question.getReciverName()+" ";
            line+= question.getHeadQuestionID()+" " +(question.IsAnonyumous()?"1":"0")+";"+question.getQuestion()+";"+question.getAnswer()+"\n";
            appender.write(line);
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
                String line = reader.nextLine();
                if (Integer.parseInt(line.split(" ")[0]) == id) {
                    String [] arr = line.split(";");
                    arr[2]=answer;
                    res+=arr[0]+";"+arr[1]+";"+arr[2]+"\n";
                } else {
                    res+=line + "\n";
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
    
    
    public static void deleteQuestion(int id){    
        try {
            String res = "";
            Scanner reader = FileManager.reader(Questions_FileName, true);

            while (reader.hasNext()) {
                String line = reader.nextLine();
                if (Integer.parseInt(line.split(" ")[0]) != id && Integer.parseInt(line.split(" ")[5]) != id) {
                    res += line + "\n";
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
    
    
    
    
    public static Question getQuestion(int id){
        for(Question q : getAllQuestions()){
            if(q.getId()==id) return q;
        }
        
        return null;
    }
    
    
    public static ArrayList<Question> getAllQuestions(){
        Scanner reader = FileManager.reader(Questions_FileName, true);
        ArrayList<Question> res = new ArrayList<>();
        
        while(reader.hasNext()){
            String[] form = reader.nextLine().split(";");
            String [] data = form[0].split(" ");
            String ques = form[1];
            String ans = form[2];
            
            res.add(new Question(Integer.parseInt(data[0]), ques, data[2], data[4], Integer.parseInt(data[3]), Integer.parseInt(data[1]), data[6].equals("1"), Integer.parseInt(data[5]),ans));
        }
        return res;
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
